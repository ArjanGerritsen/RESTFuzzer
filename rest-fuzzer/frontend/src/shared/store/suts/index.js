import axios from "axios";

const suts = {
    state: {
        suts: {
            all: null,
            current: null,
            failures: []
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
                    // TODO iets met failures ... this.messageService.error("Couldn't retrieve suts", error);
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
                    // TODO iets met failures ... this.messageService.error("Couldn't retrieve sut", error);
                    commit("sut_set", { sut: null });
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