<template>
  <div>
    <div id = "home">
      <md-button class="md-raised" v-on:click = "clickButton('ProjectList')">Project</md-button>
      <md-button class="md-raised" v-on:click = "clickButton('Code')">Code</md-button>
      <div class="hello">
        <div v-if="this.isLogined === 'true'">
          <p> welcome {{ username }} !</p>
          <md-button class="md-raised" v-on:click="logout">logout</md-button>
        </div>
        <div v-else>
          <md-button class="md-raised" v-on:click="login">login</md-button>
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
      this.username =  this.getCookieValue('username')

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
        return decodeURIComponent(cValue)
      }
    }
  }
</script>

<style scoped>
  #home{
    margin-top: 60px;
  }

</style>
