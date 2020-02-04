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
            axios
                .get("/rest/suts")
                .then(response => {
                    commit("suts_set", { suts: response.data });
                })
                .catch(error => {
                    commit("message_add", { message: { type: "error", text: "Couldn't retrieve suts", err: error } });
                    commit("suts_set", { suts: [] });
                })
        },
        findSut({ commit }, id) {
            axios
                .get(`/rest/suts/${id}`)
                .then(response => {
                    commit("sut_set", { sut: response.data });
                })
                .catch(error => {
                    commit("message_add", { message: { type: "error", text: `Couldn't retrieve sut with id ${id}`, err: error } });
                    commit("sut_set", { sut: null });
                })
        },
        addSut({ commit }, sut) {
            axios
                .post('/rest/suts', sut)
                .then(response => {
                    commit("message_add", { message: { type: "info", title: "Add sut", text: `Sut ${response.data.location} added successful.` } });
                })
                .catch(error => {
                    commit("message_add", { message: { type: "error", text: "An error occured while adding sut", err: error } });
                })
        },
        deleteSut({ commit }, sut) {
            axios
                .delete(`/rest/suts/${sut.id}`)
                .then(response => {
                    commit("message_add", { message: { type: "info", title: "Delete sut", text: `Sut ${response.data.location} deleted successful.` } });
                    commit("sut_set", { sut: null });
                })
                .catch(error => {
                    commit("message_add", { message: { type: "error", text: `Couldn't delete sut with id ${sut.id}`, err: error } });
                })
        }
    },
    getters: {
        suts: state => {
            return state.suts
        }
    }
}

export default suts