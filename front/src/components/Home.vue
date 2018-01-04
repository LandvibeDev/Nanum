<template>
  <div>
    <div id = "home">
      <b-button v-on:click = "clickButton('StudyList')">Study</b-button>
      <b-button v-on:click = "clickButton('Code')">Code</b-button>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Home',
    methods:{
      clickButton:function(routerName){
        this.$router.push({name:routerName})
      }
    }
  }
</script>

<style scoped>

</style>
