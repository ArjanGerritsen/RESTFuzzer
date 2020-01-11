<template>
  <div>
    <div class="path">
      <h5>Settings</h5>
    </div>

    <div class="row">
      <div class="col-8">
        <settings-list></settings-list>
      </div>
      <div class="col-4">
        <b-card header-tag="header">
          <span slot="header"><font-awesome-icon icon="hammer" size="1x" />&nbsp;Actions</span>
          <b-button type="submit" variant="primary" v-b-modal.settings-add><font-awesome-icon icon="plus" size="xs" />&nbsp;add setting</b-button>
        </b-card>
        <settings-detail></settings-detail>
      </div>
    </div>

    <settings-add></settings-add>
    <settings-delete></settings-delete>
  </div>
</template>

<script>
  import SettingsAdd from './settings-add';
  import SettingsList from './settings-list';
  import SettingsDetail from './settings-detail';
  import SettingsDelete from './settings-delete';

  import Store from '../../store/store';
  import RestService from '../../shared/service/rest-service';
  import MessageService from '../../shared/service/message-service';

  export default {
    components: {
      SettingsAdd,
      SettingsList,
      SettingsDetail,
      SettingsDelete
    },
    data() {
      return {
        restService: new RestService(this.$bvToast),
        messageService: new MessageService(this.$bvToast)
      }
    },
    methods: { },
    computed: { },
    created: function() {
      this.restService.getAdminSettings()
        .then(settings => Store.commit('admin_settings_set', { settings: settings } ) )
        .catch(error => {
          this.messageService.error('Couldn\'t retrieve administrative settings.', error);
          Store.commit('admin_settings_set', { settings: [] } );
        }
      );
    }
  }
</script>

<style>
  .card { margin-bottom: 20px; }
  .btn { margin-right: 10px; }
</style>