import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    projects: null,
    project: null,
    administrative: {
      tasks: null
    }
  },
  getters: {
    projects: state => {
      return state.projects
    },
    project: state => {
      return state.project
    },
    administrative_tasks: state => {
      return state.administrative.tasks
    }
  },
  mutations: {
    projects_set (state, payload) {
      state.projects = payload.projects
    },
    project_set (state, payload) {
      state.project = payload.project
    },
    administrative_tasks_set (state, payload) {
      state.administrative.tasks = payload.tasks
    },
  }
})

export default store