<template>
  <fr-table @select-item="selectProject" :fields="fields" :items="items" :formatters="formatters"></fr-table>
</template>

<script>
  import FrTable from '../shared/FrTable'
  import store from '../../plugins/veux'

  export default {
    components: {
      FrTable
    },
    data() {
      return {
        formatters: [
          { field: 'created_at', as: 'date' },
          { field: 'last_started_at', as: 'date' },
          { field: 'last_finished_at', as: 'date' }
        ],
        fields: [
          { key: 'status', thStyle: 'width: 100px;' },
          { key: 'oas_url', label: 'OpenAPI Specifcation', thStyle: 'width: 400px;' },
          { key: 'description' },
          { key: 'run_count', label: '# Runs', thStyle: 'width: 150px;' },
          { key: 'created_at', label: 'Created @', thStyle: 'width: 150px;' },
          { key: 'last_started_at', label: 'Started @', thStyle: 'width: 150px;' },
          { key: 'last_finished_at', label: 'Finished @', thStyle: 'width: 150px;' }
        ],
        items: null
      }
    },
    methods: { 
      fullList() {
        this.items = [
          { status: 'running', description: 'Met config 8', oas_url: 'http://localhost/swagger.json', created_at: new Date().getTime()+3600000, run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 7', oas_url: 'http://localhost/swagger.json', created_at: new Date().getTime()+7200000, run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 6', oas_url: 'http://localhost/swagger.json', created_at: new Date().getTime()+1000000, run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 5', oas_url: 'http://localhost/swagger.json', created_at: new Date().getTime()+2000000, run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 4', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 3', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 2', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 1', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 5', oas_url: 'http://localhost/swagger.json', created_at: new Date().getTime()+2000000, run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 4', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 3', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 2', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 1', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 5', oas_url: 'http://localhost/swagger.json', created_at: new Date().getTime()+2000000, run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 4', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 3', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 2', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 1', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() }
        ];
      },
      selectProject(value) {
        store.commit('project_set', { project: value } )
      }
    },
    computed: { },
    created: function () {
      setTimeout(() => { this.fullList(); }, 1500)
    }
  }
</script>