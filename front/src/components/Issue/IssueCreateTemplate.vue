<template>
  <div>
    <md-field>
      <label>Title</label>
      <md-input v-model="title" required></md-input>
    </md-field>
    <md-field>
      <label>Content</label>
      <md-textarea v-model="content" required></md-textarea>
    </md-field>
    <md-button class="md-raised" v-on:click="createIssue()">new issue</md-button>
  </div>
</template>

<script>
  export default {
    name: 'IssueCreateTemplate',
    data: function () {
      return {
        title: '',
        content: '',
        projectId: this.$route.params.projectId,
        baseUrl: '/api/issues'
      }
    },
    methods: {
      createIssue: function () {
        this.axios.post(this.baseUrl, {
          issue: {
            title: this.title,
            content: this.content
          },
          projectId: this.projectId
        })
        .then((result) => {
          const params = {
            projectId: this.projectId,
            issueId: result.data.id // created issue id
          }
          this.$router.push({name: 'Issue', params: params})
        })
        .catch((error) => {
          console.log(error)
        })
      }
    },
    created: function () {
      console.log('IssueTemplate created!')
    }
  }
</script>

<style>
  #content {
    width: 50%;
    margin: 0 auto;
  }

  #widget {
    right: 10%;
    up: 10%;
    position: fixed;
  }
</style>
