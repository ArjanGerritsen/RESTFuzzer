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
    actions: {},
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