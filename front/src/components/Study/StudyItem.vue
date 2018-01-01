<template>
  <div>
    <b-card>
      <b-media v-on:click = "clickStudy">
        <b-img slot="aside" blank blank-color="#ccc" width="64" alt="placeholder" />
        <h5 class="mt-0">{{ study.title }}</h5>
        <p>
          {{ study.createdAt }}
        </p>
      </b-media>
    </b-card>
  </div>

</template>

<script>
  export default {
    name: 'Study',
    props: ['study'],
    data: function () {
      return {}
    },
    methods:{
      clickStudy : function(){

        //this.$router.push({name:"Study",params:{id:this.study.id}})
        this.$router.push('/studies/'+this.study.id)

      }
    }

  }
</script>

<style scoped>

</style>
