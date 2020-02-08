import axios from "axios";

const projects = {
    state: {
        projects: {
            all: null,
            current: null
        }
    },
    mutations: {
        projects_set(state, payload) {
            state.projects.all = payload.projects
        },
        project_set(state, payload) {
            state.projects.current = payload.project
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
        findProject({ commit }, id) {
            return new Promise((resolve, reject) => {
                axios
                    .get(`/rest/projects/${id}`)
                    .then(response => {
                        commit("project_set", { project: response.data });
                        resolve();
                    })
                    .catch(error => {
                    	commit("message_add", { message: { type: "error", text: `Couldn't retrieve fuzzing project with id ${id}`, err: error } });
                        commit("project_set", { project: null });
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