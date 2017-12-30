// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import NavBar from './components/NavBar.vue'
import Study from './components/Study/Study.vue'
import StudyItem from './components/Study/StudyItem.vue'
import BootstrapVue from 'bootstrap-vue'
import axios from 'axios'
import StudyMenu from './components/Study/StudyMenu.vue'
import StudyList from './components/Study/StudyList.vue'
import StudyBoardWidget from './components/Study/StudyBoardWidget.vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false
Vue.prototype.axios = axios
Vue.component('NavBar', NavBar)
Vue.component('StudyMenu', StudyMenu)
Vue.component('StudyList', StudyList)
Vue.component('StudyItem', StudyItem)
Vue.component('StudyBoardWidget', StudyBoardWidget)
Vue.use(BootstrapVue)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App}
})
