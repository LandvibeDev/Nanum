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
        <div v-for="comment, index in comments">
          <IssueComment class='comment' :comment="comment" :commentIndex="index" v-on:deleteComment="deleteComment"></IssueComment><br>
        </div>
        <div>
          <IssueCommentCreate v-on:addComment="addComment"></IssueCommentCreate>
        </div>
      </md-app-content>
    </md-app>
  </div>
</template>

<script>
  import IssueComment from './IssueComment.vue'
  import IssueCommentCreate from './IssueCommentCreate.vue'

  export default {
    name: 'Issue',
    components: {IssueComment, IssueCommentCreate},
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
        this.comments.push(data)
      },
      deleteComment: function (commentIndex) {
        this.$delete(this.comments, commentIndex) // Only in 2.2.0+:
        // this.comments.splice(commentIndex, 1)
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
        comments: []
      }
    },
    created: function () {
      this.issueId = this.$route.params.issueId
      this.baseUrl = '/api/issues/' + this.issueId
      this.axios.get(this.baseUrl)
        .then((result) => {
          // console.log(result)
          this.issue = result.data
          this.comments = this.issue.comments
          console.log(this.comments)
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

  .page-container {
    overflow: scroll
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
