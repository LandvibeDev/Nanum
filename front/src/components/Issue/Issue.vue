<template>
  <div class="page-container">
    <md-app>
      <md-app-toolbar class="md-primary">
        <p class="md-title">{{issue.title}}</p>
      </md-app-toolbar>

      <!-- 본문 -->
      <md-app-content>
        <i class="material-icons">add_box</i>
        <md-button v-on:click="clickIssueCreate()">New</md-button>
        <i class="material-icons">mode_edit</i>
        <md-button v-on:click="clickIssueUpdate()">Update</md-button>
        <i class="material-icons">clear</i>
        <md-button v-on:click="clickIssueDelete">delete</md-button>

        <p>created {{issue.createdAt}} By {{issue.creator.username}}</p>
        <p>updatedAt: {{issue.updatedAt}}</p>

        <!-- comments -->
        <div v-for="issueComment, index in issueComments">
          <IssueComment class='comment' :issueComment="issueComment" :commentIndex="index" v-on:deleteComment="deleteComment"></IssueComment><br>
        </div>
        <div>
          <IssueCommentCreateTemplate v-on:addComment="addComment"></IssueCommentCreateTemplate>
        </div>
      </md-app-content>
    </md-app>
  </div>
</template>

<script>
  import IssueComment from './IssueComment.vue'
  import IssueCommentCreateTemplate from './IssueCommentCreateTemplate.vue'

  export default {
    name: 'Issue',
    components: {IssueComment, IssueCommentCreateTemplate},
    methods: {
      clickIssueCreate: function () {
        const params = {
          projectId: this.projectId
        }
        this.$router.push({name: 'IssueCreateTemplate', params: params})
      },
      clickIssueUpdate: function () {
        const params = {
          projectId: this.projectId,
          issueId: this.issueId
        }
        this.$router.push({name: 'IssueUpdateTemplate', params: params})
      },
      clickIssueDelete: function () {
        this.axios.delete(this.baseUrl)
          .then((result) => {
            // console.log(result)
            this.$router.replace({name: 'IssueList'})
          })
      },
      addComment: function (data) {
        // console.log(data)
        this.issueComments.push(data)
      },
      deleteComment: function (commentIndex) {
        this.$delete(this.issueComments, commentIndex) // Only in 2.2.0+:
        // this.issueComments.splice(commentIndex, 1)
      }
    },
    data: function () {
      return {
        issue: {
          creator: {
            username: ''
          }
        },
        baseUrl: '',
        projectId: this.$route.params.projectId,
        issueId: '',
        issueComments: []
      }
    },
    created: function () {
      this.issueId = this.$route.params.issueId
      this.baseUrl = '/api/issues/' + this.issueId
      this.axios.get(this.baseUrl)
        .then((result) => {
          // console.log(result)
          this.issue = result.data
          this.issueComments = this.issue.issueComments
          console.log(this.issueComments)
        })
    }
  }
</script>

<style scoped>
  #content {
    width: 50%;
    margin: 0 auto;
  }

  #widget {
    right: 10%;
    up: 10%;
    position: fixed;
  }

  .comment {
    text-align: left
  }

  /*.md-app {*/
  /*max-height: 400px;*/
  /*border: 1px solid;*/
  /*}*/

  /*!* Demo purposes only*!*/
  /*.md-drawer {*/
  /*width: 230px;*/
  /*max-width: calc(100vw - 125px);*/
  /*}*/

</style>
