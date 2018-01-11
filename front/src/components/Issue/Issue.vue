<template>
  <div>
    <button v-on:click="clickIssueCreate()">Create Issue</button>
    <button v-on:click="clickIssueUpdate()">Update Issue</button>
    <button v-on:click="clickIssueDelete()">Delete Issue</button>
    <p>title {{issue.title}}</p>
    <p>content: {{issue.content}}</p>
    <p>createdAt: {{issue.createdAt}}</p>
    <p>updatedAt: {{issue.updatedAt}}</p>
    <p>user: {{issue.creator}}</p>
    <p>Issue!</p>
  </div>
</template>

<script>
  export default {
    name: 'Issue',
    methods: {
      clickIssueCreate: function () {
        // this.$router.push('/issues/new')
        this.$router.replace('/issues/new')
        // this.$router.push({path: '/issues/new'}) // -> /issues/new
      },
      clickIssueUpdate: function () {
        // go IssueTemplate component with issue data
      },
      clickIssueDelete: function () {
        this.axios.delete(this.baseUri)
        .then((result) => {
          console.log(result)
          this.$router.replace({name: 'IssueContainer'})
        })
        // .catch((e) => {
        //   console.log(e)
        // })
        // .error((err) => {
        //   console.log(err)
        // })
      }
    },
    data: function () {
      return {
        issue: {},
        baseUri: ''
      }
    },
    created: function () {
      this.issueId = this.$route.params.issueId
      this.baseUrl = '/api/issues/' + this.issueId
      this.axios.get(this.baseUrl)
        .then((result) => {
          console.log(result)
          this.issue = result.data
        })
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
