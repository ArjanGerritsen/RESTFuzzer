import axios from "axios";

export default class RestService {

  getTasksProgress() {
    return new Promise(function (resolve, reject) {
      axios
        .get("/rest/tasks/progress")
        .then(response => { resolve(response); })
        .catch(error => { reject(error); })
    })
  }

  addTask(name, metaDataTuples) {
    return new Promise(function (resolve, reject) {
      axios
        .post(`/rest/tasks/${name}/start`, metaDataTuples)
        .then(response => { resolve(response); })
        .catch(error => { reject(error); })
    })
  }
}