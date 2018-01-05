// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import Vuex from 'vuex'
import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'

import NavBar from './components/NavBar.vue'
import StudyItem from './components/Study/StudyItem.vue'
import StudyMenu from './components/Study/StudyMenu.vue'
import StudyWidget from './components/Study/StudyWidget.vue'
import PostTemplate from './components/Study/Board/Template/PostTemplate.vue'

Vue.config.productionTip = false

Vue.prototype.axios = axios

Vue.component('NavBar', NavBar)
Vue.component('StudyMenu', StudyMenu)
Vue.component('StudyItem', StudyItem)
Vue.component('StudyWidget', StudyWidget)
Vue.component('PostTemplate', PostTemplate)
Vue.use(VueMaterial)
Vue.use(Vuex)

/* eslint-disable no-new */
const store = new Vuex.Store({
  state: {
    type: 0
  },
  mutations: {
    setType (state, type) {
      state.type = type
    }
  }
})

new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: {App}
})
