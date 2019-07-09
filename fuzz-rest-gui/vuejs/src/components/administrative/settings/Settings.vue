<template>
  <div>
    <div class="path">
      <h5>Administrative settings</h5>
    </div>

    <div class="row">
      <div class="col-8">
        <b-card header-tag="header">
          <span slot="header"><font-awesome-icon icon="list" size="1x" />&nbsp;Overview</span>
          <b-card-text>
            <settings-list></settings-list>
          </b-card-text>
        </b-card>
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
  </div>
</template>

<script>
  import SettingsAdd from './SettingsAdd'
  import SettingsList from './SettingsList'
  import SettingsDetail from './SettingsDetail'
  import RestService from '../../shared/RestService'

  export default {
    components: {
      SettingsAdd,
      SettingsList,
      SettingsDetail
    },
    data() {
      return {
        restService: new RestService(this.$bvToast)
      }
    },
    methods: { },
    computed: { },
    created: function() {
      this.restService.getAdminSettings();
    }
  }
</script>

<style>
  .card { margin-bottom: 20px; }
  .btn { margin-right: 10px; }
</style>