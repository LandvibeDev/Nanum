<template>
  <div>
    Title: <input v-model="title" placeholder="여기를 수정해보세요">
    Message: <textarea v-model="content" placeholder="내용"></textarea>
    <button v-on:click="createIssue()">new issue</button>
  </div>
</template>

<script>
  export default {
    name: 'IssueTemplate',
    data: function () {
      return {
        title: '',
        content: ''
      }
    },
    methods: {
      createIssue: function () {
        const baseUri = '/api/issues'
        this.axios.post(baseUri, {
          title: this.title,
          content: this.content
        })
        .then((response) => {
          // console.log(response)
          let createdIssueId = response.data.id
          this.$router.push({path: `/issues/${createdIssueId}`})
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
