<template>
  <div>
    <div class="path">
      <h5>Projects</h5>
    </div>

    <button-group>
      <b-button type="submit" variant="primary"><font-awesome-icon icon="plus" size="1x" /></b-button>
    </button-group>

    <div class="row">
      <div class="col-8">
        <project-list></project-list>
      </div>
      <div class="col-4">
        <project></project>
      </div>
    </div>
  </div>
</template>

<script>
  import ProjectList from './List'
  import Project from './Detail'

  import store from '../shared/Store'

  import axios from 'axios';

  export default {
    components: {
      ProjectList, Project
    },
    data() {
      return {
        form: {
          email: '',
          name: '',
          checked: []
        }
      }
    },
    methods: {
      getProjects() {
        axios
          .get('rest/projects')
          .then(response => { store.commit('projects_set', { projects: response.data } ) })
          .catch(error => { console.log(error) })
          .finally(() => console.log('done loading projects') )
      }
    },
    computed: { },
    created: function () {
      setTimeout(() => { this.getProjects() }, 1500)
    },
  }
</script>

<style scoped>
  .btn {
    margin-right: 10px;
  }
</style>
