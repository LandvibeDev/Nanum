<template>
  <div id ="study_menu">
    <b-list-group>
      <b-list-group-item><router-link :to="{path: '/studies/'+this.studyId }">홈</router-link></b-list-group-item>
      <b-list-group-item><router-link :to="{path: '/studies/'+this.studyId + '/board'}">게시판</router-link></b-list-group-item>
      <b-list-group-item>기타</b-list-group-item>
      <b-list-group-item>설정</b-list-group-item>
    </b-list-group>
  </div>
</template>

<script>
  export default {
    props: ["studyId"]
  }
</script>

<style scoped>
  #study_menu{
    left:0;
    width:20%;
    position: absolute;
  }

</style>
