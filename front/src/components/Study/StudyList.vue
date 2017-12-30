<template>
  <div id="study_list">
    <div v-for="study in studies">
      <Study :study="study"></Study>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'studyList',
    created: function () {
      const baseUrl = '/api/studies/'
      this.axios.get(baseUrl)
        .then((result) => {
          console.log(result)
          this.studies = result.data
        })
    },
    data: function () {
      return {
        studies: []
      }
    }

  }
</script>

<style scoped>
  #study_list {
    width: 50%;
    margin: 0 auto;
  }


</style>
