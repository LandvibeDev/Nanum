<template>
  <div id="project_list">
    <div v-for="project in projects">
      <ProjectItem :project="project"></ProjectItem>
    </div>
  </div>
</template>

<script>
  import ProjectItem from './ProjectItem.vue'

  export default {
    components:{ProjectItem},
    name: 'ProjectList',
    created: function () {
      const baseUrl = '/api/projects/'
      this.axios.get(baseUrl)
        .then((result) => {
          console.log(result)
          this.projects = result.data
        })
    },
    data: function () {
      return {
        projects: []
      }
    }

  }
</script>

<style scoped>
  #project_list {
    width: 50%;
    margin: 0 auto;
  }


</style>
