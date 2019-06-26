<template>
  <default-table @select-item="selectTask" :fields="fields" :items="tasks" :formatters="formatters"></default-table>
</template>

<script>
  import DefaultTable from '../../shared/DefaultTable'
  import Store from '../../shared/Store'

  export default {
    components: {
      DefaultTable
    },
    data() {
      return {
        formatters: [
          { field: 'createdAt', as: 'date' },
          { field: 'lastStartedAt', as: 'date' },
        ],
        fields: [
          { key: 'id', thStyle: 'width: 100px;' },
          { key: 'status', thStyle: 'width: 100px;' },
          { key: 'type', label: 'type', thStyle: 'width: 200px;' },
          { key: 'description' },
          { key: 'createdAt', label: 'Created @', thStyle: 'width: 150px;' },
          { key: 'lastStartedAt', label: 'Last started @', thStyle: 'width: 150px;' },
        ],
      }
    },
    methods: { 
      selectTask(task) {
    //     Store.commit('project_set', { project: value } )
      }
    },
    computed: { 
      tasks() {
        return Store.getters.administrative_tasks
      }
    },
    created: function () { },
  }
</script>