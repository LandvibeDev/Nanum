// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import axios from 'axios'
import Vuex from 'vuex'
import NavBar from './components/NavBar.vue'
import StudyItem from './components/Study/StudyItem.vue'
import StudyMenu from './components/Study/StudyMenu.vue'
import StudyWidget from './components/Study/StudyWidget.vue'
import PostTemplate from './components/Study/Board/Template/PostTemplate.vue'
// import Issue from './components/Issue/Issue'
import IssueList from './components/Issue/IssueList'
import IssueItem from './components/Issue/IssueItem'
// import IssueMain from './components/Issue/IssueMain'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false

Vue.prototype.axios = axios
Vue.component('NavBar', NavBar)
Vue.component('StudyMenu', StudyMenu)
Vue.component('StudyItem', StudyItem)
Vue.component('StudyWidget', StudyWidget)
Vue.component('PostTemplate', PostTemplate)
Vue.component('IssueList', IssueList)
Vue.component('IssueItem', IssueItem)
Vue.use(BootstrapVue)
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
