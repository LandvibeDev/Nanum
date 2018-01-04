<template>
  <div>
    <div id = "home">
      <b-button v-on:click = "clickButton('StudyList')">Study</b-button>
      <b-button v-on:click = "clickButton('Code')">Code</b-button>
      <div class="hello">
        <div v-if="this.isLogined === 'true'">
          <p> welcome {{ username }} !</p>
          <b-button v-on:click="logout">logout</b-button>
        </div>
        <div v-else>
          <b-button v-on:click="login">login</b-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Home',
    created: function () {
      this.isLogined = this.getCookieValue('isLogin')
      this.username = this.getCookieValue('username')
      console.log(document.cookie)
    },
    data: function () {
      return {
        message: 'Welcome to Your Vue.js App',
        username: ''
      }
    },
    methods: {
      clickButton:function(routerName){
        this.$router.push({name:routerName})
      },
      login: function () {
        this.$router.push('/login')
      },
      logout: function () {
        let baseUri = 'http://localhost:8080'
        this.axios.get(`${baseUri}/logout`, this.get)
          .then((result) => {
            // console.log(result)
            this.$router.go('/')
          })
      },
      // Get cookieValue for cookieName
      getCookieValue: function (cookieName) {
        cookieName = cookieName + '='
        let cookieData = document.cookie
        console.log(document.cookie)
        let start = cookieData.indexOf(cookieName)
        let cValue = ''
        if (start !== -1) {
          start += cookieName.length
          let end = cookieData.indexOf(';', start)
          if (end === -1) end = cookieData.length
          cValue = cookieData.substring(start, end)
        }
        return unescape(cValue)
      }
    }
  }
</script>

<style scoped>

</style>
