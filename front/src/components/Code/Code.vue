<template>
  <div id="editor">


  </div>

</template>

<script>
  import * as ace from 'brace'
  import 'brace/mode/java'
  import 'brace/theme/tomorrow'
  import 'brace/ext/language_tools'

  export default {
    mounted:function() {
      this.initAce();
    },
    methods:{
      initAce:function(){
        this.editorEl = document.getElementById("editor")
        this.editor = ace.edit("editor");
        ace.acequire("ace/ext/language_tools");
        ace.acequire("ace/snippets")
        this.editor.setOptions({
          enableBasicAutocompletion: false,
          enableLiveAutocompletion: true
        })
        this.editor.setTheme("ace/theme/tomorrow");
        this.editor.getSession().setMode("ace/mode/javascript");
        this.editorEl.style.fontSize = "18px"
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
