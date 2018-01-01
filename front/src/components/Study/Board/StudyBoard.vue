<template>
  <div>
    <div v-if="type === 1">
      <PostTemplate></PostTemplate>
    </div>
    <div v-else-if="type === 2">
      B
    </div>
    <div v-else-if="type === 'C'">
      C
    </div>
    <div v-else>
      Not A/B/C
    </div>

  </div>
</template>

<script>
  export default {
    props: ['study'],
    created:function(){
      this.type = this.$store.state.type
      if(this.type === 0){
        this.axios.get('/api/boards/'+this.$route.params.boardId).then((result)=>{
          this.type = result.data.type
        })
      }
    },
    data:function(){
      return {
        type: ""
      }
    }
  }
</script>

<style scoped>


</style>
