<template>
  <default-list @select-item="selectProject" :fields="fields" :items="projects" :formatters="formatters"></default-list>
</template>

<script>
  import DefaultList from '../shared/DefaultList'
  import Store from '../shared/Store'

  export default {
    components: { DefaultList },
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
      selectProject(value) { Store.commit('project_set', { project: value } ) },
    },
    computed: { 
      projects() { return Store.getters.projects },
    },
    created: function () { },
  }
</script>