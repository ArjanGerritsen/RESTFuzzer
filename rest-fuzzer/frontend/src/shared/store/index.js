import Vue from "vue";
import Vuex from "vuex";

import suts from "./suts";
import tasks from "./tasks";
import projects from "./projects";
import messsages from "./messages";

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    suts: suts,
    tasks: tasks,
    projects: projects,
    messsages: messsages
  }
})

export default store