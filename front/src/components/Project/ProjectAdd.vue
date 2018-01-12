<template>
  <div id ="add_project">
    <md-field>
      <label>Title</label>
      <md-input v-model="title"></md-input>
    </md-field>
    <md-field>
      <label>Description</label>
      <md-input v-model="description"></md-input>
    </md-field>
    <md-button class="md-raised md-accent" v-on:click="clickAdd">Add</md-button>
  </div>
</template>

<script>
export default {
  data:function(){
    return {
      title : "",
      description: ""
    }
  },
  methods:{
    clickAdd: function(){
      const baseUri = '/api/projects'
      this.axios.post(baseUri, {
        title : this.title,
        description : this.description
      }).then((response) => {
          console.log(response)
          const params = {
            projectId : response.data.id
          }
          this.$router.push({name: 'ProjectMain', params: params})
        })
        .catch((error) => {
          console.log(error)
        })
    }
  }

}
</script>

<style scoped>
  #add_project {
    width: 50%;
    margin: 0 auto;
  }

</style>
