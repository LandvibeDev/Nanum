<template>
  <div>
    <b-card>
      <b-media>
        <b-img slot="aside" blank blank-color="#ccc" width="64" alt="placeholder" />
        <h5 class="mt-0">Main</h5>
        <p>
          {{ study.title }}
        </p>
        <p>
          {{ study.createdAt }}
        </p>
      </b-media>
    </b-card>
  </div>
</template>

<script>
  export default {
    props:["study"]
  }
</script>

<style scoped>

</style>
