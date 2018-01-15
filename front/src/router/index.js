import Vue from 'vue'
import Router from 'vue-router'
import IssueContainer from '../components/Issue/IssueContainer.vue'
import Issue from '../components/Issue/Issue.vue'
import IssueList from '../components/Issue/IssueList.vue'
import IssueCreateTemplate from '../components/Issue/IssueCreateTemplate.vue'
import IssueUpdateTemplate from '../components/Issue/IssueUpdateTemplate.vue'
import IssueCommentUpdateTemplate from '../components/Issue/IssueCommentUpdateTemplate'
import Login from '@/components/Login/Login'
import Home from '@/components/Home'
import ProjectContainer from '../components/Project/ProjectContainer.vue'
import ProjectMain from '../components/Project/ProjectMain.vue'
import ProjectBoard from '../components/Project/Board/ProjectBoard.vue'
import ProjectList from '../components/Project/ProjectList.vue'
import ProjectUser from '../components/Project/ProjectUser.vue'
import ProjectAdd from '../components/Project/ProjectAdd.vue'
import WriteProjectBoard from '../components/Project/Board/WriteProjectBoard.vue'
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
      path: '/projects',
      name: 'ProjectList',
      component: ProjectList
    },
    {
      path: '/addProject',
      name: 'ProjectAdd',
      component: ProjectAdd
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
      path: '/projects/:projectId',
      name: 'ProjectContainer',
      component: ProjectContainer,
      children: [
        {
          path: '',
          component: ProjectMain,
          name: 'ProjectMain'
        },
        {
          path: 'board/:boardId',
          component: ProjectBoard,
          name: 'ProjectBoard'
        },
        {
          path: 'user',
          component: ProjectUser,
          name: 'ProjectUser'
        },
        {
          path: 'writeBoard',
          component: WriteProjectBoard,
          name: 'WriteProjectBoard'
        },
        {
          path: 'code',
          name: 'Code',
          component: Code
        },
        {
          path: 'issues',
          name: 'IssueContainer',
          component: IssueContainer,
          children: [
            {
              path: '',
              name: 'IssueList',
              component: IssueList
            },
            {
              path: 'new',
              name: 'IssueCreateTemplate',
              component: IssueCreateTemplate
            },
            {
              path: ':issueId',
              name: 'Issue',
              component: Issue
            },
            {
              path: ':issueId/update',
              name: 'IssueUpdateTemplate',
              component: IssueUpdateTemplate
            },
            {
              path: ':issueId/issue-comment/:issueCommentId/update',
              name: 'IssueCommentUpdateTemplate',
              component: IssueCommentUpdateTemplate
            }
          ]
        }
      ]
    }
  ]
})
