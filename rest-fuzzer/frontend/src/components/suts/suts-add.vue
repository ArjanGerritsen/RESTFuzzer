<template>
  <b-modal
    id="suts-add"
    ref="modal"
    scrollable
    title="Scrollable Content"
    size="lg"
    header-bg-variant="light"
    footer-bg-variant="light"
  >
    <template slot="modal-header">
      <h6>
        <b-icon icon="plus" font-scale="1"></b-icon>
        &nbsp;Add system under test
      </h6>
    </template>

    <b-form>
      <b-form-group
        id="input-group-1"
        label="location:"
        label-for="input-1"
        description="Url to OpenAPI specification"
      >
        <b-form-input id="input-2" v-model="sut.location" required placeholder="enter url to OpenAPI specification"></b-form-input>
      </b-form-group>
    </b-form>

    <template slot="modal-footer" slot-scope="{ cancel }">
      <b-button type="submit" variant="primary" @click="addSut()">
        <b-icon icon="plus" font-scale="1"></b-icon>
        &nbsp;add system under test
      </b-button>
      <b-button type="cancel" variant="secondary" @click="cancel()">cancel</b-button>
    </template>
  </b-modal>
</template>

<script>
import RestService from "../../shared/services/rest-service";

export default {
  data() {
    return {
      sut: {
        location: ""
      },
      display: true,
      restService: new RestService(this.$bvToast)
    };
  },
  methods: {
    reset() {
      this.sut.location = ""
    },
    cancel() {
      this.reset
      this.display = false
    },
    addSut() {
      this.restService.addSut(this.sut)
      this.$nextTick(() => {
        this.$refs.modal.hide()
      });
    },
  }
};
</script>
