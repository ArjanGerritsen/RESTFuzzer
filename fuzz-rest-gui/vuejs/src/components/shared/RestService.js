import axios from 'axios'
import store from './Store'

export default class RestService {
  toast;

  constructor(toast) {
    this.toast = toast;
  }

  getProjects() {
    axios
      .get('rest/projects')
      .then(response => { store.commit('projects_set', { projects: response.data } ) })
      .catch(error => {
        this.handleError("Couldn't retrieve projects.", error); 
        store.commit('projects_set', { projects: [] } 
        );
      }
    )
  }

  addProject(project) {
    axios
      .post('rest/projects', project)
      .then(response => { console.log('done') })
      .catch(error => { this.handleError("Couldn't add project.", error); }
    )    
  }

  getAdministrativeTasks() {
    axios
      .get('rest/administrative/tasks')
      .then(response => { store.commit('administrative_tasks_set', { tasks: response.data } ) })
      .catch(error => {
        this.handleError("Couldn't retrieve administrative tasks.", error); 
        store.commit('administrative_tasks_set', { tasks: [] } 
        );
      }
    )
  }

  handleError(text, error) {
    this.toast.toast(text, {
      title: 'AJAX call failed',
      variant: 'danger',
      noAutoHide: true,
      appendToast: true
    })
  }
}