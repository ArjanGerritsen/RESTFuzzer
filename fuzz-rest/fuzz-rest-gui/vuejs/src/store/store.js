import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    projects: null,
    project: null,
    admin: {
      settings: null,
      setting: null,
      tasks: null,
      task: null,
    }
  },
  getters: {
    projects: state => {
      return state.projects
    },
    project: state => {
      return state.project
    },
    admin_tasks: state => {
      return state.admin.tasks
    },
    admin_task: state => {
      return state.admin.task
    },
    admin_settings: state => {
      return state.admin.settings
    },
    admin_setting: state => {
      return state.admin.setting
    },    
  },
  mutations: {
    projects_set (state, payload) {
      state.projects = payload.projects
    },
    project_set (state, payload) {
      state.project = payload.project
    },
    admin_tasks_set (state, payload) {
      state.admin.tasks = payload.tasks
    },
    admin_task_set (state, payload) {
      state.admin.task = payload.task
    },
    admin_settings_set (state, payload) {
      state.admin.settings = payload.settings
    },
    admin_setting_set (state, payload) {
      state.admin.setting = payload.setting
    },
  }
})

export default store