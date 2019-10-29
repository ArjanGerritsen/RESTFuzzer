<template>
  <b-modal
    id="projects-add"
    ref="modal"
    scrollable
    title="Scrollable Content"
    size="lg"
    header-bg-variant="light"
    footer-bg-variant="light"
  >
    <template slot="modal-header">
      <h6>
        <font-awesome-icon icon="plus" size="1x"/>&nbsp;Add project
      </h6>
    </template>

    <b-form>
      <b-form-group id="input-group-1" label="Description:" label-for="input-1">
        <b-form-textarea
          id="input-1"
          v-model="project.description"
          required
          placeholder="Enter description"
        ></b-form-textarea>
      </b-form-group>

      <b-form-group
        id="input-group-2"
        label="OAS url:"
        label-for="input-2"
        description="Url to OpenAPI Specification"
      >
        <b-form-input id="input-2" v-model="project.oasUrl" required placeholder="Enter OAS url"></b-form-input>
      </b-form-group>
    </b-form>

    <template slot="modal-footer" slot-scope="{ cancel }">
      <b-button type="submit" variant="primary" @click="addProject()">
        <font-awesome-icon icon="plus" size="xs"/>&nbsp;add project
      </b-button>
      <b-button type="cancel" variant="secondary" @click="cancel()">cancel</b-button>
    </template>
  </b-modal>
</template>

<script>
import RestService from '../../shared/service/rest-service';

export default {
  data() {
    return {
      project: {
        description: "",
        oasUrl: ""
      },
      display: true,
      restService: new RestService(this.$bvToast)
    };
  },
  methods: {
    reset() {
      this.project.description = ""
      this.project.oasUrl = ""
    },
    cancel() {
      this.reset
      this.display = false
    },
    addProject() {
      this.restService.addProject(this.project)
      this.$nextTick(() => {
        this.$refs.modal.hide()
      });
    },
  }
};
</script>