import Vue from "vue";
import Vuex from "vuex";

import suts from "./suts";
import tasks from "./tasks";
import projects from "./projects";
import dictionaries from "./dictionaries";
import messsages from "./messages";

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    suts: suts,
    tasks: tasks,
    projects: projects,
    dictionaries: dictionaries,
    messsages: messsages
  }
})

export default store