import axios from "axios";

function getCountRequests({ commit }, data) {
    return new Promise((resolve, reject) => {
        let queryParams = '';
        if (data.context && data.context.filter !== null) { queryParams += `?filter=${data.context.filter}`; }
        axios
            .get(`/rest/projects/${data.project_id}/requests/count${queryParams}`)
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project request count for project with id ${data.project_id}`, err: error } });
                reject(error);
            })
    });
}

function getCountResponses({ commit }, data) {
    return new Promise((resolve, reject) => {
        let queryParams = '';
        if (data.context && data.context.filter !== null) { queryParams += `?filter=${data.context.filter}`; }
        axios
            .get(`/rest/projects/${data.project_id}/responses/count${queryParams}`)
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project responses count for project with id ${data.project_id}`, err: error } });
                reject(error);
            })
    });
}

const projects = {
    state: {
        projects: {
            all: null,
            current: null,
            current_requests: {
                total: null,
                count: null,
                list: null
            },
            current_responses: {
                total: null,
                count: null,
                list: null
            },
        }
    },
    mutations: {
        set_projects(state, payload) {
            state.projects.all = payload.projects
        },
        set_project(state, payload) {
            state.projects.current = payload.project
        },

        set_project_requests_total(state, payload) {
            state.projects.current_requests.total = payload.total
        },
        set_project_requests_count(state, payload) {
            state.projects.current_requests.count = payload.count
        },
        set_project_requests_list(state, payload) {
            state.projects.current_requests.list = payload.list
        },

        set_project_responses_total(state, payload) {
            state.projects.current_responses.total = payload.total
        },
        set_project_responses_count(state, payload) {
            state.projects.current_responses.count = payload.count
        },
        set_project_responses_list(state, payload) {
            state.projects.current_responses.list = payload.list
        },
    },
    actions: {
        findAllProjects({ commit }) {
            return new Promise((resolve, reject) => {
                axios
                    .get("/rest/projects")
                    .then(response => {
                        commit("set_projects", { projects: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: "Couldn't retrieve fuzzing projects", err: error } });
                        commit("set_projects", { projects: [] });
                        reject(error);
                    })
            })
        },
        findProject({ commit, dispatch }, data) {
            return new Promise((resolve, reject) => {
                commit("set_project", { project: null });
                axios
                    .get(`/rest/projects/${data.project_id}`)
                    .then(response => {
                        commit("set_project", { project: response.data });
                        dispatch("countAllProjectRequests", data);
                        dispatch("countAllProjectResponses", data);
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project with id ${data.project_id}`, err: error } });
                        commit("set_project", { project: null });
                        reject(error);
                    })
            })
        },
        findProjectRequests({ commit, dispatch }, data) {
            return new Promise((resolve, reject) => {
                let queryParams = `?curPage=${data.context.currentPage}&perPage=${data.context.perPage}`;
                if (data.context.filter !== null) { queryParams += `&filter=${data.context.filter}`; }
                axios
                    .get(`/rest/projects/${data.project_id}/requests${queryParams}`)
                    .then(response => {
                        commit("set_project_requests_list", { requests: response.data });
                        dispatch("countProjectRequests", data);
                        resolve();
                    })
                    .catch(error => {
                        commit("set_project_requests_list", { requests: null });
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project requests for project with id ${data.project_id}`, err: error } });
                        reject(error);
                    })
            })
        },
        countAllProjectRequests({ commit }, data) {
            getCountRequests({ commit }, data)
                .then(total => {
                    commit("set_project_requests_total", { total: total });
                });
        },
        countProjectRequests({ commit }, data) {
            getCountRequests({ commit }, data)
                .then(count => {
                    commit("set_project_requests_count", { count: count });
                });
        },
        findProjectResponses({ commit, dispatch }, data) {
            return new Promise((resolve, reject) => {
                let queryParams = `?curPage=${data.context.currentPage}&perPage=${data.context.perPage}`;
                if (data.context.filter !== null) { queryParams += `&filter=${data.context.filter}`; }
                axios
                    .get(`/rest/projects/${data.project_id}/responses${queryParams}`)
                    .then(response => {
                        commit("set_project_responses_list", { responses: response.data });
                        dispatch("countProjectResponses", data);
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project responses for project with id ${data.project_id}`, err: error } });
                        commit("set_project_responses_list", { responses: [] });
                        reject(error);
                    })
            })
        },
        countAllProjectResponses({ commit }, data) {
            getCountResponses({ commit }, data)
                .then(total => {
                    commit("set_project_responses_total", { total: total });
                });
        },
        countProjectResponses({ commit }, data) {
            getCountResponses({ commit }, data)
                .then(count => {
                    commit("set_project_responses_count", { count: count });
                });
        },
        addProject({ commit }, project) {
            return new Promise((resolve, reject) => {
                axios
                    .post('/rest/projects', project)
                    .then(response => {
                        commit("message_add", { message: { type: "info", title: "Add fuzzing project", text: `Fuzzing project ${response.data.type} added successful.` } });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: "An error occured while adding fuzzing project", err: error } });
                        reject(error);
                    })
            })
        },
        deleteProject({ commit }, project) {
            return new Promise((resolve, reject) => {
                axios
                    .delete(`/rest/projects/${project.id}`)
                    .then(response => {
                        commit("message_add", { message: { type: "info", title: "Delete fuzzing project", text: `Fuzzing project ${response.data.type} with id ${response.data.id} deleted successful.` } });
                        commit("set_project", { project: null });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't delete fuzzing project with id ${project.id}`, err: error } });
                        reject(error);
                    })
            })
        }
    },
    getters: {
        projects: state => {
            return state.projects
        }
    }
}

export default projects