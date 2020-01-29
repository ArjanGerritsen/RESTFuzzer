import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    suts: {
      all: null,
      current: null
    },
    tasks: {
      all: null,
      progress: null,
    }
  },
  getters: {
    suts: state => {
      return state.suts
    },
    tasks: state => {
      return state.tasks
    }
  },
  mutations: {
    suts_set (state, payload) {
      state.suts.all = payload.suts
    },
    sut_set (state, payload) {
      state.suts.current = payload.sut
    },
    tasks_progress_set (state, payload) {
      state.tasks.progress = payload.tasks
    }
  }
})

export default store