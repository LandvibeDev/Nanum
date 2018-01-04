<template>
  <div>
    <b-dropdown variant="link" size="lg" no-caret>
      <template slot="button-content">
        &#x1f50d;<span class="sr-only">Search</span>
      </template>

      <b-dropdown-item v-on:click="clickWidget('/writeBoard')">MakeBoard</b-dropdown-item>
      <b-dropdown-item>Write</b-dropdown-item>
      <b-dropdown-item>Chat</b-dropdown-item>
    </b-dropdown>
  </div>
</template>

<script>
  export default {
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
