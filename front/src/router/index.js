import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/Login/Login.vue'
import Home from '../components/Home.vue'
import Study from '../components/Study/Study.vue'
import StudyMain from '../components/Study/StudyMain.vue'
import StudyBoard from '../components/Study/Board/StudyBoard.vue'
import StudyList from '../components/Study/StudyList.vue'
import StudyUser from '../components/Study/StudyUser.vue'
import WriteStudyBoard from '../components/Study/Board/WriteStudyBoard.vue'
import IssueContainer from '../components/Issue/IssueContainer.vue'
import Issue from '../components/Issue/Issue.vue'
import IssueList from '../components/Issue/IssueList.vue'
import IssueItem from '../components/Issue/IssueItem.vue'
import IssueTemplate from '../components/Issue/IssueTemplate.vue'
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
      ]
    },
    {
      path: '/issues',
      name: 'IssueContainer',
      component: IssueContainer,
      children: [
        {
          path: '',
          name: IssueList,
          component: IssueList,
          children: [
            {
              path: '',
              name: 'IssueItem',
              component: IssueItem
            }
          ]
        },
        {
          path: 'new',
          name: 'IssueTemplate',
          component: IssueTemplate
        },
        {
          path: ':issueId',
          name: 'Issue',
          component: Issue
        }
      ]
    }
  ]
})
