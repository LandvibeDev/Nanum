import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login/Login'
import Home from '@/components/Home'
import Study from '../components/Study/Study.vue'
import StudyMain from '../components/Study/StudyMain.vue'
import StudyBoard from '../components/Study/Board/StudyBoard.vue'
import StudyList from '../components/Study/StudyList.vue'
import StudyUser from '../components/Study/StudyUser.vue'
import WriteStudyBoard from '../components/Study/Board/WriteStudyBoard.vue'
import Issue from '../components/Issue/Issue'
import IssueMain from '../components/Issue/IssueMain'
import IssueList from '../components/Issue/IssueList'
import IssueItem from '../components/Issue/IssueItem'
// import IssueBoard from '../components/Issue/Board/IssueBoard'
// import IssueBoardItem from '../components/Issue/Board/IssueBoardItem'

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
      path: '/studies',
      name: 'StudyList',
      component: StudyList
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/logout',
      redirect: Home
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
        // {
        //   path: '/issues',
        //   name: 'Issue',
        //   component: Issue,
        //   children: [
        //   ]
        // }
      ]
    },
    {
      path: '/issues',
      name: 'IssueList',
      component: IssueList,
      children: [
        {
          path: '/:issueId',
          name: 'Issue',
          component: Issue,
          children: [
            {
              path: '',
              component: IssueMain
            }
          ]
        },
      ]
    }
  ]
})
