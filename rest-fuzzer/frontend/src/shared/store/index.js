import Vue from "vue";
import Vuex from "vuex";

import Constants from "../constants";

import suts from "./suts";
import tasks from "./tasks";

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    suts: suts,
    tasks: tasks
  }
})

export default store