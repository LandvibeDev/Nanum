<template>
  <div id="project_list">
    <md-speed-dial id="list_widget">
      <md-speed-dial-target @click="clickAdd">
        <md-icon>add</md-icon>
      </md-speed-dial-target>
    </md-speed-dial>
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
    },
    methods:{
      clickAdd: function(){
        this.$router.push({name:'ProjectAdd'})
      }

    }
  }
</script>

<style scoped>
  #project_list {
    width: 50%;
    margin: 0 auto;
  }

  #list_widget{
    position:fixed;
    left: 10%;
    top:10%;
  }


</style>
