import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    suts: null,
    sut: null,
    tasks: null,
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
    tasks_set (state, payload) {
      state.tasks = payload.tasks
    }
  }
})

export default store