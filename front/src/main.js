// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import Vuex from 'vuex'
import VueMaterial from 'vue-material'
import Toasted from 'vue-toasted'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'
import {store} from './store/'

Vue.config.productionTip = false

Vue.prototype.axios = axios

Vue.use(Toasted)
Vue.use(VueMaterial)
Vue.use(Vuex)

/* eslint-disable no-new */

new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: {App}
})
