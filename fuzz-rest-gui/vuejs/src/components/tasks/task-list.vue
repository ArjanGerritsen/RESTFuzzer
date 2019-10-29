<template>
  <default-table @select-item="selectTask" :fields="fields" :items="tasks" :formatters="formatters"></default-table>
</template>

<script>
  import Store from '../../store/store'
  import DefaultTable from '../../shared/default-list'

  export default {
    components: { DefaultTable },
    data() {
      return {
        formatters: [
          { field: 'createdAt', as: 'date' },
          { field: 'lastStartedAt', as: 'date' },
          { field: 'status_icon', as: 'html' },
        ],
        fields: [
          { key: 'status', thStyle: 'width: 100px;' },
          { key: 'type', label: 'type', thStyle: 'width: 200px;' },
          { key: 'description' },
          { key: 'createdAt', label: 'Created @', thStyle: 'width: 150px;' },
          { key: 'lastStartedAt', label: 'Last started @', thStyle: 'width: 150px;' },
        ],
      }
    },
    methods: {
      selectTask(task) { Store.commit('admin_task_set', { task: task } ) },
    },
    computed: { 
      tasks() { return Store.getters.admin_tasks }
    },
    created: function () { },
  }
</script>