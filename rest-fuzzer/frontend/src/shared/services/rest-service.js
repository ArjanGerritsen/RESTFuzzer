import axios from "axios";

export default class RestService {

  getSuts() {
    return new Promise( function (resolve, reject) {
    axios
      .get("/rest/suts")
      .then(response => { resolve(response); })
      .catch(error => { reject(error); } ) 
    })
  }

  addSut(sut) {
    return new Promise( function (resolve, reject) {
      axios
        .post('/rest/suts', sut)
        .then(response => { resolve(response); })
        .catch(error => { reject(error); } ) 
    })
  }

  deleteSut(sut) {
    return new Promise( function (resolve, reject) {
      axios
        .delete(`/rest/suts/${sut.id}`)
        .then(response => { resolve(response); })
        .catch(error => { reject(error); } )
    })
  }

  getQueuedTasks() {
    return new Promise( function (resolve, reject) {
    axios
      .get("/rest/tasks/status/queued")
      .then(response => { resolve(response); })
      .catch(error => { reject(error); } ) 
    })
  }

  getRunningTasks() {
    return new Promise( function (resolve, reject) {
    axios
      .get("/rest/tasks/status/running")
      .then(response => { resolve(response); })
      .catch(error => { reject(error); } ) 
    })
  }

  getCompletedTasks() {
    return new Promise( function (resolve, reject) {
    axios
      .get("/rest/tasks/status/completed")
      .then(response => { resolve(response); })
      .catch(error => { reject(error); } ) 
    })
  }  

  addTask(name, metaDataTuples) {
    return new Promise( function (resolve, reject) {
      axios
        .post(`/rest/tasks/${name}/start`, metaDataTuples)
        .then(response => { resolve(response); })
        .catch(error => { reject(error); } )
    })    
  }
}