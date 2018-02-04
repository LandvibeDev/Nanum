// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import Vuex from 'vuex'
import store from '../vuex/store'
import VueMaterial from 'vue-material'
import Toasted from 'vue-toasted'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'
import * as OfflinePluginRuntime from 'offline-plugin/runtime';

Vue.config.productionTip = false

Vue.prototype.axios = axios

Vue.use(Toasted)
Vue.use(VueMaterial)
Vue.use(Vuex)

if (process.env.NODE_ENV === 'production') {
  OfflinePluginRuntime.install({
    onUpdateReady () {
      OfflinePluginRuntime.applyUpdate()
    },
    onUpdated () {
      window.location.reload()
    }
  })
}

new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: {App}
})
