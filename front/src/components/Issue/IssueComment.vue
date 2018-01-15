<template>
  <div v-if="isTemplate === false">
    <md-card>
      <md-ripple>
        <md-card-header>
          <!--<div class="md-title">Card with hover effect</div>-->
          <div class="md-subhead">{{this.issueComment.id}}
            created {{this.issueComment.createdAt}} By {{this.issueComment.creator.username}}</div>
        </md-card-header>

        <md-card-content>
          {{this.issueComment.content}}
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
  <div v-else>
    <md-field>
      <label>Content</label>
      <md-textarea v-model="content" required></md-textarea>
    </md-field>
    <md-button class="md-raised" v-on:click="createIssueComment()">new</md-button>
  </div>
</template>

<script>
  export default {
    name: 'IssueComment',
    props: ['issueComment', 'isTemplate'],
    methods: {
      createIssueComment: function () {
        this.axios.post(this.baseUrl, {
          issueComment: {
            content: this.content
          },
          issueId: this.issueId
        })
          .then((result) => {
            console.log(result.data)
            this.$emit('addComment', result.data)
            this.content = ''
            // const params = {
            //   issueId: this.issueId
            // }
            // this.$router.replace({name: 'ProjectList', params: params})
            // !temp code
            // this.$router.go({
            //   path: '/a/1',
            //   force: true
            // })
          })
      },
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
            // console.log(result)
            // const params = {
            //   issueId: this.issueId
            // }
            // this.$router.push({name: 'Issue', params: params})
            // !temp code
            this.$router.go({
              path: '/a/1',
              force: true
            })
            // this.$emit('deleteComment', this.issueComment.id)
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
