import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login/Login'
import Home from '@/components/Home'
import ProjectContainer from '../components/Project/ProjectContainer.vue'
import ProjectMain from '../components/Project/ProjectMain.vue'
import ProjectBoard from '../components/Project/Board/ProjectBoard.vue'
import ProjectList from '../components/Project/ProjectList.vue'
import ProjectUser from '../components/Project/ProjectUser.vue'
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
        }
      ]
    }

  ]
})
