<template>
  <div>
    <div class="viewport">
      <md-toolbar :md-elevation="1">
        <span class="md-title">Primary</span>
      </md-toolbar>

      <md-list>
        <md-subheader class="md-primary">Contacts</md-subheader>

        <md-list-item v-for="user in users">
          <md-avatar>
            <img :src= user.profileImage alt="People">
          </md-avatar>
          <span class="md-list-item-text">{{user.username}}</span>
          <md-button class="md-icon-button md-list-action">
            <md-icon class="md-primary">chat_bubble</md-icon>
          </md-button>
        </md-list-item>
      </md-list>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'ProjectChat',
    props: ['project'],
    data:function(){
      return{
        projectId : null,
        users : []
      }
    },
    created:function(){
      this.projectId = this.$route.params.projectId
      const baseUrl = '/api/projects/'+this.projectId + '/users'
      this.axios.get(baseUrl)
        .then((result) => {
          console.log(result)
          this.users = result.data
        })
    }
  }
</script>

<style scoped>

</style>
