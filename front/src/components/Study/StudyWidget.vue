<template>
  <div>
    <md-list>
      <md-list-item v-on:click="clickWidget('/writeBoard')">Home</md-list-item>
    </md-list>
  </div>
</template>

<script>
  export default {
    name : 'StudyWidget',
    props: ["studyId"],
    methods:{
      clickWidget:function(routerPath){
        const basePath = '/studies/' + this.studyId
        this.$router.push(basePath + routerPath)
      }
    }
  }
</script>

<style scoped>


</style>
