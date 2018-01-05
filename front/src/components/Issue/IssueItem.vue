<template>
  <div>
    <b-media v-on:click="clickIssue">
      <p class='issue-date'>{{ issue.createdAt }}</p>
      <p>{{ issue.slug }}</p>
      <h3>{{ issue.title }}</h3>
      <p>{{ issue.teaser }}</p>
    </b-media>
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
        // this.$router.push({name:"Study",params:{id:this.study.id}})
        this.$router.push('/issues/' + this.issue.id)
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
