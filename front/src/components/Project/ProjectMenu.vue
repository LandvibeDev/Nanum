<template>
  <div id="project_menu">
    <md-speed-dial class="bottomPosition">
      <md-speed-dial-target>
        <md-icon>add</md-icon>
      </md-speed-dial-target>

      <md-speed-dial-content>
        <md-button class="md-icon-button" v-on:click="clickMenu({name:'ProjectMain'})">
         Home
        </md-button>
        <md-button class="md-icon-button" v-on:click="clickMenu({name:'ProjectUser'})">
          User
        </md-button>
        <md-button class="md-icon-button" v-on:click="clickMenu({name:'Code'})">
          Code
        </md-button>
        <md-button class="md-icon-button" v-for="board in boards"
                   v-on:click="clickMenu({name:'ProjectBoard',type:board.type,boardId:board.id})">
          {{board.title}}
        </md-button>
        <md-button class="md-icon-button" v-on:click="clickMenu({name:'IssueContainer'})">
          Issue
        </md-button>
          <!--<md-list-item v-on:click="clickMenu({name:'ProjectMain'})">Home</md-list-item>-->
          <!--<md-list-item v-on:click="clickMenu({name:'ProjectUser'})">User</md-list-item>-->
          <!--<md-list-item v-on:click="clickMenu({name:'Code'})">Code</md-list-item>-->
          <!--<md-list-item v-for="board in boards"-->
                        <!--v-on:click="clickMenu({name:'ProjectBoard',type:board.type,boardId:board.id})">-->
            <!--{{board.title}}-->
          <!--</md-list-item>-->
      </md-speed-dial-content>
    </md-speed-dial>
  </div>
</template>

<script>
  export default {
    name: 'ProjectMenu',
    props: ['projectId'],
    created: function () {
      const baseUrl = '/api/projects/'+this.projectId+'/boards'
      this.axios.get(baseUrl)
        .then((result) => {
          console.log(result)
          this.boards = result.data
        })
    },
    data: function () {
      return {
        boards: [],
        bottomPosition: 'md-bottom-left'
      }
    },
    methods: {
      clickMenu: function (option) {
        const params = {
          projectId: this.projectId,
          boardId: option.boardId
        }
        this.$store.commit('setType',option.type)
        this.$router.push({name: option.name, params: params})
      }
    }
  }
</script>

<style scoped>
  .bottomPosition{
    position:fixed;
    left:5%;
    bottom:10%;
    z-index: 9999;
  }

</style>
