<template>
  <li class="file_item" >
    <span class="fa-li">
      <i v-if="!sync" :class="icon"></i>
      <md-progress-spinner v-else :md-diameter="10" :md-stroke="1" md-mode="indeterminate"></md-progress-spinner>
    </span>
    <span v-show="sync">

    </span>
    <span
      @dragover="onDragOver"
      @dragstart="onDragStart"
      @drop="onDrop"
      draggable="true"
      @click="toggle"
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
//  @example
//  model = {
//    text: ""
//    type: ""
//    children: []
//    path: ""
//  }
  export default {
    name: 'CodeTree',
    props: ['model','projectId'],
    data: function () {
      return {
        open: false,
        icon: "",
        selected: "",
        sync:false
      }
    },
    watch: {
      model: function(){
        if(this.isFolder){
          this.icon = "fas fa-folder folder"
          this.model.children = []
        }else{
          this.icon = "fas fa-file file"
        }
      }
    },
    created:function(){
      this.checkModifying()
      if(this.isFolder){
        this.icon = "fas fa-folder folder"
        this.model.children = []
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
      getFileList: function(){
        const param = {
          params: {
            path: this.model.path
          }
        }
        return this.axios.get('/api/projects/'+this.projectId+'/directories/' + this.model.text, param).then((result) => {
          if(result.data){
            this.model.childFlag = true
            this.model.children = this.filterFile(result)
            return new Promise((resolve,reject)=>{resolve()})
          }
        }).catch((error) => {
          console.log(error)
        })
      },
      getFileListAndOpenDirectory:function(){
        this.getFileList().then((result) => {
          this.openDirectory()
        }).catch((error) => {
          console.log(error)
        })
      },
      filterFile(result){
        let temp = result.data.filter((file) => {
          file.path = this.model.path + '/' + this.model.text
          return file
        })
        return temp
      },
      add: function (text,isDirectory) {
        if(this.isFolder){
          let url = '/api/projects/'+this.projectId + '/files/' + text
          let fileType = isDirectory ? 'DIRECTORY':'FILE'
          const data={
            path:this.model.path + '/' + this.model.text,
            type:fileType
          }
          this.axios.post(url,data).then((result)=>{
            console.log(result)
            this.getFileList().then(()=>{
              this.$forceUpdate()
            })
          })
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
          this.$parent.getFileList().then(()=>{
            this.$parent.$forceUpdate()
          })
          console.log(result)
        })
      },
      update:function(content){
        let url = '/api/projects/'+this.projectId + '/files/' + this.model.text
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
              console.log('Move')
              dragFile.$parent.getFileList().then(()=>{
                this.getFileList().then(()=>{
                  dragFile.$parent.$forceUpdate()
                  this.$forceUpdate()
                })
              })
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
      syncFile: function(file){
        if(this.model.text == file.filename && this.model.path == file.path){
          this.sync = !this.sync
          console.log('Sync!!')
          return true
        }
        if(this.isFolder){
          let length = this.$children.length
          for(let i=0;i<length;i++){
            if(this.$children[i].model){
              if(this.$children[i].syncFile(file)){
                return true
              }
            }

          }
        }

      },
      checkModifying(){
        let files = this.$store.getters.modifyingFiles
        let length = files.length
        for(let i=0;i<length;i++){
          this.syncFile(files[i])
        }
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
