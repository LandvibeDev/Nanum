<template>
  <div>
    <md-field>
      <label>Content</label>
      <md-textarea v-model="issueComment.content" placeholder="Enter content"></md-textarea>
    </md-field>
    <md-button class="md-raised" v-on:click="updateIssue()">update comment</md-button>
  </div>
</template>

<script>
  export default {
    name: 'IssueCommentUpdateTemplate',
    data: function () {
      return {
        issueComment: {},
        issueId: this.$route.params.issueId,
        baseUrl: '/api/issue-comments/' + this.$route.params.issueCommentId
      }
    },
    methods: {
      updateIssue: function () {
        this.axios.patch(this.baseUrl, {
          content: this.issueComment.content
        })
          .then((result) => {
            console.log(result)
            const params = {
              issueId: this.issueId
            }
            this.$router.push({name: 'Issue', params: params})
          })
      }
    },
    created: function () {
      console.log('IssueCommentUpdateTemplate created!')
      this.axios.get(this.baseUrl)
        .then((result) => {
          console.log(result)
          this.issueComment = result.data
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
