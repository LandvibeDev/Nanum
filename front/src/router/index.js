import Vue from 'vue'
import Router from 'vue-router'
// import StudyList from '@/components/Study/StudyList'
import HelloWorld from '@/components/HelloWorld'
import Login from '@/components/Login/Login'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    }
  ]
})
