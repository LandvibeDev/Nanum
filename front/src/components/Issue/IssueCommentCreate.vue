<template>
  <div>
    <md-field>
      <label>Content</label>
      <md-textarea v-model="content" required></md-textarea>
    </md-field>
    <md-button class="md-raised" v-on:click="createIssueComment()">new</md-button>
  </div>
</template>

<script>
  export default {
    name: 'IssueCommentCreate',
    methods: {
      createIssueComment: function () {
        this.axios.post(this.baseUrl, {
          issueComment: {
            content: this.content.split('\n').join('<br>')
          },
          issueId: this.issueId
        })
          .then((result) => {
            // 부모 컴포넌트의 addIssueComment를 호출하고 data를 전달하여 comment 추가
            this.$emit('addIssueComment', result.data)
            this.content = ''
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

<style>

</style>
