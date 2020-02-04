import Vue from "vue";
import Vuex from "vuex";

import suts from "./suts";
import tasks from "./tasks";
import messsages from "./messages";

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    suts: suts,
    tasks: tasks,
    messsages: messsages
  }
})

export default store