<template>
  <div v-on:click="clickIssue">
      <p class='issue-date'>{{ issue.createdAt }}</p>
      <p> id: {{ issue.id }}</p>
      <h3> title: {{ issue.title }}</h3>
      <p> content: {{ issue.content }}</p>
  </div>
</template>

<script>
  export default {
    name: 'IssueItem',
    props: ['issue'],
    data: function () {
      return {}
    },
    methods: {
      clickIssue: function () {
        this.$router.push('/issues/' + this.issue.id) // -> /issues/new
      }
    }
  }
</script>

<style scoped>
  * {
    outline: none;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
  }

  .issue-date a {
    font-weight: 300;
    font-size: 14px;
  }
</style>
