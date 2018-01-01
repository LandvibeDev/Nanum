<template>
  <div id="study_menu">
    <b-btn v-b-toggle.collapse2 class="m-1">Menu</b-btn>
    <b-collapse id="collapse2">
      <b-list-group>
        <div>
          <b-list-group-item v-on:click="clickMenu('')" button>Home</b-list-group-item>
        </div>
        <div>
          <b-list-group-item v-on:click="clickMenu('/user')" button>User</b-list-group-item>
        </div>
        <div v-for="board in boards">
          <b-list-group-item v-on:click="clickMenu('/board/'+board.id,board.type)" button>{{ board.title }}</b-list-group-item>
        </div>
      </b-list-group>
    </b-collapse>
  </div>
</template>

<script>
  export default {
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
      clickMenu: function (routerPath,type) {
        const basePath = '/studies/' + this.studyId
        this.$store.commit('setType',type)
        this.$router.push(basePath + routerPath)
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
