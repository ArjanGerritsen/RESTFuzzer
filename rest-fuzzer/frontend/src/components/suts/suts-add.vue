<template>
  <b-modal
    id="suts-add"
    ref="modal"
    size="lg"
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
        label="OAS location:"
        label-for="input-1"
        description="Url to OpenAPI specification"
      >
        <b-form-input id="input-2" v-model="sut.location" required placeholder="enter url to OpenAPI specification"></b-form-input>
      </b-form-group>
    </b-form>

    <template slot="modal-footer" slot-scope="{ cancel }">
      <div class="button-group-right">
        <b-button size="sm" type="submit" variant="primary" @click="addSut()">
          <b-icon icon="plus" font-scale="1"></b-icon>
          &nbsp;add
        </b-button>
        <b-button size="sm" type="cancel" variant="outline-secondary" @click="cancel()">
          <b-icon icon="backspace" font-scale="1"></b-icon>
          &nbsp;cancel
        </b-button>
      </div>
    </template>
  </b-modal>
</template>

<script>
  import Store from '../../store';
  import RestService from "../../shared/services/rest-service";
  import MessageService from "../../shared/services/message-service";

  export default {
    data() {
      return {
        sut: {
        location: ""
      },
      restService: new RestService(),
      messageService: new MessageService(this)
    };
  },
  methods: {
    reset() {
      this.sut.location = "";
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
    async addSut() {
      await this.restService.addSut(this.sut)
        .then(response => {
          this.messageService.info("Add sut", `Sut ${response.data.location} added successful.`);
        })
         .catch(error => {
          this.messageService.error("An error occured while adding sut", error); 
        }
      );

      this.restService.getSuts()
        .then(response => {
          Store.commit('suts_set', { suts: response.data });
        })
        .catch(error => {
          this.messageService.error("Couldn't retrieve suts", error);
          Store.commit('suts_set', { suts: [] } );
        }
      );

      this.cancel();
    },
    }
  };
</script>
