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

      <b-form-group
        label="Value:"
        label-for="input-value"
      >
        <b-form-input id="input-value" v-model="setting.value" required placeholder="Enter value"></b-form-input>
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
      setting: {
        key: "",
        type: "",
        scope: "",
        description: "",
        value: "",
      },
      display: true,
      restService: new RestService(this.$bvToast)
    };
  },
  methods: {
    reset() {
      this.setting.key = ""
      this.project.type = ""
    },
    cancel() {
      this.reset
      this.display = false
    },
    addProject() {
      this.restService.addSetting(this.setting)
      this.$nextTick(() => {
        this.$refs.modal.hide()
      });
    },
  }
};
</script>