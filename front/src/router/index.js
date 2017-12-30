import Vue from 'vue'
import Router from 'vue-router'
import StudyList from '@/components/Study/StudyList'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'StudyList',
      component: StudyList
    }
  ]
})
