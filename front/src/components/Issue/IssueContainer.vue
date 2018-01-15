<template>
  <div>
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    name: 'IssueContainer',
    data: function () {
      return {
        isCreateIssue: false,
        isCreqteIssueComment: false
      }
    }
  }
</script>

<style scoped>
</style>
