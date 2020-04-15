import axios from "axios";

function getCountRequests({ commit }, data) {
    return new Promise((resolve, reject) => {
        let queryParams = '';
        if (data.context && data.context.filter !== null) { queryParams += `?filter=${data.context.filter}`; }
        axios
            .get(`/rest/projects/${data.id}/requests/count${queryParams}`)
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project request count for project with id ${data.id}`, err: error } });
                reject(error);
            })
    });
}

function getCountResponses({ commit }, data) {
    return new Promise((resolve, reject) => {
        let queryParams = '';
        if (data.context && data.context.filter !== null) { queryParams += `?filter=${data.context.filter}`; }
        axios
            .get(`/rest/projects/${data.id}/responses/count${queryParams}`)
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project responses count for project with id ${data.id}`, err: error } });
                reject(error);
            })
    });
}

const projects = {
    state: {
        projects: {
            all: {
                items: null
            },
            current: {
                item: null,
                queued_or_running_tasks_count: null,
                requests: {
                    items: null,
                    count: null,
                    total: null
                },
                responses: {
                    items: null,
                    count: null,
                    total: null
                }
            },
            display: null
        }
    },
    mutations: {
        set_projects(state, payload) {
            state.projects.all.items = payload.items
        },
        set_project(state, payload) {
            state.projects.current.item = payload.item
        },

        set_project_running_or_queued_tasks_count(state, payload) {
            state.projects.current.queued_or_running_tasks_count = payload.count
        },

        set_project_requests_total(state, payload) {
            state.projects.current.requests.total = payload.total
        },
        set_project_requests_count(state, payload) {
            state.projects.current.requests.count = payload.count
        },
        set_project_requests_items(state, payload) {
            state.projects.current.requests.items = payload.items
        },

        set_project_responses_total(state, payload) {
            state.projects.current.responses.total = payload.total
        },
        set_project_responses_count(state, payload) {
            state.projects.current.responses.count = payload.count
        },
        set_project_responses_items(state, payload) {
            state.projects.current.responses.items = payload.items
        },

        set_project_display(state, payload) {
            state.projects.display = payload.display
        }
    },
    actions: {
        findAllProjects({ commit }) {
            return new Promise((resolve, reject) => {
                axios
                    .get("/rest/projects")
                    .then(response => {
                        commit("set_projects", { items: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: "Couldn't retrieve fuzzing projects", err: error } });
                        commit("set_projects", { items: [] });
                        reject(error);
                    })
            })
        },
        findProject({ commit, dispatch }, id) {
            return new Promise((resolve, reject) => {
                commit("set_project", { project: null });
                axios
                    .get(`/rest/projects/${id}`)
                    .then(response => {
                        commit("set_project", { item: response.data });
                        dispatch("countAllProjectRequests", { id: id });
                        dispatch("countAllProjectResponses", { id: id });
                        dispatch("countProjectRunningOrQueuedTasks", { id: id });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project with id ${id}`, err: error } });
                        commit("set_project", { item: null });
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
        findProjectRequests({ commit, dispatch }, data) {
            return new Promise((resolve, reject) => {
                let queryParams = `?curPage=${data.context.currentPage}&perPage=${data.context.perPage}`;
                if (data.context.filter !== null) { queryParams += `&filter=${data.context.filter}`; }
                axios
                    .get(`/rest/projects/${data.id}/requests${queryParams}`)
                    .then(response => {
                        commit("set_project_requests_items", { items: response.data });
                        dispatch("countProjectRequests", data);
                        resolve();
                    })
                    .catch(error => {
                        commit("set_project_requests_items", { items: null });
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project requests for project with id ${data.id}`, err: error } });
                        reject(error);
                    })
            })
        },
        findProjectResponses({ commit, dispatch }, data) {
            return new Promise((resolve, reject) => {
                let queryParams = `?curPage=${data.context.currentPage}&perPage=${data.context.perPage}`;
                if (data.context.filter !== null) { queryParams += `&filter=${data.context.filter}`; }
                axios
                    .get(`/rest/projects/${data.id}/responses${queryParams}`)
                    .then(response => {
                        commit("set_project_responses_items", { items: response.data });
                        dispatch("countProjectResponses", data);
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project responses for project with id ${data.id}`, err: error } });
                        commit("set_project_responses_items", { items: [] });
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
        countProjectRunningOrQueuedTasks({ commit }, data) {
            return new Promise((resolve, reject) => {
                axios
                    .get(`/rest/tasks/running_or_queued/projects/${data.id}/count`)
                    .then(response => {
                        commit("set_project_running_or_queued_tasks_count", { count: response.data });
                        resolve();
                    })
                    .catch(error => {
                        commit("message_add", { message: { type: "error", text: `Couldn't retrieve tasks count (running or queued) for project with id ${data.id}`, err: error } });
                        commit("set_project_running_or_queued_tasks_count", { count: null });
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