<template>
  <div>

    <b-card title="Card title"
            sub-title="Card subtitle">
      <p class="card-text">
        일일 과제 : 영어단어 <em>10개</em>
      </p>
      <b-btn v-b-modal.modalPrevent>Launch demo modal</b-btn>
      <!-- Main UI -->
      <div class="mt-3 mb-3">
        Submitted Names:
        <ul>
          <li v-for="n in names">{{n}}</li>
        </ul>
      </div>
      <!-- Modal Component -->
      <b-modal id="modalPrevent"
               ref="modal"
               title="Submit your name"
               @ok="handleOk"
               @shown="clearName">
        <form @submit.stop.prevent="handleSubmit">
          <b-form-group horizontal
                        label="apple:"
                        label-class="text-sm-right">
            <b-form-input type="text"
                          v-model="name"></b-form-input>
          </b-form-group>
          <b-form-group horizontal
                        label="street:"
                        label-class="text-sm-right">
            <b-form-input type="text"
                          v-model="name"></b-form-input>
          </b-form-group>
        </form>
      </b-modal>
    </b-card>
    <b-card title="Card title"
            sub-title="Card subtitle">
      <p class="card-text">
        WHAT
      </p>
    </b-card>

  </div>
</template>

<script>
  export default {
    props: ['study'],
    data () {
      return {
        name: '',
        names: []
      }
    },
    methods: {
      clearName () {
        this.name = ''
      },
      handleOk (evt) {
        // Prevent modal from closing
        evt.preventDefault()
        if (!this.name) {
          alert('Please enter your name')
        } else {
          this.handleSubmit()
        }
      },
      handleSubmit () {
        this.names.push(this.name)
        this.clearName()
        this.$refs.modal.hide()
      }
    }
  }
</script>

<style scoped>


</style>
