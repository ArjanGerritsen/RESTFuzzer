import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    projects: null,
    project: null
  },
  getters: {
    project: state => {
      return state.project
    }
  },
  mutations: {
    project_set (state, payload) {
      state.project = payload.project
    }
  }
})

export default store