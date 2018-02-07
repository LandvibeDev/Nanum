<template>
  <div>
    <!--<v-jstree id="tree" :data="fileList" draggable allow-batch whole-row @item-click="clickFile"-->
    <!--@item-toggle="toggleDirectory"></v-jstree>-->
    <div id="tree">
      <div id="edit-tree">
        <md-button class="md-icon-button edit-icon" v-on:click="showModal('add')">
          <md-icon>note_add</md-icon>
        </md-button>
        <md-button class="md-icon-button edit-icon" v-on:click="updateFile">
          <md-icon>save</md-icon>
        </md-button>
        <md-button class="md-icon-button edit-icon" v-on:click="showModal('delete')">
          <md-icon>delete_forever</md-icon>
        </md-button>
      </div>
      <ul id="project-tree" class="fa-ul tree-ul">
        <CodeTree :model="fileList" :projectId="projectId" v-on:clickFile="clickFile" class="item"></CodeTree>
      </ul>
    </div>
    <div id="editor" ref="editor">/*ace editor*/</div>
    <!--v-on:keyup="keyEvent"-->

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
  import 'brace/mode/c_cpp'
  import '../../lib/sockjs.min'
  import '../../lib/stomp.min'
  import CodeTree from './CodeTree.vue'
  import EditModal from './EditModal.vue'

  export default {
    components: {CodeTree, EditModal},
    data: function () {
      return {
        fileContent: '',
        fileName: '',
        socket: null,
        subscribeObject: null,
        subscribeJoinObject: null,
        stompClient: null,
        editorEl: null,
        session: null,
        fileList: {text: 'Project', type: 'DIRECTORY', path: '', children: []},
        projectId: this.$route.params.projectId,
        addActive: false,
        addFileValue: null,
        deleteActive: false,
        selectedFile: null,
        isAddDirectory: false,
        sourceKey: '',
        receiveFlag: false
      }
    },
    mounted: function () {
      this.initAce()
    },
    created: function () {
      this.initSockJS()
      this.sourceKey = this.makeRandomId()
    },
    destroyed: function () {
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
        this.session = this.editor.getSession()
        this.session.setMode('ace/mode/javascript')
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
        let data = JSON.parse(payload.body)
        console.log(data)
        if (data.source == this.sourceKey) {
          return
        }
        let deltas = []
        deltas[0] = data.delta
        this.receiveFlag = true
        this.session.getDocument().applyDeltas(deltas)
      },
      onJoinMessageReceived: function (payload) {
        let message = JSON.parse(payload.body)
        this.$toasted.show(message.sender + '님이 ' + message.filename + '을 수정중입니다', {duration: 2000})
      },
      sendMessage: function (delta) {
        let data = {
          delta: delta,
          source: this.sourceKey
        }
        console.log(data)
        this.stompClient.send('/app/' + this.fileName, {}, JSON.stringify(data))
      },
      sendJoinMessage: function (fileName) {
        let data = {
          filename: fileName
        }
        this.stompClient.send('/app/join', {}, JSON.stringify(data))
      },
      clickFile (node) {
        console.log(node)
        if (this.selectedFile == node) {
          return
        }
        if (this.selectedFile) {
          this.selectedFile.selected = ''
        }
        this.selectedFile = node
        if (!node.isFolder) {
          const fileName = node.model.text
          const path = node.model.path
          this.getFileAndMakeNewSession(fileName, path)
          this.sendJoinMessage(fileName)
        }
      },
      getFileAndMakeNewSession (fileName, path) {
        let url = '/api/projects/' + this.projectId + '/files/' + fileName
        const param = {
          params: {
            path: path
          }
        }
        this.axios.get(url, param).then((result) => {
          this.fileName = fileName
          this.fileContent = result.data
          this.makeNewEditSession(this.fileName, this.fileContent)
          if (this.subscribeObject) {
            this.subscribeObject.unsubscribe()
          }
          this.subscribeStomp(this.fileName)
        }).catch((error) => {
          console.log(error)
        })
      },
      makeNewEditSession (fileName, fileContent) {
        this.session.removeAllListeners('change')
        this.session.$stopWorker()
        let newSession = ace.createEditSession(fileContent)
        newSession.setMode(this.getFileMode(fileName))
        newSession.on('change', (delta) => {
          console.log(delta)
          if (this.receiveFlag) {
            this.receiveFlag = false
          } else {
            this.sendMessage(delta)
          }
          this.fileContent = this.session.getValue()
        })
        // session 메모리가 남는 것이 걱정되지만 자동으로 삭제된다고 하니 일단 보류
        // https://groups.google.com/forum/#!msg/ace-discuss/Eeg8gNDxDbw/XQQlUIQtofkJ

        this.editor.setSession(newSession)
        this.session = newSession
      },
      getFileMode (fileName) {
        const ext = this.getExtensionOfFilename(fileName)
        if (ext === '.java') {
          return 'ace/mode/java'
        } else if (ext === '.js') {
          return 'ace/mode/javascript'
        } else if (ext === '.cpp' || ext === '.c') {
          return 'ace/mode/c_cpp'
        } else {
          return 'ace/mode/text'
        }
      },
      getExtensionOfFilename (filename) {
        let fileLen = filename.length
        let lastDot = filename.lastIndexOf('.')
        let fileExt = filename.substring(lastDot, fileLen).toLowerCase()
        return fileExt
      },
      addFile: function () {
        this.addActive = false
        console.log(this.selectedFile)
        this.selectedFile.add(this.addFileValue, this.isAddDirectory)
        this.isAddDirectory = false
        this.addFileValue = ''
      },
      deleteFile: function () {
        this.deleteActive = false
        this.selectedFile.delete()
      },
      showModal: function (type) {
        if (this.selectedFile) {
          if (type == 'add') {
            this.addActive = true
          } else if (type == 'delete') {
            this.deleteActive = true
          }
        } else {
          this.$toasted.show('파일을 선택해 주세요.', {duration: 2000})
        }
      },
      updateFile: function () {
        if (this.selectedFile.model.type == 'FILE') {
          this.selectedFile.update(this.fileContent)
        } else {
          this.$toasted.show('파일을 선택해 주세요.', {duration: 2000})
        }
      },
      makeRandomId: function () {
        let text = ''
        let possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'

        for (var i = 0; i < 10; i++)
          text += possible.charAt(Math.floor(Math.random() * possible.length));

        return text
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

  #project-tree {
    margin-left: 2em !important;
  }

  .tree-ul {
    margin-left: 1em !important;
  }

  #edit-tree {
    text-align: left;
    color: #555555;
  }

  .edit-icon {
    display: inline-block;
  }

</style>
