import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    projects: null,
    project: null
  },
  getters: {
    projects: state => {
      return state.projects
    },
    project: state => {
      return state.project
    },
  },
  mutations: {
    projects_set (state, payload) {
      state.projects = payload.projects
    },
    project_set (state, payload) {
      state.project = payload.project
    },
  }
})

export default store