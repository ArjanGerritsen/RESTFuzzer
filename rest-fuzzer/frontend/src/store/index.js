import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    suts: null,
    sut: null,
    tasks: {
      queued: null,
      running: null,
      completed: null
    }
  },
  getters: {
    suts: state => {
      return state.suts
    },
    sut: state => {
      return state.sut
    },    
    tasks: state => {
      return state.tasks
    }
  },
  mutations: {
    suts_set (state, payload) {
      state.suts = payload.suts
    },
    sut_set (state, payload) {
      state.sut = payload.sut
    },
    tasks_queued_set (state, payload) {
      state.tasks.queued = payload.tasks
    },
    tasks_running_set (state, payload) {
      state.tasks.running = payload.tasks
    },
    tasks_completed_set (state, payload) {
      state.tasks.completed = payload.tasks
    }
  }
})

export default store