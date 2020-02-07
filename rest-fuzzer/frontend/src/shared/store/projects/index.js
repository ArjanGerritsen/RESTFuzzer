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
        // findSut({ commit }, id) {
        //     return new Promise((resolve, reject) => {
        //         axios
        //             .get(`/rest/fuzzing_tasks/${id}`)
        //             .then(response => {
        //                 commit("fuzzing_task_set", { sut: response.data });
        //                 resolve();
        //             })
        //             .catch(error => {
        //                 commit("message_add", { message: { type: "error", text: `Couldn't retrieve sut with id ${id}`, err: error } });
        //                 commit("fuzzing_task_set", { fuzzing_task: null });
        //                 reject(error);
        //             })
        //     })
        // },
        addProject({ commit }, project) {
            return new Promise((resolve, reject) => {
                console.log(project)
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
        // deleteSut({ commit }, sut) {
        //     return new Promise((resolve, reject) => {
        //         axios
        //             .delete(`/rest/fuzzing_tasks/${sut.id}`)
        //             .then(response => {
        //                 commit("message_add", { message: { type: "info", title: "Delete sut", text: `Sut ${response.data.location} deleted successful.` } });
        //                 commit("sut_set", { sut: null });
        //                 resolve();
        //             })
        //             .catch(error => {
        //                 commit("message_add", { message: { type: "error", text: `Couldn't delete sut with id ${sut.id}`, err: error } });
        //                 reject(error);
        //             })
        //     })
        // }
    },
    getters: {
        projects: state => {
            return state.projects
        }
    }
}

export default projects