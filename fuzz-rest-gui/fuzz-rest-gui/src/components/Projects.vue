<template>
  <div>
    <div class="path">
      <h5>Projects</h5>
    </div>

    <b-button type="submit" variant="primary">add project</b-button>
    
    <b-button type="submit" variant="primary" @click="emptyList">empty list</b-button>

    <b-button type="submit" variant="primary" @click="fullList">full list</b-button>

    <b-button type="submit" variant="primary" @click="loadingList">loading</b-button>

    <fr-table @select-item="selectProject" :fields="fields" :items="items"></fr-table>

    <div>{{this.selectedProject}}</div>
  </div>
</template>

<script>
  import FrTable from './shared/FrTable'

  export default {
    components: {
      FrTable
    },
    data() {
      return {
        form: {
          email: '',
          name: '',
          checked: []
        },
        selectedProject: null,
        fields: [
          { key: 'status', thStyle: 'width: 100px;' },
          { key: 'oas_url', label: 'OpenAPI Specifcation', thStyle: 'width: 400px;' },
          { key: 'description' },
          { key: 'run_count', label: '# Runs', thStyle: 'width: 150px;' },
          { key: 'created_at', label: 'Created @', thStyle: 'width: 175px;' },
          { key: 'last_started_at', label: 'Started @', thStyle: 'width: 175px;' },
          { key: 'last_finished_at', label: 'Finished @', thStyle: 'width: 175px;' }
        ],
        items: null
      }
    },
    methods: { 
      selectProject(value) {
        this.selectedProject = value;
      },
      emptyList() {
        this.items = [];
      },
      loadingList() {
        this.items = null;
        setTimeout(() => { this.fullList(); }, 1500);
      },
      fullList() {
        this.items = [
          { status: 'running', description: 'Met config 8', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 7', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 6', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 5', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 4', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 3', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 2', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 1', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() }
        ];
      }
    },
    computed: { },
    created: function () {
      setTimeout(() => { this.fullList(); }, 1500);
    }
  }
</script>

<style scoped>
  .btn {
    margin-right: 10px;
  }
</style>
