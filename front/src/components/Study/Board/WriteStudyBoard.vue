<template>
  <div>
    <div>
      <b-form-input v-model="name"
                    type="text"
                    placeholder="Enter name"></b-form-input>
      <b-form-select v-model="selected" :options="options" class="mb-3">
      </b-form-select>
    </div>
    <b-button variant="danger">Make</b-button>
  </div>

</template>

<script>
  export default {
    name: 'Study',
    props: ['study'],
    data: function () {
      return {
        name:"",
        selected: null,
        options: [
          { value: null, text: 'select template' },
          { value: 'posts', text: 'posts' },
          { value: 'code', text: 'code' },
          { value: 'issue', text: 'issue' }
        ]
      }
    }

  }
</script>

<style scoped>

</style>
