<template>
  <div id="study_menu">
    <md-list>
      <md-list-item v-on:click="clickMenu({path:''})">Home</md-list-item>
      <md-list-item v-on:click="clickMenu({path:'user'})">User</md-list-item>
      <md-list-item v-for="board in boards" v-on:click="clickMenu({path:'board/'+ board.id,type:board.type})">{{board.title}}</md-list-item>
    </md-list>
  </div>
</template>

<script>
  export default {
    name: 'StudyMenu',
    props: ['studyId'],
    created: function () {
      const baseUrl = '/api/studies/'+this.studyId+'/boards'
      this.axios.get(baseUrl)
        .then((result) => {
          console.log(result)
          this.boards = result.data
        })
    },
    data: function () {
      return {
        boards: []
      }
    },
    methods: {
      clickMenu: function (params) {
        const basePath = '/studies/' + this.studyId +'/'
        this.$store.commit('setType',params.type)
        this.$router.push(basePath + params.path)
      }
    }
  }
</script>

<style scoped>
  #study_menu {
    left: 0;
    width: 20%;
    position: absolute;
  }

</style>
