<template>
  <fr-table @select-item="selectTask" :fields="fields" :items="items" :formatters="formatters"></fr-table>
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
        //   { key: 'status', thStyle: 'width: 100px;' },
        //   { key: 'oas_url', label: 'OpenAPI Specifcation', thStyle: 'width: 400px;' },
          { key: 'description' },
        //   { key: 'run_count', label: '# Runs', thStyle: 'width: 150px;' },
          { key: 'created_at', label: 'Created @', thStyle: 'width: 150px;' },
        //   { key: 'last_started_at', label: 'Started @', thStyle: 'width: 150px;' },
        //   { key: 'last_finished_at', label: 'Finished @', thStyle: 'width: 150px;' }
        ],
        items: null
      }
    },
    methods: { 
      fullList() {
        this.items = [
          { name: 'Parse OAS', description: 'Parsing the OAS. Etract and persist all information to the database. ', java_name: 'nl.ou.se.fuzz-rest.tasks.OasParser', created_at: new Date() },
          { name: 'Prepare fuzzing', description: 'Parsing the OAS. Etract and persist all information to the database. ', java_name: 'nl.ou.se.fuzz-rest.tasks.OasParser', created_at: new Date() },
          { name: 'Execute fuzzing', description: 'Parsing the OAS. Etract and persist all information to the database. ', java_name: 'nl.ou.se.fuzz-rest.tasks.OasParser', created_at: new Date() },
          { name: 'Create report', description: 'Parsing the OAS. Etract and persist all information to the database. ', java_name: 'nl.ou.se.fuzz-rest.tasks.OasParser', created_at: new Date() }
        ];
      },
      selectTask(value) {
        // store.commit('project_set', { project: value } )
      }
    },
    computed: { },
    created: function () {
      setTimeout(() => { this.fullList(); }, 1500)
    }
  }
</script>