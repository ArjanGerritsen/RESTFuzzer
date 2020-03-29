import axios from "axios";

function getCountActions({ commit }, data) {
    return new Promise((resolve, reject) => {
        let queryParams = '';
        if (data.context && data.context.filter !== null) { queryParams += `?filter=${data.context.filter}`; }
        axios
            .get(`/rest/suts/${data.sut_id}/actions/count${queryParams}`)
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                commit("message_add", { message: { type: "error", text: `Couldn't retrieve sut action count for sut with id ${data.sut_id}`, err: error } });
                reject(error);
            })
    });
}

const suts = {
    state: {
        suts: {
            all: null,
            current: null,
            current_queued_or_running_tasks_count: null,
            current_actions: {
                total: null,
                count: null,
                list: null
            },
            current_dependencies: {
                nodes: null,
                links: null
            }
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
            state.suts.current_queued_or_running_tasks_count = payload.count
        },

        set_sut_actions_total(state, payload) {
            state.suts.current_actions.total = payload.total
        },
        set_sut_actions_count(state, payload) {
            state.suts.current_actions.count = payload.count
        },
        set_sut_actions_list(state, payload) {
            state.suts.current_actions.list = payload.list
        },

        set_sut_dependencies_nodes(state, payload) {
            state.suts.current_dependencies.nodes = payload.nodes
        },
        set_sut_dependencies_links(state, payload) {
            state.suts.current_dependencies.links = payload.links
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
                commit("set_sut", { sut: null });
                axios
                    .get(`/rest/suts/${id}`)
                    .then(response => {
                        commit("set_sut", { sut: response.data });
                        dispatch("countAllSutActions", { sut_id: id });
                        dispatch("findSutDependenciesNodes", { sut_id: id });
                        dispatch("findSutDependenciesLinks", { sut_id: id });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve sut with id ${id}`, err: error } });
                        commit("set_sut", { sut: null });
                        reject(error);
                    })
            })
        },
        findSutActions({ commit, dispatch }, data) {
            return new Promise((resolve, reject) => {
                let queryParams = `?curPage=${data.context.currentPage}&perPage=${data.context.perPage}`;
                if (data.context.filter !== null) { queryParams += `&filter=${data.context.filter}`; }
                axios
                    .get(`/rest/suts/${data.sut_id}/actions/paginated/${queryParams}`)
                    .then(response => {
                        commit("set_sut_actions_list", { list: response.data });
                        dispatch("countSutActions", data);
                        resolve();
                    })
                    .catch(error => {
                        commit("set_sut_actions_list", { list: null });
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve sut actions for sut with id ${data.sut_id}`, err: error } });
                        reject(error);
                    })
            })
        },
        countAllSutActions({ commit }, data) {
            getCountActions({ commit }, data)
                .then(total => {
                    commit("set_sut_actions_total", { total: total });
                });
        },
        countSutActions({ commit }, data) {
            getCountActions({ commit }, data)
                .then(count => {
                    commit("set_sut_actions_count", { count: count });
                });
        },
        countSutRunningOrQueuedTasks({ commit }, id) {
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
        findSutDependenciesNodes({ commit }, data) {
            return new Promise((resolve, reject) => {
                axios
                    .get(`/rest/suts/${data.sut_id}/actions`)
                    .then(response => {
                        commit("set_sut_dependencies_nodes", { nodes: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("set_sut_dependencies_nodes", { nodes: null });
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve sut dependency nodes for sut with id ${data.sut_id}`, err: error } });
                        reject(error);
                    })
            })
        },
        findSutDependenciesLinks({ commit }, data) {
            return new Promise((resolve, reject) => {
                axios
                    .get(`/rest/suts/${data.sut_id}/actions/dependencies`)
                    .then(response => {
                        commit("set_sut_dependencies_links", { links: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("set_sut_dependencies_links", { links: null });
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve sut dependency links for sut with id ${data.sut_id}`, err: error } });
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
                        commit("set_sut", { sut: null });
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
        sutNodes: state => {
            let nodes = []

            if (state.suts.current_dependencies.nodes !== null) {
                nodes = state.suts.current_dependencies.nodes.map(
                    node => {
                        let newNode = {};
                        newNode["id"] = node.id;
                        newNode["title"] = `${node.path} (${node.httpMethod})`;
                        return newNode;
                    }
                );
            }

            return nodes;
        },
        sutLinks: state => {
            let links = []

            if (state.suts.current_dependencies.links !== null) {
                links = state.suts.current_dependencies.links.map(
                    link => {
                        let newLink = {};
                        newLink["id"] = link.id;
                        newLink["target"] = link.dependsOnActionId;
                        newLink["source"] = link.actionId;
                        return newLink;
                    }
                );
            }

            return links;
        },        
    }
}

export default suts