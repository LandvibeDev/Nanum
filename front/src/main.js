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
import IssueContainer from './components/Issue/IssueContainer.vue'
import Issue from './components/Issue/Issue.vue'
import IssueList from './components/Issue/IssueList.vue'
import IssueItem from './components/Issue/IssueItem.vue'
import IssueTemplate from './components/Issue/IssueTemplate.vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false

Vue.prototype.axios = axios
Vue.component('NavBar', NavBar)
Vue.component('StudyMenu', StudyMenu)
Vue.component('StudyItem', StudyItem)
Vue.component('StudyWidget', StudyWidget)
Vue.component('PostTemplate', PostTemplate)
Vue.component('IssueContainer', IssueContainer)
Vue.component('Issue', Issue)
Vue.component('IssueList', IssueList)
Vue.component('IssueItem', IssueItem)
Vue.component('IssueTemplate', IssueTemplate)
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
