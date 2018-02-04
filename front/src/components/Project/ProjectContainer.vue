<template>
  <div>
    <ProjectWidget id="widget" :projectId = "projectId"></ProjectWidget>
    <projectMenu :projectId = "projectId"></projectMenu>
    <div id="content">
      <router-view :project = "project" :key="$route.fullPath"/>
    </div>

  </div>
</template>

<script>
  import ProjectMenu from './ProjectMenu.vue'
  import ProjectWidget from './ProjectWidget.vue'

  export default {
    components:{ProjectMenu,ProjectWidget},
    name: 'Project',
    created: function () {
      this.projectId = this.$route.params.projectId
      const baseUrl = '/api/projects/' + this.projectId
      this.axios.get(baseUrl)
        .then((result) => {
          // console.log(result)
          this.project = result.data
        })
    },
    data: function () {
      return {
        project: {}
      }
    }

  }

</script>

<style>
  #content{
    width:75%;
    margin: 0 auto;
  }

  #widget{
    right : 10%;
    up : 10%;
    position : fixed;
  }

</style>
