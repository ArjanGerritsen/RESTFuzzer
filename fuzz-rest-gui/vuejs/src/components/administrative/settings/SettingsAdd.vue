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
      <h6>
        <font-awesome-icon icon="plus" size="1x"/>&nbsp;Add setting
      </h6>
    </template>

    <b-form>
      <b-form-group
        label="Key:"
        label-for="input-key"
      >
        <b-form-input id="input-key" v-model="setting.key" required placeholder="Enter key"></b-form-input>
      </b-form-group>
      
      <b-form-group label="Description:" label-for="textarea-description">
        <b-form-textarea
          id="textarea-description"
          v-model="setting.description"
          required
          placeholder="Enter description"
        ></b-form-textarea>
      </b-form-group>

      <b-form-group label="Type:">
        <b-form-radio-group
          v-model="setting.type"
          :options="typeOptions"
          name="radio-type"
        ></b-form-radio-group>
      </b-form-group>

      <b-form-group label="Scope:">
        <b-form-radio-group
          v-model="setting.scope"
          :options="scopeOptions"
          name="radio-scope"
        ></b-form-radio-group>
      </b-form-group>

      <b-form-group
        v-if="this.setting.type === 'STRING'"
        label="Value:"
        label-for="input-value-string"
      >
        <b-form-input id="input-value-string" type="text" v-model="setting.value" required placeholder="Enter value"></b-form-input>        
      </b-form-group>

      <b-form-group
        v-if="this.setting.type === 'NUMBER'"
        label="Value:"
        label-for="input-value-number"
      >
        <b-form-input id="input-value-number" type="number" v-model="setting.value" required placeholder="Enter value"></b-form-input>        
      </b-form-group>

      <b-form-group v-if="this.setting.type === 'BOOLEAN'" label="Value:">
        <b-form-radio-group
          v-model="setting.value"
          :options="booleanOptions"
          name="radio-boolean"
        ></b-form-radio-group>
      </b-form-group>      
    </b-form>

    <template slot="modal-footer" slot-scope="{ cancel }">
      <b-button type="submit" variant="primary" @click="addSetting()">
        <font-awesome-icon icon="plus" size="xs"/>&nbsp;add setting
      </b-button>
      <b-button type="cancel" variant="secondary" @click="cancel()">cancel</b-button>
    </template>
  </b-modal>
</template>

<script>
import RestService from "../../shared/RestService";

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
      display: true,
      restService: new RestService(this.$bvToast)
    };
  },
  methods: {
    reset() {
      this.setting.key = "";
      this.project.type = "";
    },
    cancel() {
      this.reset;
      this.display = false;
    },
    addSetting() {
      if (true) {
        this.restService.addSetting(this.setting);
        this.$nextTick(() => {
          this.$refs.modal.hide();
        });
      }
    }
  }
};
</script>