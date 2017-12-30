import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Study from '../components/Study/Study.vue'
import StudyMain from '../components/Study/StudyMain.vue'
import StudyBoard from '../components/Study/StudyBoard.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/studies/:id',
      name: 'Study',
      component: Study,
      children: [
        {
          path: '',
          component: StudyMain
        },
        {
          path: 'board',
          component: StudyBoard
        }
      ]
    }
  ]
})
