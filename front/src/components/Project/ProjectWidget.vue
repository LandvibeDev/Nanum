<template>
  <div>
    <transition name="component-fade" mode="out-in">
      <component id="chat" v-bind:is="view"></component>
    </transition>
    <md-speed-dial id="project_widget">
      <md-speed-dial-target @click="clickChat">
        <md-icon class="md-primary">chat_bubble</md-icon>
      </md-speed-dial-target>
    </md-speed-dial>
  </div>
</template>

<script>
  import ProjectChat from './ProjectChat.vue'

  export default {
    components:{ProjectChat},
    name: 'ProjectWidget',
    props: ['projectId'],
    data: function(){
      return {
        users:[],
        view:null,
        chatShow:false
      }
    },
    methods: {
      clickChat : function(){
        if(this.view ===null){
          this.view = ProjectChat
        }else{
          this.view =null
        }

      }
    }
  }
</script>

<style scoped>
  #project_widget{
    position:fixed;
    right:5%;
    bottom:10%;
    z-index: 9999;
  }

  #chat{
    position:fixed;
    right:5%;
    bottom:15%;
    z-index: 9999;
  }

  .component-fade-enter-active, .component-fade-leave-active {
    transition: opacity .3s ease;
  }
  .component-fade-enter, .component-fade-leave-to
    /* .component-fade-leave-active below version 2.1.8 */ {
    opacity: 0;
  }

</style>
