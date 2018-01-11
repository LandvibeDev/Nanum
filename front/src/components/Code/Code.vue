<template>
  <div>
    <v-jstree id="tree" :data="fileList" allow-batch whole-row @item-click="clickFile"
              @item-toggle="toggleDirectory"></v-jstree>
    <div v-on:keyup="keyEvent" id="editor" ref="editor">/*ace editor*/</div>
  </div>

</template>

<script>
  import * as ace from 'brace'
  import 'brace/mode/java'
  import 'brace/theme/tomorrow'
  import 'brace/ext/language_tools'
  import '../../lib/sockjs.min'
  import '../../lib/stomp.min'
  import VJstree from 'vue-jstree'

  export default {
    components: {VJstree},
    data: function () {
      return {
        fileContent: '',
        fileName: '',
        socket:null,
        subscribeObject: null,
        subscribeJoinObject:null,
        stompClient: null,
        editorEl: null,
        fileList: [],
        preCursor: null,
        solo : true
      }
    },
    mounted: function () {
      this.initAce()
    },
    created: function () {
      this.getFileList()
      this.initSockJS()
    },
    destroyed : function(){
      this.socket.close()
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
        const fileName = node.model.text
        const path = node.model.path
        if (node.model.type === 'FILE') {
          this.preCursor = null;
          this.getFileAndDrawEditor(fileName, path)
          this.sendJoinMessage(fileName)
        } else {
          this.openDirectory(node)
        }
      },
      getFileAndDrawEditor (fileName, path) {
        let url = '/api/files/' + fileName
        const param = {
          params: {
            path: path
          }
        }
        this.axios.get(url,param).then((result) => {
          this.fileName = fileName
          this.fileContent = result.data
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
      openDirectory (node) {
        console.log(this.fileList)
        console.log(node)
      },
      getFileList () {
        this.axios.get('/api/directories').then((result) => {
          this.fileList = result.data.filter((file) => {
            if (file.type === 'FILE') {
              file.icon = 'tree-file'
            } else {
              file.children = [{
                text: 'Loading...',
                value: 'Loading...',
                loading: true,
              }]
            }
            file.path = ''
            return file
          })
        }).catch((error) => {
          console.log(error)
        })
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
      toggleDirectory (node) {
        if (node.data.childFlag == undefined && node.data.type == 'DIRECTORY') {
          const param = {
            params: {
              path: node.data.path
            }
          }
          this.axios.get('/api/directories/' + node.data.text, param).then((result) => {
            let temp = result.data.filter((file) => {
              if (file.type === 'FILE') {
                file.icon = 'tree-file'
              } else {
                file.children = [{
                  text: 'Loading...',
                  value: 'Loading...',
                  loading: true,
                }]
              }
              file.path = node.data.path + '\\' + node.data.text
              return file
            })
            node.data.childFlag = true
            node.data.children = temp

          }).catch((error) => {
            console.log(error)
          })
        }
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

</style>
