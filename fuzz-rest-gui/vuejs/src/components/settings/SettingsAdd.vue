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
      <h6><font-awesome-icon icon="plus" size="1x"/>&nbsp;Add setting</h6>
    </template>

    <b-form @submit.stop.prevent="addSetting">
      <b-form-group label="Key:" label-for="input-key">
        <b-form-input 
          id="input-key" 
          v-model="$v.setting.key.$model"
          :state="$v.setting.key.$dirty ? !$v.setting.key.$error : null"
          aria-describedby="input-key-validation"
          placeholder="Enter key">
        </b-form-input>
        <b-form-invalid-feedback id="input-key-validation">
          Key is a required field and should be at least 3 characters and at most 32.
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group label="Description:" label-for="textarea-description">
        <b-form-textarea
          id="textarea-description"
          v-model="$v.setting.description.$model"
          :state="$v.setting.description.$dirty ? !$v.setting.description.$error : null"
          aria-describedby="input-description-validation"
          placeholder="Enter description"
        ></b-form-textarea>
        <b-form-invalid-feedback id="input-description-validation">
          Description is a required field.
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group label="Type:">
        <b-form-radio-group
          id="radio-type"
          v-model="$v.setting.type.$model"
          :state="$v.setting.type.$dirty ? !$v.setting.type.$error : null"
          aria-describedby="input-type-validation"
          :options="typeOptions"
          name="radio-type"
        ></b-form-radio-group>
        <b-form-invalid-feedback id="input-type-validation">
          Type is a required field.
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group label="Scope:">
        <b-form-radio-group
          id="radio-scope"
          v-model="$v.setting.scope.$model"
          :state="$v.setting.scope.$dirty ? !$v.setting.scope.$error : null"
          aria-describedby="input-scope-validation"
          :options="scopeOptions"
          name="radio-scope"
        ></b-form-radio-group>
        <b-form-invalid-feedback id="input-type-validation">
          Scope is a required field.
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group
        v-if="this.setting.type === 'STRING'"
        label="Value:"
        label-for="input-value-string"
      >
        <b-form-input
          id="input-value-string" 
          type="text" 
          v-model="$v.setting.value.$model"
          :state="$v.setting.value.$dirty ? !$v.setting.value.$error : null"
          aria-describedby="input-value-validation"
          placeholder="Enter value">
        </b-form-input>
        <b-form-invalid-feedback id="input-value-validation">
          Value is a required field.
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group
        v-if="this.setting.type === 'NUMBER'"
        label="Value:"
        label-for="input-value-number"
      >
        <b-form-input
          id="input-value-number"
          type="number"
          v-model="$v.setting.value.$model"
          :state="$v.setting.value.$dirty ? !$v.setting.value.$error : null"
          aria-describedby="input-value-validation"
          placeholder="Enter value">
        </b-form-input>
        <b-form-invalid-feedback id="input-value-validation">
          Value is a required field.
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group v-if="this.setting.type === 'BOOLEAN'" label="Value:">
        <b-form-radio-group
          v-model="setting.value"
          :options="booleanOptions"
          name="radio-boolean"
        ></b-form-radio-group>
      </b-form-group>

      <b-button type="submit" variant="primary" :disabled="$v.setting.$invalid">
        <font-awesome-icon icon="plus" size="xs"/>&nbsp;add setting
      </b-button>
    </b-form>

    <template slot="modal-footer">
      <b-button type="cancel" variant="secondary" @click="cancel()">cancel</b-button>
    </template>
  </b-modal>
</template>

<script>
import Store from "../shared/Store";
import RestService from "../shared/RestService";
import MessageService from "../shared/MessageService";

import { required, minLength, maxLength } from 'vuelidate/lib/validators';

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
      messageService: new MessageService(this.$bvToast)
    };
  },
  validations: {
    setting: {
      key: { required, minLength: minLength(3), maxLength: maxLength(32) },
      type: { required },
      scope: { required },
      description: { required },
      value: { required }
    }
  },
  methods: {
    reset() {
      this.setting.key = "";
      this.setting.type = null;
      this.setting.scope = null;
      this.setting.description = "";
      this.setting.value = "";
      this.$v.$reset;
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
      this.$v.setting.$touch;
      if (!this.$v.setting.$invalid) {
        await this.restService.addSetting(this.setting)
          .then(response => {
            this.messageService.info("Add setting", "Setting added successful.");
            this.reset;
          })
          .catch(error => {
            this.messageService.error("An error occured while saving setting", error); 
          }
        );

        this.restService.getAdminSettings()
          .then(settings => {
             Store.commit('admin_settings_set', { settings: settings });
          })
          .catch(error => {
            this.messageService.error("Couldn't retrieve administrative settings", error); 
            Store.commit('admin_settings_set', { settings: [] } );
          }
        );

        this.cancel();
      }
    }
  }
};
</script>