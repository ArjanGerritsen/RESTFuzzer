import Vue from "vue";
import Vuex from "vuex";

import Constants from "../shared/constants";

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
      if (state.tasks.progress != null) {
        // TODO
        state.tasks.progress.forEach(t => {
          let nameParts = t.canonicalName.split(".");
          t["name"] = nameParts[nameParts.length - 1];

          let status = Constants.TASK_STATUS_QUEUED;
          if (t.startedAt != null && t.crashedAt == null && t.finishedAt == null) {
            status = Constants.TASK_STATUS_RUNNING;
          } else if (t.startedAt != null && t.crashedAt != null) {
            status = Constants.TASK_STATUS_CRASHED;
          } else if (t.startedAt != null && t.finishedAt != null) {
            status = Constants.TASK_STATUS_FINISHED;
          }
          t["status"] = status;

          let endedAt = null;
          if (t.crashedAt != null) { endedAt = t.crashedAt };
          if (t.finishedAt != null) { endedAt = t.finishedAt };
          t["endedAt"] = endedAt;
        });
      }

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