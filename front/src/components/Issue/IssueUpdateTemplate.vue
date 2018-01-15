<template>
  <div>
    <md-field>
      <label>Title</label>
      <md-input v-model="issue.title" placeholder="Enter title"></md-input>
    </md-field>
    <md-field>
      <label>Content</label>
      <md-textarea v-model="issue.content" placeholder="Enter content"></md-textarea>
    </md-field>
    <md-button class="md-raised" v-on:click="updateIssue()">update issue</md-button>
  </div>
</template>

<script>
  export default {
    name: 'IssueUpdateTemplate',
    data: function () {
      return {
        issue: {},
        projectId: this.$route.params.projectId,
        baseUrl: '/api/issues/' + this.$route.params.issueId
      }
    },
    methods: {
      updateIssue: function () {
        this.axios.patch(this.baseUrl, {
          title: this.issue.title,
          content: this.issue.content.split('\n').join('<br>')
        })
        .then((result) => {
          console.log(result)
          const params = {
            projectId: this.projectId,
            issueId: result.data.id // created issue id
          }
          this.$router.push({name: 'Issue', params: params})
        })
      }
    },
    created: function () {
      console.log('IssueUpdateTemplate created!')
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
