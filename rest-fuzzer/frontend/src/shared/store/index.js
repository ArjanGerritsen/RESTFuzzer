import Vue from "vue";
import Vuex from "vuex";

import suts from "./suts";
import tasks from "./tasks";
import projects from "./projects";
import dictionaries from "./dictionaries";
import configurations from "./configurations";
import messsages from "./messages";

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    suts: suts,
    tasks: tasks,
    projects: projects,
    dictionaries: dictionaries,
    configurations: configurations,
    messsages: messsages
  }
})

export default store