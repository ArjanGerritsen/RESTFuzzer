<template>
  <default-table @select-item="selectProject" :fields="fields" :items="projects" :formatters="formatters"></default-table>
</template>

<script>
  import DefaultTable from '../shared/DefaultTable'
  import Store from '../shared/Store'

  export default {
    components: {
      DefaultTable
    },
    data() {
      return {
        formatters: [
          { field: 'createdAt', as: 'date' },
        ],
        fields: [
          { key: 'status', thStyle: 'width: 100px;' },
          { key: 'oasUrl', label: 'OAS url', thStyle: 'width: 400px;' },
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