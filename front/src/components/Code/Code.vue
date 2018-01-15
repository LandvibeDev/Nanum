<template>
  <div>
    <!--<v-jstree id="tree" :data="fileList" draggable allow-batch whole-row @item-click="clickFile"-->
              <!--@item-toggle="toggleDirectory"></v-jstree>-->
    <div id="tree">
      <div id="edit-tree">
        <md-button class="md-icon-button edit-icon" v-on:click="showModal('add')"><md-icon>note_add</md-icon></md-button>
        <md-button class="md-icon-button edit-icon" v-on:click="updateFile"><md-icon>save</md-icon></md-button>
        <md-button class="md-icon-button edit-icon" v-on:click="showModal('delete')"><md-icon>delete_forever</md-icon></md-button>
      </div>
      <ul id="project-tree" class="fa-ul tree-ul">
        <CodeTree :model="fileList" :projectId="projectId" v-on:clickFile="clickFile" class="item"></CodeTree>
      </ul>
    </div>
    <div v-on:keyup="keyEvent" id="editor" ref="editor">/*ace editor*/</div>

    <transition name="modal">
      <EditModal v-if="addActive" @close="addActive = false" @confirm="addFile" v-model="addFileValue">
        <!--
          you can use custom content here to overwrite
          default content
        -->
        <h3 slot="header">Enter a new file name</h3>
        <div slot="body">
          <md-switch v-model="isAddDirectory">Directory</md-switch>
          <md-field slot="body">
            <label>name</label>
            <md-input v-model="addFileValue"/>
          </md-field>
        </div>
      </EditModal>
      <EditModal v-if="deleteActive" @close="deleteActive = false" @confirm="deleteFile">
        <h3 slot="header">delete {{this.selectedFile.model.text}}?</h3>
      </EditModal>
    </transition>

  </div>

</template>

<script>
  import * as ace from 'brace'
  import 'brace/mode/java'
  import 'brace/theme/tomorrow'
  import 'brace/ext/language_tools'
  import '../../lib/sockjs.min'
  import '../../lib/stomp.min'
  import CodeTree from './CodeTree.vue'
  import EditModal from './EditModal.vue'

  export default {
    components: {CodeTree,EditModal},
    data: function () {
      return {
        fileContent: '',
        fileName: '',
        socket:null,
        subscribeObject: null,
        subscribeJoinObject:null,
        stompClient: null,
        editorEl: null,
        fileList: {text:'Project',type:'DIRECTORY',path:"",children:[]},
        preCursor: null,
        solo : true,
        projectId : this.$route.params.projectId,
        addActive: false,
        addFileValue: null,
        deleteActive: false,
        selectedFile: null,
        intervalId: null,
        isAddDirectory: false
      }
    },
    mounted: function () {
      this.initAce()
    },
    created: function () {
//      this.getFileList()
      this.initSockJS()
    },
    destroyed : function(){
      this.socket.close()
      clearInterval(this.intervalId)
    },
    methods: {
      initAce: function () {
        this.editorEl = this.$refs.editor
        this.editor = ace.edit('editor')
        ace.acequire('ace/ext/language_tools')
        ace.acequire('ace/snippets')
        this.editor.setOptions({
          enableBasicAutocompletion: false,
          enableLiveAutocompletion: true
        })
        this.editor.setTheme('ace/theme/tomorrow')
        this.editor.getSession().setMode('ace/mode/javascript')
        this.editor.$blockScrolling = Infinity
        this.editorEl.style.fontSize = '18px'
      },
      initSockJS () {
        this.socket = new SockJS('/codes')
        this.stompClient = Stomp.over(this.socket)
        this.stompClient.debug = null
        this.stompClient.connect({}, this.onConnected)
      },
      onConnected: function (frame) {
        this.initSubscribe()
      },
      initSubscribe: function () {
        this.subscribeJoinObject = this.stompClient.subscribe('/code', this.onJoinMessageReceived)
      },
      subscribeStomp: function (fileName) {
        this.subscribeObject = this.stompClient.subscribe('/code/' + fileName, this.onMessageReceived)
      },
      onMessageReceived: function (payload) {
        let message = JSON.parse(payload.body)
        this.fileContent = message.content
        this.drawEditor()
      },
      onJoinMessageReceived : function(payload){
        let message = JSON.parse(payload.body)
        this.$toasted.show(message.sender+"님이 "+message.filename+"을 수정중입니다",{duration:2000})
      },
      sendMessage: function (message) {
        let data = {
          content: message
        }
        this.stompClient.send('/app/' + this.fileName, {}, JSON.stringify(data))
      },
      sendJoinMessage:function(fileName){
        let data = {
          sender: this.getCookieValue('username'),
          filename: fileName
        }
        this.stompClient.send("/app/join",{},JSON.stringify(data))
      },
      keyEvent: function () {
        this.fileContent = this.editor.getValue()
        this.preCursor = this.editor.selection.getCursor()
        this.sendMessage(this.fileContent)
      },
      clickFile (node) {
        console.log(node)
        if(this.selectedFile == node){
          return
        }
        if(this.selectedFile){
          this.selectedFile.selected = ""
        }
        this.selectedFile = node
//        if(this.intervalId){
//          clearInterval(this.intervalId)
//        }
        if(!node.isFolder){
          const fileName = node.model.text
          const path = node.model.path
          this.preCursor = null;
          this.getFileAndDrawEditor(fileName, path)
          this.sendJoinMessage(fileName)
        }
      },
      getFileAndDrawEditor (fileName, path) {
        let url = '/api/projects/'+this.projectId + '/files/' + fileName
        const param = {
          params: {
            path: path
          }
        }
        this.axios.get(url,param).then((result) => {
          this.fileName = fileName
          this.fileContent = result.data
//          this.intervalId = setInterval(this.selectedFile.update.bind(this.selectedFile,this.fileContent),10000);
          this.setFileMode();
          this.drawEditor()
          if(this.subscribeObject){
            this.subscribeObject.unsubscribe()
          }
          this.subscribeStomp(this.fileName)
        }).catch((error) => {
          console.log(error)
        })
      },
      setFileMode(){
        const ext = this.getExtensionOfFilename(this.fileName)
        if (ext === '.java') {
          this.editor.getSession().setMode('ace/mode/java')
        } else if (ext === '.js') {
          this.editor.getSession().setMode('ace/mode/javascript')
        } else {
          this.editor.getSession().setMode('ace/mode/text')
        }
      },
      drawEditor () {
        this.editor.setValue(this.fileContent)
        this.editor.clearSelection()
        if(this.preCursor){
          this.editor.selection.moveCursorTo(this.preCursor.row,this.preCursor.column)
        }
      },
      getExtensionOfFilename (filename) {
        let fileLen = filename.length
        let lastDot = filename.lastIndexOf('.')
        let fileExt = filename.substring(lastDot, fileLen).toLowerCase()
        return fileExt
      },
      getCookieValue: function (cookieName) {
        cookieName = cookieName + '='
        let cookieData = document.cookie
        console.log(document.cookie)
        let start = cookieData.indexOf(cookieName)
        let cValue = ''
        if (start !== -1) {
          start += cookieName.length
          let end = cookieData.indexOf(';', start)
          if (end === -1) end = cookieData.length
          cValue = cookieData.substring(start, end)
        }
        return decodeURIComponent(cValue)
      },
      addFile: function(){
        this.addActive = false
        console.log(this.selectedFile)
        this.selectedFile.add(this.addFileValue,this.isAddDirectory)
        this.isAddDirectory=false
        this.addFileValue=""
      },
      deleteFile: function(){
        this.deleteActive = false
        this.selectedFile.delete()
      },
      showModal: function(type){
        if(this.selectedFile){
          if(type == 'add'){
            this.addActive = true
          }else if(type == 'delete'){
            this.deleteActive = true
          }
        }else{
          this.$toasted.show("파일을 선택해 주세요.",{duration:2000})
        }
      },
      updateFile: function(){
        if(this.selectedFile.model.type=='FILE'){
          this.selectedFile.update(this.fileContent)
        }else{
          this.$toasted.show('파일을 선택해 주세요.',{duration:2000})
        }

      }
    }

  }
</script>

<style>
  #tree {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    width: 20%;
  }

  #editor {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 20%;
  }

  #project-tree{
    margin-left : 2em !important;
  }

  .tree-ul {
    margin-left: 1em !important;
  }

  #edit-tree{
    text-align: left;
    color: #555555;
  }

  .edit-icon{
    display: inline-block;
  }

</style>
