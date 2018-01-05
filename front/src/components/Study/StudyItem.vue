<template>
  <div>
    <md-card>
      <md-card-media >
        <md-ripple>
        <img src="../../assets/cat.jpg" alt="Cover" style="width:200px;height:200px">
        </md-ripple>
      </md-card-media>

      <md-card-header-text >
        <div class="md-title">{{ study.title }}</div>
        <div class="md-subhead">{{ study.createdAt }}</div>
      </md-card-header-text>

      <md-card-actions>
        <md-button class="md-icon-button" v-on:click="clickStudy">
          <md-icon>favorite</md-icon>
        </md-button>

        <md-button class="md-icon-button">
          <md-icon>bookmark</md-icon>
        </md-button>

        <md-button class="md-icon-button">
          <md-icon>share</md-icon>
        </md-button>
      </md-card-actions>
    </md-card>
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
        console.log(this.study.id)
        this.$router.push('/studies/'+this.study.id)

      }
    }

  }
</script>

<style scoped>

</style>
