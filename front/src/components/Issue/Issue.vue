<template>
  <div class="page-container">
    <md-app>
      <md-app-toolbar class="md-primary">
        <span class="md-title">{{issue.title}}</span>
      </md-app-toolbar>

      <md-app-drawer md-permanent="full">
        <md-toolbar class="md-transparent" md-elevation="0">
          Navigation
        </md-toolbar>
        <md-list>

          <md-list-item>
            <span class="md-list-item-text">createdAt: {{issue.createdAt}}</span>
          </md-list-item>

          <md-list-item>
            <span class="md-list-item-text">updatedAt: {{issue.updatedAt}}</span>
          </md-list-item>

          <!--<md-list-item>-->
            <!--<span class="md-list-item-text">user: {{issue.creator['username']}}</span>-->
          <!--</md-list-item>-->

          <md-list-item>
            <md-icon>move_to_inbox</md-icon>
            <md-button v-on:click="clickIssueCreate()">New Issue</md-button>
          </md-list-item>

          <md-list-item>
            <md-icon>send</md-icon>
            <md-button v-on:click="clickIssueUpdate()">Update Issue</md-button>
          </md-list-item>

          <md-list-item>
            <md-icon>delete</md-icon>
            <md-button v-on:click="clickIssueDelete()">Delete Issue</md-button>
          </md-list-item>

        </md-list>
      </md-app-drawer>

      <md-app-content>
        <p>{{issue.content}}</p>
      </md-app-content>
    </md-app>
  </div>
</template>

<script>
  export default {
    name: 'Issue',
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
