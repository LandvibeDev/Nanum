<template>
  <div>
    <!--<StudyWidget id="widget" :issueId="issueId"></StudyWidget>-->
    <!--<issueMenu :issueId="issueId"></issueMenu>-->
    <!--<div id="content">-->
      <!--<router-view :issue="issue"/>-->
    <!--</div>-->
    <p>Issue!</p>
  </div>
</template>

<script>
  export default {
    name: 'Issue',
    created: function () {
      this.issueId = this.$route.params.issueId
      const baseUrl = '/api/issues/' + this.issueId
      this.axios.get(baseUrl)
        .then((result) => {
          console.log(result)
          this.issue = result.data
        })
    },
    data: function () {
      return {
        issue: {}
      }
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
