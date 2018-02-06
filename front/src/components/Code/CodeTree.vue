<template>
  <li class="file_item" >
    <span class="fa-li">
      <i :class="icon"></i></span>
    <span
      @dragover="onDragOver"
      @dragstart="onDragStart"
      @drop="onDrop"
      draggable="true"
      @click="toggle"
      @dblclick="changeType"
      :class="selected">
      {{model.text}}
    </span>

    <transition name="slide-fade" >
      <ul v-show="open" v-if="isFolder" class="fa-ul tree-ul">
        <CodeTree
          class="item"
          v-for="(model, index) in model.children"
          :key="index"
          v-on:clickFile="emitFile"
          :model="model"
          :projectId="projectId">
        </CodeTree>
      </ul>
    </transition>
  </li>
</template>

<script>
  export default {
    name: 'CodeTree',
    props: ['model','projectId'],
    data: function () {
      return {
        open: false,
        icon: "",
        selected: ""
      }
    },
    created:function(){
      if(this.isFolder){
        this.icon = "fas fa-folder folder"
        this.children = []
      }else{
        this.icon = "fas fa-file file"
      }
    },
    computed: {
      isFolder: function () {
        return this.model.type === 'DIRECTORY'
      }
    },
    methods: {
      toggle: function () {
        console.log(this)
        this.selected = "selected"
        if (this.isFolder) {
          if (this.model.childFlag == undefined) {
            this.getFileListAndOpenDirectory()
          }else{
            this.openDirectory();
          }
        }
        this.emitFile(this)

      },
      openDirectory:function(){
        this.open = !this.open
        if(this.open){
          this.icon = "fas fa-folder-open folder"
        }else{
          this.icon = "fas fa-folder folder"
        }
      },
      emitFile:function(node){
        this.$emit("clickFile",node)
      },
      getFileListAndOpenDirectory:function(){
        const param = {
          params: {
            path: this.model.path
          }
        }
        this.axios.get('/api/projects/'+this.projectId+'/directories/' + this.model.text, param).then((result) => {
          if(result.data){
            let temp = result.data.filter((file) => {
              file.path = this.model.path + '/' + this.model.text
              return file
            })
            this.model.childFlag = true
            this.model.children = temp
            this.openDirectory()
          }
        }).catch((error) => {
          console.log(error)
        })
      },
      changeType: function () {
        if (!this.isFolder) {
          Vue.set(this.model, 'children', [])
          this.addChild()
          this.open = true
        }
      },
      add: function (text,isDirectory) {
        if(this.isFolder){
          let url = '/api/projects/'+this.projectId + '/files/' + text
          let fileType = isDirectory ? 'DIRECTORY':'FILE'
          const file = {
            text: text,
            type: fileType,
            path: this.model.path + '/' + this.model.text
          }
          const data={
            path:this.model.path + '/' + this.model.text,
            type:fileType
          }
          this.model.children.push(file)
          this.$forceUpdate()
          this.axios.post(url,data).then((result)=>{
            console.log(result)
          })
        }else{
          this.$parent.add(text)
        }
      },
      delete: function(){
        let url = '/api/projects/'+this.projectId + '/files/' + this.model.text
        const param = {
          params: {
            path:this.model.path
          }
        }
        this.axios.delete(url,param).then((result)=>{
          this.$parent.model.children.splice(this.$parent.model.children.indexOf(this.model),1)
          this.$parent.$forceUpdate()
          console.log(result)
        })
      },
      update:function(content){
        let url = '/api/projects/'+this.projectId + '/files/' + this.model.text
        const file = {
          text: this.model.text,
          type: 'FILE',
          path: this.model.path
        }
        const data = {
          path:this.model.path,
          content:content
        }
        this.axios.put(url,data).then((result)=>{
          console.log(result)
          this.$toasted.show('저장되었습니다.',{duration:2000})
        })
      },
      onDrop:function(e){
        e.preventDefault()
        if(this.isFolder){
          let dragFile = this.$store.getters.dragFile
          if(dragFile.$parent === this){
            return;
          }
          if(dragFile) {
            console.log(dragFile)
            let param = {
              projectId: this.projectId,
              newPath: this.model.path + '/' + this.model.text,
              path: dragFile.model.path,
              fileName: dragFile.model.text,
              fileType: dragFile.model.type
            }
            this.$store.dispatch('moveDragFile',param).then((result)=>{
              console.log('Move :')
              dragFile.$parent.model.children.splice(dragFile.$parent.model.children.indexOf(dragFile.model),1)
              this.model.children.push(dragFile.model)
              if(dragFile.model.type === 'FILE'){
                dragFile.model.path = this.model.path + '/' + this.model.text
              }else if(dragFile.model.type === 'DIRECTORY'){
                dragFile.model.path = this.model.path + '/' + this.model.text
                dragFile.changeChildrenPath()
              }
              dragFile.$parent.$forceUpdate()
              this.$forceUpdate()
            })

          }
        }
      },
      onDragStart:function(e){
        this.$store.commit('setDragFile',this)
      },
      onDragOver:function(e){
        if(this.isFolder){
          e.preventDefault()
        }
      },
      changeChildrenPath(){
        function changePath(file){
          for(let child in file.children){
            child.path = file.path + '/' + file.text
            if(child.type === 'DIRECTORY'){
              this.changePath(child)
            }
          }
        }
        changePath(this.model)
      }
    }
  }
</script>

<style scoped>
  .file_item{
    text-align: left;
    font-size: 15px;
    padding-top: 4px;
  }

  .item {
    cursor: pointer;
  }

  .selected{
    background-color: lightgrey;
  }

  .folder{
    color: #5b7bd5;
  }

  .file{
    color: #7f7f7f;
  }

  .slide-fade-enter-active, .slide-fade-leave-active {
    transition: all .3s ease;
  }
  .slide-fade-enter, .slide-fade-leave-to
    /* .slide-fade-leave-active below version 2.1.8 */ {
    transform: translateY(-10px);
    opacity: 0;
  }
</style>
