<template>
  <div>
    <b-card-group deck>
      <b-card img-src="https://placekitten.com/1000/300"
              img-alt="Card image"
              img-top>
        <p class="card-text">
          {{ study.title }}
        </p>
      </b-card>
    </b-card-group>
  </div>

</template>

<script>
  export default {
    name: 'Study',
    props: ['study'],
    data: function () {
      return {}
    }

  }
</script>

<style scoped>

</style>
