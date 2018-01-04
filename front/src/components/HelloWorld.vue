<template>
  <div class="hello">

    <div v-if="this.isLogined === 'true'">
      <p> welcome {{ username }} !</p>
      <button v-on:click="logout">logout</button>
    </div>
    <div v-else>
      <button v-on:click="login">login</button>
    </div>

  </div>
</template>

<script>
  export default {
    name: 'HelloWorld',
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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
