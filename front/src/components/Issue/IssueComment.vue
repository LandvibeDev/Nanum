<template>
  <div>
    <md-card>
      <md-ripple>
        <md-card-header>
          <!--<div class="md-title">Card with hover effect</div>-->
          <div class="md-subhead">{{this.issueComment.id}}
            created {{this.issueComment.createdAt}} By {{this.issueComment.creator.username}}</div>
        </md-card-header>

        <md-card-content v-html="this.issueComment.content">
          <!--{{this.issueComment.content}}-->
        </md-card-content>

        <md-card-actions>
          <i class="material-icons">mode_edit</i>
          <md-button v-on:click="updateIssueComment()">update</md-button>
          <i class="material-icons">clear</i>
          <md-button v-on:click="deleteIssueComment()">delete</md-button>
        </md-card-actions>
      </md-ripple>
    </md-card>
  </div>
</template>

<script>
  export default {
    name: 'IssueComment',
    props: ['issueComment', 'commentIndex'],
    methods: {
      updateIssueComment: function () {
        const params = {
          issueId: this.issueId,
          issueCommentId: this.issueComment.id
        }
        this.$router.push({name: 'IssueCommentUpdateTemplate', params: params})

        // this.axios.patch(this.baseUrl + '/' + this.issueComment.id, {
        //   issueComment: {
        //     content: this.content
        //   }
        // })
        //   .then((result) => {
        //     const params = {
        //       issueId: this.issueId
        //     }
        //     this.$router.replace({name: 'Issue', params: params})
        //   })
      },
      deleteIssueComment: function () {
        this.axios.delete(this.baseUrl + '/' + this.issueComment.id)
          .then((result) => {
            this.$emit('deleteComment', this.commentIndex)
          })
      }
    },
    data: function () {
      return {
        content: '',
        issueId: this.$route.params.issueId,
        baseUrl: '/api/issue-comments'
      }
    }
  }
</script>

<style scoped>


</style>
