<template>
  <div>
    <b-dropdown variant="link" size="lg" no-caret>
      <template slot="button-content">
        &#x1f50d;<span class="sr-only">Search</span>
      </template>

      <b-dropdown-item>Write</b-dropdown-item>
      <b-dropdown-item>Live</b-dropdown-item>
      <b-dropdown-item>Chat</b-dropdown-item>
    </b-dropdown>
  </div>
</template>

<script>
  export default {
    props: ["studyId"]
  }
</script>

<style scoped>


</style>
