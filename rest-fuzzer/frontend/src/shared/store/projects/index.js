import axios from "axios";

const projects = {
    state: {
        projects: {
            all: null,
            current: null,
            current_requests: null,
            current_responses: null
        }
    },
    mutations: {
        projects_set(state, payload) {
            state.projects.all = payload.projects
        },
        project_set(state, payload) {
            state.projects.current = payload.project
        },
        project_requests_set(state, payload) {
            state.projects.current_requests = payload.requests
        },
        project_requests_set(state, payload) {
            state.projects.current_requests = payload.requests
        },
        project_requests_count_set(state, payload) {
            state.projects.current["requestsCount"] = payload.count
        },
        project_responses_set(state, payload) {
            state.projects.current_responses = payload.responses
        },
        project_responses_count_set(state, payload) {
            state.projects.current["responsesCount"] = payload.count
        }
    },
    actions: {
        findAllProjects({ commit }) {
            return new Promise((resolve, reject) => {
                axios
                    .get("/rest/projects")
                    .then(response => {
                        commit("projects_set", { projects: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: "Couldn't retrieve fuzzing projects", err: error } });
                        commit("projects_set", { projects: [] });
                        reject(error);
                    })
            })
        },
        findProject({ commit, dispatch }, id) {
            return new Promise((resolve, reject) => {
                commit("project_set", { project: null });
                axios
                    .get(`/rest/projects/${id}`)
                    .then(response => {
                        commit("project_set", { project: response.data });
                        dispatch("countProjectRequests", id);
                        dispatch("countProjectResponses", id);
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project with id ${id}`, err: error } });
                        commit("project_set", { project: null });
                        reject(error);
                    })
            })
        },
        findProjectRequests({ commit }, params) {
            return new Promise((resolve, reject) => {
                let queryParams = `?curPage=${params.context.currentPage}&perPage=${params.context.perPage}`;
                if (params.context.filter !== null) { queryParams += `&filter=${params.context.filter}`; }
                axios
                    .get(`/rest/projects/${params.project_id}/requests${queryParams}`)
                    .then(response => {
                        commit("project_requests_set", { requests: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project requests for id ${params.project_id}`, err: error } });
                        commit("project_requests_set", { requests: [] });
                        reject(error);
                    })
            })
        },
        countProjectRequests({ commit }, id) {
            return new Promise((resolve, reject) => {
                axios
                    .get(`/rest/projects/${id}/requests/count`)
                    .then(response => {
                        commit("project_requests_count_set", { count: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project request count for id ${id}`, err: error } });
                        commit("project_requests_count_set", { count: 0 });
                        reject(error);
                    })
            })
        },
        findProjectResponses({ commit }, id) {
            return new Promise((resolve, reject) => {
                axios
                    .get(`/rest/projects/${id}/responses`)
                    .then(response => {
                        commit("project_responses_set", { responses: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project responses for id ${id}`, err: error } });
                        commit("project_responses_set", { responses: [] });
                        reject(error);
                    })
            })
        },
        countProjectResponses({ commit }, id) {
            return new Promise((resolve, reject) => {
                axios
                    .get(`/rest/projects/${id}/responses/count`)
                    .then(response => {
                        commit("project_responses_count_set", { count: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project responses count for id ${id}`, err: error } });
                        commit("project_responses_count_set", { count: 0 });
                        reject(error);
                    })
            })
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
                        commit("project_set", { project: null });
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