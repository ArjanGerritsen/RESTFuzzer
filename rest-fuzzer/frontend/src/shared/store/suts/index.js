import axios from "axios";

const suts = {
    state: {
        suts: {
            all: null,
            current: null,
            currentQueuedOrRunningTasksCount: null
        }
    },
    mutations: {
        set_suts(state, payload) {
            state.suts.all = payload.suts
        },
        set_sut(state, payload) {
            state.suts.current = payload.sut
        },
        set_sut_running_or_queued_tasks_count(state, payload) {
            state.suts.currentQueuedOrRunningTasksCount = payload.count
        }
    },
    actions: {
        findAllSuts({ commit }) {
            return new Promise((resolve, reject) => {
                axios
                    .get("/rest/suts")
                    .then(response => {
                        commit("set_suts", { suts: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: "Couldn't retrieve suts", err: error } });
                        commit("set_suts", { suts: [] });
                        reject(error);
                    })
            })
        },
        findSut({ commit, dispatch }, id) {
            return new Promise((resolve, reject) => {
                axios
                    .get(`/rest/suts/${id}`)
                    .then(response => {
                        commit("set_sut", { sut: response.data });
                        dispatch("countSutRunningOrQueuedTasks", id);
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve sut with id ${id}`, err: error } });
                        commit("set_sut", { sut: null });
                        reject(error);
                    })
            })
        },
        countSutRunningOrQueuedTasks({ commit }, id)  {
            return new Promise((resolve, reject) => {
                axios
                    .get(`/rest/tasks/running_or_queued/suts/${id}/count`)
                    .then(response => {
                        commit("set_sut_running_or_queued_tasks_count", { count: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve tasks count (running or queued) for sut with id ${id}`, err: error } });
                        commit("set_sut_running_or_queued_tasks_count", { count: null });
                        reject(error);
                    })
            })
        },
        addSut({ commit }, sut) {
            return new Promise((resolve, reject) => {               
                axios
                    .post('/rest/suts', sut)
                    .then(response => {
                        commit("message_add", { message: { type: "info", title: "Add sut", text: `Sut ${response.data.location} added successful.` } });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: "An error occured while adding sut", err: error } });
                        reject(error);
                    })
            })
        },
        deleteSut({ commit }, sut) {
            return new Promise((resolve, reject) => {
                axios
                    .delete(`/rest/suts/${sut.id}`)
                    .then(response => {
                        commit("message_add", { message: { type: "info", title: "Delete system under test", text: `System under test ${response.data.location} deleted successful.` } });
                        commit("sut_set", { sut: null });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't delete system under test with id ${sut.id}`, err: error } });
                        reject(error);
                    })
            })
        },
    },
    getters: {
        suts: state => {
            return state.suts
        },
        sutsForPullDown: state => {
            let sutsForPullDown = []

            if (state.suts.all !== null) {
            	sutsForPullDown = state.suts.all.filter(sut => sut.title !== null).map(
                    sut => {
                        const newSut = {};
                        newSut["value"] = sut.id;
                        newSut["text"] = `${sut.title} (${sut.location})`;
                        return newSut;
                    }
                );
            }

            return sutsForPullDown;
        },
    }
}

export default suts