import axios from 'axios'
import store from './Store'

export default class RestService {
  static toast;

  constructor(toast) {
    RestService.toast = toast;
  }

  getProjects() {
    axios
      .get('/rest/projects')
      .then(response => { store.commit('projects_set', { projects: response.data } ) })
      .catch(error => {
        RestService.handleError("Couldn't retrieve projects.", error); 
        store.commit('projects_set', { projects: [] } 
        );
      }
    )
  }

  addProject(project) {
    axios
      .post('/rest/projects', project)
      .then(response => { console.log('done') })
      .catch(error => { RestService.handleError("Couldn't add project.", error); }
    )    
  }

  getAdminTasks() {
    axios
      .get('/rest/admin/tasks')
      .then(response => { store.commit('admin_tasks_set', { tasks: response.data } ) })
      .catch(error => {
        RestService.handleError("Couldn't retrieve administrative tasks.", error); 
        store.commit('admin_tasks_set', { tasks: [] } 
        );
      }
    )
  }

  getAdminSettings() {
    axios
      .get('/rest/admin/settings')
      .then(response => { store.commit('admin_settings_set', { settings: response.data } ) })
      .catch(error => {
        RestService.handleError("Couldn't retrieve administrative settings.", error); 
        store.commit('admin_settings_set', { settings: [] } 
        );
      }
    )
  }

  addSetting(setting) {
    axios
      .post('/rest/admin/settings', setting)
      .then(response => { RestService.displayToast("Add setting", "Setting added successful."); })
      .catch(error => { RestService.handleError("Couldn't add setting.", error); }
    )    
  }  

  getAdminTaskEvents(taskId, events) {
    axios
      .get(`/rest/admin/tasks/${taskId}/events`)
      .then(response => { events = response.data })
      .catch(error => { RestService.handleError("Couldn't retrieve events for administrative task.", error) }
    )
  }

  static handleError(text, error) {
    this.toast.toast(text, {
      title: 'AJAX call failed',
      variant: 'danger',
      noAutoHide: true,
      appendToast: true
    })
  }

  static displayToast(title, text) {
    this.toast.toast(text, {
      title: title,
      variant: 'primary',
      noAutoHide: false,
      appendToast: true
    })
  }  
}