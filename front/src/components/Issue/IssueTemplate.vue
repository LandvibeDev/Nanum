<template>
  <div>
    Title: <input v-model="title" placeholder="여기를 수정해보세요">
    Message: <textarea v-model="content" placeholder="내용"></textarea>
    <md-button v-on:click="createIssue()">new issue</md-button>
  </div>
</template>

<script>
  export default {
    name: 'IssueTemplate',
    data: function () {
      return {
        title: '',
        content: '',
        projectId: this.$route.params.projectId
      }
    },
    methods: {
      createIssue: function () {
        // const baseUri = '/api/projects/' + this.project.id + '/issues'
        const baseUri = '/api/issues'
        this.axios.post(baseUri, {
          issue: {
            title: this.title,
            content: this.content
          },
          projectId: this.projectId
        })
        .then((response) => {
          // console.log(response)
          const params = {
            projectId: this.projectId,
            issueId: response.data.id // created issue id
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
