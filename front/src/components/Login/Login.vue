<template>
  <div id="login">
    {{ message }}
    <a
      href="https://accounts.google.com/o/oauth2/v2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.login%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.me&access_type=offline&include_granted_scopes=true&state=state_parameter_passthrough_value&redirect_uri=http://localhost:8080/&response_type=code&client_id=2053235076-de59aup60vhb16i0kfea5fo4rgu1v8da.apps.googleusercontent.com">구글
      로그인 api 사용</a>
    <a
      href="https://accounts.google.com/o/oauth2/v2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar&access_type=offline&include_granted_scopes=true&state=state_parameter_passthrough_value&redirect_uri=http://localhost:8080/&response_type=code&client_id=2053235076-de59aup60vhb16i0kfea5fo4rgu1v8da.apps.googleusercontent.com">구글
      캘린더 api 사용</a>
    <button @click="googleLogin()">Google+</button>

    <form action="https://www.googleapis.com/oauth2/v4/token" method="post" enctype="application/x-www-form-urlencoded">
      code : <input type="text" name="code" value=""><br>
      client_id : <input type="text" name="client_id"
                         value="2053235076-de59aup60vhb16i0kfea5fo4rgu1v8da.apps.googleusercontent.com"><br>
      client_secret : <input type="text" name="client_secret" value="WQ47Jmo-S12D9EWSBvESIoTD"><br>
      redirect_uri : <input type="text" name="redirect_uri" value="http://localhost:8080/"><br>
      grant_type : <input type="text" name="grant_type" value="authorization_code"><br>
      <input type="submit">
    </form>
    <div class="row">
      <template v-for="variant in ['danger']">
        <div class="col-md-4 pb-2" @click="click()" v-for="size in ['']" :key="`${variant}_${size}`">
          <b-button :size="size" :variant="variant">
            {{variant}} {{size}}
          </b-button>
        </div>
      </template>
    </div>

  </div>
</template>

<script>
  export default {
    name: 'Login',
    created: function () {
      // alert('this is login page!')
    },
    data: function () {
      return {
        message: 'this is login page hahaha'
      }
    },
    methods: {
      googleLogin: function () {
        const loginUri = '/login/google'
        location.href = loginUri
      }
    }
  }
</script>

<style scoped>
  #login {
    width: 50%;
    margin: 0 auto;

  }

  button {
    color: crimson;
  }
</style>
