import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Study from '../components/Study/Study.vue'
import StudyMain from '../components/Study/StudyMain.vue'
import StudyBoard from '../components/Study/Board/StudyBoard.vue'
import StudyList from '../components/Study/StudyList.vue'
import StudyUser from '../components/Study/StudyUser.vue'
import WriteStudyBoard from '../components/Study/Board/WriteStudyBoard.vue'
import Code from '../components/Code/Code.vue'

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
      path: '/code',
      name: 'Code',
      component: Code
    },
    {
      path: '/studies',
      name: 'StudyList',
      component: StudyList
    },
    {
      path: '/studies/:studyId',
      name: 'Study',
      component: Study,
      children: [
        {
          path: '',
          component: StudyMain
        },
        {
          path: 'board/:boardId',
          component: StudyBoard
        },
        {
          path: 'user',
          component: StudyUser
        },
        {
          path: 'writeBoard',
          component: WriteStudyBoard
        }
      ]
    }

  ]
})
