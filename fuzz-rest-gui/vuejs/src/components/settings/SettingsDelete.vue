<template>
  <div>
    <b-modal id="settings-delete" ref="modal" centered v-if="this.setting" size="xs">
      <template slot="modal-header">
        <h6><font-awesome-icon icon="trash-alt" size="1x" />&nbsp;Delete setting</h6>
      </template>

      <template slot="default">
        Are you sure you want to delete setting '{{this.setting.key}}'.
      </template>

      <template slot="modal-footer" slot-scope="{ cancel }">
        <b-button variant="outline-danger" @click="deleteSetting()">
          <font-awesome-icon icon="trash-alt" size="xs" />&nbsp; delete
        </b-button>
        <b-button variant="outline-secondary" @click="cancel()">
          <font-awesome-icon icon="times-circle" size="xs" />&nbsp; cancel
        </b-button>
      </template>
    </b-modal>
  </div>
</template>

<script>
import Store from '../shared/Store';
import RestService from '../shared/RestService';
import MessageService from '../shared/MessageService';

export default {
  data() {
    return {
      restService: new RestService(this.$bvToast),
      messageService: new MessageService(this.$bvToast)
    }
  }, 
  computed: {
    setting() { return Store.getters.admin_setting; }
  },
  methods: {
    hide() {
      this.$nextTick(() => {
        this.$refs.modal.hide();
      });
    },
    async deleteSetting() {
      await this.restService.deleteSetting(this.setting)
        .then(response => {
          this.messageService.info("Delete setting", "Setting deleted successful.");
          Store.commit('admin_setting_set', { setting: null } );
        })
         .catch(error => {
          this.messageService.error("An error occured while deleting setting", error); 
        }
      );

      this.restService.getAdminSettings()
        .then(settings => {
          Store.commit('admin_settings_set', { settings: settings });
        })
        .catch(error => {
          this.messageService.error("Couldn't retrieve settings", error);
          Store.commit('admin_settings_set', { settings: [] } );
        }
      );

      this.hide();
    }
  }
}
</script>