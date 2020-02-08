import axios from "axios";

const suts = {
    state: {
        suts: {
            all: null,
            current: null
        }
    },
    mutations: {
        suts_set(state, payload) {
            state.suts.all = payload.suts
        },
        sut_set(state, payload) {
            state.suts.current = payload.sut
        }
    },
    actions: {
        findAllSuts({ commit }) {
            return new Promise((resolve, reject) => {
                axios
                    .get("/rest/suts")
                    .then(response => {
                        commit("suts_set", { suts: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: "Couldn't retrieve suts", err: error } });
                        commit("suts_set", { suts: [] });
                        reject(error);
                    })
            })
        },
        findSut({ commit }, id) {
            return new Promise((resolve, reject) => {
                axios
                    .get(`/rest/suts/${id}`)
                    .then(response => {
                        commit("sut_set", { sut: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve sut with id ${id}`, err: error } });
                        commit("sut_set", { sut: null });
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
            	sutsForPullDown = state.suts.all.map(
                    sut => {
                        const newSut = {};
                        newSut["value"] = sut.id;
                        newSut["text"] = `#${sut.id} ${sut.title === null ? '' : '- ' + sut.title}`;
                        return newSut;
                    }
                );
            }

            return sutsForPullDown;
        },
    }
}

export default suts