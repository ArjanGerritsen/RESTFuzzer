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

  getTasksProgress() {
    return new Promise( function (resolve, reject) {
    axios
      .get("/rest/tasks/progress")
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