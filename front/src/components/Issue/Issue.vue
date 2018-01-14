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
        <div v-for="issueComment in this.issue.issueComments">
          <IssueComment :issueComment="issueComment" :isTemplate="false"></IssueComment><br>
        </div>
        <div>
          <!--issue Comment template here for create-->
          <IssueComment :isTemplate="true"></IssueComment>
        </div>
      </md-app-content>
    </md-app>
  </div>
</template>

<script>
  import IssueComment from './IssueComment'

  export default {
    name: 'Issue',
    components: {IssueComment},
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
            console.log(result)
            this.$router.replace({name: 'IssueList'})
          })
      }
    },
    data: function () {
      return {
        issue: {},
        baseUrl: '',
        projectId: this.$route.params.projectId,
        issueId: ''
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
