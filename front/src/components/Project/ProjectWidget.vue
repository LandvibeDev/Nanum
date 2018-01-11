<template>
  <div>
    <md-list>
      <md-list-item v-on:click="clickWidget('/writeBoard')">Home</md-list-item>
    </md-list>
  </div>
</template>

<script>
  export default {
    name : 'ProjectWidget',
    props: ["projectId"],
    methods:{
      clickWidget:function(routerPath){
        const basePath = '/projects/' + this.projectId
        this.$router.push(basePath + routerPath)
      }
    }
  }
</script>

<style scoped>


</style>
