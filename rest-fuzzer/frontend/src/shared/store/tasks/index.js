import axios from "axios";

import Constants from "../../constants";

const tasks = {
    state: {
        tasks: {
            all: null,
            progress: null,
        }
    },
    mutations: {
        tasks_progress_set(state, payload) {
            state.tasks.progress = payload.tasks
        }
    },
    actions: {
	  findTasksProgress({ commit }) {
          axios
              .get("/rest/tasks/progress")
              .then(response => {
                  commit("tasks_progress_set", { tasks: response.data });
              })
              .catch(error => {
                  commit("message_add", { message: { type: "error", text: "Couldn't retrieve tasks (progress)", err: error } });
                  commit("tasks_progress_set", { tasks: [] });
              })
      },
      addTask({ commit }, data) {
          axios
          	  .post(`/rest/tasks/${data.name}/start`, data.metaDataTuples)
	          .then(response => {
	        	  commit("message_add", { message: { type: "info", title: "Add task", text: `Task (${data.name}) added successful.` } });
	          })
              .catch(error => {
                  commit("message_add", { message: { type: "error", text: `Couldn't add task (${data.name})`, err: error } });
              })
      }
    },
    getters: {
        tasks: state => {
            if (state.tasks.progress != null) {
                // TODO
                state.tasks.progress.forEach(t => {
                    let nameParts = t.canonicalName.split(".");
                    t["name"] = nameParts[nameParts.length - 1].replace('Task', '');

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
    }
}

export default tasks