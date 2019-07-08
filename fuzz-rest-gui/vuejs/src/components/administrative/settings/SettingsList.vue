<template>
  <default-table @select-item="selectSetting" :fields="fields" :items="settings" :formatters="formatters"></default-table>
</template>

<script>
  import DefaultTable from '../../shared/DefaultList'
  import Store from '../../shared/Store'

  export default {
    components: { DefaultTable },
    data() {
      return {
        formatters: [
          { field: 'createdAt', as: 'date' },
          { field: 'updatedAt', as: 'date' },
        ],
        fields: [
          { key: 'key', thStyle: 'width: 100px;' },
          { key: 'type', thStyle: 'width: 200px;' },
          { key: 'scope', thStyle: 'width: 200px;' },
          { key: 'description' },
          { key: 'value', thStyle: 'width: 200px;' },
          { key: 'createdAt', label: 'Created @', thStyle: 'width: 150px;' },
          { key: 'updatedAt', label: 'Updated @', thStyle: 'width: 150px;' },
        ],
      }
    },
    methods: {
      selectSetting(setting) { Store.commit('admin_setting_set', { setting: setting } ) },
    },
    computed: { 
      settings() { return Store.getters.admin_settings }
    },
    created: function () { },
  }
</script>