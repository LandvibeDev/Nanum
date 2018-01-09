<template>
  <div id="study_list">
    <div v-for="study in studies">
      <StudyItem :study="study"></StudyItem>
    </div>
  </div>
</template>

<script>
  import StudyItem from './StudyItem.vue'

  export default {
    components:{StudyItem},
    name: 'StudyList',
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
