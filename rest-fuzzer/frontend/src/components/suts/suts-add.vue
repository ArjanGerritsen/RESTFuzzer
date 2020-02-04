<template>
  <b-modal id="suts-add" ref="modal" size="lg">
    <template slot="modal-header">
      <h6>
        <b-icon icon="plus" font-scale="1"></b-icon>&nbsp;Add system under test
      </h6>
    </template>

    <b-form>
      <b-form-group
        id="input-group-1"
        label="OAS location:"
        label-for="input-1"
        description="Url to OpenAPI specification"
      >
        <b-form-input
          id="input-2"
          v-model="sut.location"
          required
          placeholder="enter url to OpenAPI specification"
        ></b-form-input>
      </b-form-group>
    </b-form>

    <template slot="modal-footer" slot-scope="{ cancel }">
      <div class="button-group-right">
        <b-button size="sm" type="submit" variant="primary" @click="addSut()">
          <b-icon icon="plus" font-scale="1"></b-icon>&nbsp;add
        </b-button>
        <b-button size="sm" type="cancel" variant="outline-secondary" @click="cancel()">
          <b-icon icon="backspace" font-scale="1"></b-icon>&nbsp;cancel
        </b-button>
      </div>
    </template>
  </b-modal>
</template>

<script>
export default {
  data() {
    return {
      sut: {
        location: ""
      }
    };
  },
  methods: {
    resetForm() {
      this.sut.location = "";
    },
    hide() {
      this.$nextTick(() => {
        this.$refs.modal.hide();
      });
    },
    cancel() {
      this.resetForm();
      this.hide();
    },
    addSut() {
      this.$store.dispatch("addSut", this.sut);
      // TODO promise terug ... en dan verversen lijst met suts ...
      // this.cancel();
    }
  }
};
</script>
