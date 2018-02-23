import Vue from 'vue'
import Vuex from 'vuex'
import code from './modules/code'
import board from './modules/board'
Vue.use(Vuex)

export const store = new Vuex.Store({
  modules: {
    code,
    board
  }
})
