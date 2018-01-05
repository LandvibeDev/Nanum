<template>
  <div>
    <div v-on:keyup="keyEvent" id="editor">

    </div>
  </div>


</template>

<script>
  import * as ace from 'brace'
  import 'brace/mode/java'
  import 'brace/theme/tomorrow'
  import 'brace/ext/language_tools'
  import '../../lib/sockjs.min'
  import '../../lib/stomp.min'

  export default {
    data: function () {
      return {
        code: ''
      }
    },
    mounted: function () {
      this.initAce()
    },
    created: function () {
      let socket = new SockJS('/codes')
      this.stompClient = Stomp.over(socket)
      this.stompClient.connect({}, this.onConnected)
    },
    methods: {
      initAce: function () {
        this.editorEl = document.getElementById('editor')
        this.editor = ace.edit('editor')
        ace.acequire('ace/ext/language_tools')
        ace.acequire('ace/snippets')
        this.editor.setOptions({
          enableBasicAutocompletion: false,
          enableLiveAutocompletion: true
        })
        this.editor.setTheme('ace/theme/tomorrow')
        this.editor.getSession().setMode('ace/mode/javascript')
        this.editorEl.style.fontSize = '18px'
      },
      onConnected: function (frame) {
        this.stompClient.subscribe('/topic', this.onMessageReceived)
      },
      onMessageReceived: function (payload) {
        let message = JSON.parse(payload.body)
        this.code = message.content
        this.editor.setValue(this.code)
        this.editor.clearSelection()
      },
      sendMessage: function (message) {
        let data = {
          content: message
        }
        this.stompClient.send('/app/hello', {}, JSON.stringify(data))
      },
      keyEvent: function () {
        this.code = this.editor.getValue()
        this.sendMessage(this.code)
      }
    }

  }


</script>

<style>
  #editor {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
  }


</style>
