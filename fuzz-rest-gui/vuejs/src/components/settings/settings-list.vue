<template>
  <div>
    <b-card header-tag="header">
      <span slot="header"><font-awesome-icon icon="list" size="1x" />&nbsp;Overview</span>
      <b-card-text>
        <default-list @select-item="selectSetting" @deselect-item="deselectSetting" :fields="fields" :items="settings" :formatters="formatters"></default-list>
      </b-card-text>
    </b-card>
  </div>
</template>

<script>
  import Store from '../../store/store';
  import DefaultList from '../../shared/default-list';

  export default {
    components: { DefaultList },
    data() {
      return {
        formatters: [
          { field: 'createdAt', as: 'date' },
          { field: 'updatedAt', as: 'date' },
        ],
        fields: [
          { key: 'scope', thStyle: 'width: 100px;' },
          { key: 'key', thStyle: 'width: 300px;' },
          { key: 'type', thStyle: 'width: 100px;' },
          { key: 'value', thStyle: 'width: 300px;' },
          { key: 'description' },
          { key: 'createdAt', label: 'Created @', thStyle: 'width: 150px;' },
          { key: 'updatedAt', label: 'Updated @', thStyle: 'width: 150px;' }
        ]
      };
    },
    methods: {
      selectSetting(setting) { Store.commit('admin_setting_set', { setting: setting } ); },
      deselectSetting() { Store.commit('admin_setting_set', { setting: null } ); }
    },
    computed: { 
      settings() { return Store.getters.admin_settings; }
    },
    created: function () { }
  }
</script>