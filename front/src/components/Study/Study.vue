<template>
  <div>
    <studyMenu :studyId = "studyId"></studyMenu>
    <div id="content">
      <router-view :study = "study"/>
    </div>

  </div>
</template>

<script>
  export default {
    name: 'Study',
    created: function () {
      this.studyId = this.$route.params.id
      const baseUrl = '/api/studies/' + this.studyId
      this.axios.get(baseUrl)
        .then((result) => {
          console.log(result)
          this.study = result.data
        })
    },
    data: function () {
      return {
        study: {}
      }
    }

  }

</script>

<style>
  #content{
    width:50%;
    margin: 0 auto;
  }

</style>
