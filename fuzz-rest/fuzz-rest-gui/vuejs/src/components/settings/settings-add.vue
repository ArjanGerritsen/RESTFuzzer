<template>
  <b-modal
    id="settings-add"
    ref="modal"
    scrollable
    size="lg"
    header-bg-variant="light"
    footer-bg-variant="light"
  >
    <template slot="modal-header">
      <h6 v-if="!updateMode">
        <font-awesome-icon icon="plus" size="1x"/>&nbsp;Add setting
      </h6>
      <h6 v-if="updateMode">
        <font-awesome-icon icon="wrench" size="1x"/>&nbsp;Edit setting
      </h6>
    </template>

    <b-form>
      <b-form-group label="Key:" label-for="input-key">
        <b-form-input 
          id="input-key"
          v-model="setting.key"
          placeholder="Enter key">
        </b-form-input>
      </b-form-group>

      <b-form-group label="Description:" label-for="textarea-description">
        <b-form-textarea
          id="textarea-description"
          v-model="setting.description"
          placeholder="Enter description"
        ></b-form-textarea>
      </b-form-group>

      <b-form-group label="Type:">
        <b-form-radio-group
          id="radio-type"
          v-model="setting.type"
          :options="typeOptions"
          name="radio-type"
        ></b-form-radio-group>
      </b-form-group>

      <b-form-group label="Scope:">
        <b-form-radio-group
          id="radio-scope"
          v-model="setting.scope"
          :options="scopeOptions"
          name="radio-scope"
        ></b-form-radio-group>
      </b-form-group>

      <b-form-group
        v-if="setting.type === 'STRING'"
        label="Value:"
        label-for="input-value-string"
      >
        <b-form-input
          id="input-value-string" 
          type="text" 
          v-model="setting.value"
          placeholder="Enter value">
        </b-form-input>
      </b-form-group>

      <b-form-group
        v-if="setting.type === 'NUMBER'"
        label="Value:"
        label-for="input-value-number"
      >
        <b-form-input
          id="input-value-number"
          type="number"
          v-model="setting.value"
          placeholder="Enter value">
        </b-form-input>
        <b-form-invalid-feedback id="input-value-validation">
          Value is a required field.
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group v-if="setting.type === 'BOOLEAN'" label="Value:">
        <b-form-radio-group
          v-model="setting.value"
          :options="booleanOptions"
          name="radio-boolean"
        ></b-form-radio-group>
      </b-form-group>
    </b-form>

    <template slot="modal-footer" slot-scope="{ cancel }">
      <b-button v-if="!updateMode" variant="primary" @click="addSetting()">
        <font-awesome-icon icon="plus" size="1x"/>&nbsp;add setting
      </b-button>
      <b-button v-if="updateMode" variant="primary" @click="updateSetting()">
        <font-awesome-icon icon="wrench" size="1x"/>&nbsp;update setting
      </b-button>
      <b-button variant="outline-secondary" @click="cancel()">
        <font-awesome-icon icon="times-circle" size="xs" />&nbsp; cancel
      </b-button>
    </template>
  </b-modal>
</template>

<script>
import Store from '../../store/store';
import RestService from '../../shared/service/rest-service';
import MessageService from '../../shared/service/message-service';

export default {
  data() {
    return {
      typeOptions: [
        { text: 'String', value: 'STRING' },
        { text: 'Number', value: 'NUMBER' },
        { text: 'Boolean', value: 'BOOLEAN' }
      ],
      scopeOptions: [
        { text: 'Global', value: 'GLOBAL' },
        { text: 'Project', value: 'PROJECT' }
      ],
      booleanOptions: [
        { text: 'True', value: 'TRUE' },
        { text: 'False', value: 'FALSE' }
      ],
      setting: {
        key: "",
        type: null,
        scope: null,
        description: "",
        value: ""
      },
      restService: new RestService(this.$bvToast),
      messageService: new MessageService(this)
    };
  },
  methods: {
    reset() {
      this.setting.key = "";
      this.setting.type = null;
      this.setting.scope = null;
      this.setting.description = "";
      this.setting.value = "";
    },
    hide() {
      this.$nextTick(() => {
        this.$refs.modal.hide();
      });
    },
    cancel() {
      this.reset();
      this.hide();
    },
    async addSetting() {
      await this.restService.addSetting(this.setting)
        .then(response => {
          this.messageService.info("Add setting", "Setting added successful.");

          this.cancel();

          this.restService.getAdminSettings()
            .then(settings => {
                Store.commit('admin_settings_set', { settings: settings });
            })
            .catch(error => {
              this.messageService.error("Couldn't retrieve administrative settings", error); 
              Store.commit('admin_settings_set', { settings: [] } );
            }
          );
        })
        .catch(error => {
          this.messageService.error("An error occured while saving setting", error); 
        }
      );
    }
  },
  computed: {
    updateMode() {
      return this.settings && this.setting.id !== 0;
    }
  }
};
</script>