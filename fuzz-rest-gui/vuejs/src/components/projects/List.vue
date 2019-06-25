<template>
  <fr-table @select-item="selectProject" :fields="fields" :items="projects" :formatters="formatters"></fr-table>
</template>

<script>
  import FrTable from '../shared/FrTable'

  import Store from '../shared/Store'

  export default {
    components: {
      FrTable
    },
    data() {
      return {
        formatters: [
          { field: 'createdAt', as: 'date' },
        ],
        fields: [
          { key: 'status', thStyle: 'width: 100px;' },
          { key: 'oasUrl', label: 'OpenAPI Specifcation', thStyle: 'width: 400px;' },
          { key: 'description' },
          { key: 'createdAt', label: 'Created @', thStyle: 'width: 150px;' },
        ],
      }
    },
    methods: { 
      selectProject(value) {
        Store.commit('project_set', { project: value } )
      }
    },
    computed: { 
      projects() {
        return Store.getters.projects
      }
    },
    created: function () { },
  }
</script>