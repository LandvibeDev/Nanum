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
  import PostTemplate from './Template/PostTemplate.vue'

  export default {
    components:{PostTemplate},
    props: ['project'],
    created:function(){
      this.type = this.$store.state.boardType
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
