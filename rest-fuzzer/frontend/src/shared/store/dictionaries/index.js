import axios from "axios";

const dictionaries = {
    state: {
    	dictionaries: {
            all: null,
            current: null
        }
    },
    mutations: {
        set_dictionaries(state, payload) {
            state.dictionaries.all = payload.dictionaries
        },
        set_dictionary(state, payload) {
            state.dictionaries.current = payload.dictionary
        }
    },
    actions: {
        findAllDictionaries({ commit }) {
            return new Promise((resolve, reject) => {
                axios
                    .get("/rest/dictionaries")
                    .then(response => {
                        commit("set_dictionaries", { dictionaries: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: "Couldn't retrieve dictionaries", err: error } });
                        commit("set_dictionaries", { dictionaries: [] });
                        reject(error);
                    })
            })
        },
        findDictionary({ commit }, id) {
            return new Promise((resolve, reject) => {
                commit("set_dictionary", { dictionary: null });
                axios
                    .get(`/rest/dictionaries/${id}`)
                    .then(response => {
                        commit("set_dictionary", { dictionary: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve dictionary with id ${id}`, err: error } });
                        commit("set_dictionary", { dictionary: null });
                        reject(error);
                    })
            })
        },
        addDictionary({ commit }, dictionary) {
            return new Promise((resolve, reject) => {
                axios
                    .post('/rest/dictionaries', dictionary)
                    .then(response => {
                        commit("message_add", { message: { type: "info", title: "Add dictionary", text: `Dictionary ${response.data.name} added successful.` } });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: "An error occured while adding dictionary", err: error } });
                        reject(error);
                    })
            })
        },
        deleteDictionary({ commit }, dictionary) {
            return new Promise((resolve, reject) => {
                axios
                    .delete(`/rest/dictionaries/${dictionary.id}`)
                    .then(response => {
                        commit("message_add", { message: { type: "info", title: "Delete dictionary", text: `Dictionary ${response.data.name} deleted successful.` } });
                        commit("set_sut", { sut: null });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't delete dictionary with id ${dictionary.id}`, err: error } });
                        reject(error);
                    })
            })
        },
    },
    getters: {
    	dictionaries: state => {
            return state.dictionaries
        },
    }
}

export default dictionaries