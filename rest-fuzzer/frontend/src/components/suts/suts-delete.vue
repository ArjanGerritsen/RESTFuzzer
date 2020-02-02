<template>
  <b-modal id="suts-delete" ref="modal" centered v-if="this.sut" size="xs">
    <template slot="modal-header">
      <h6><b-icon icon="trash" font-scale="1"></b-icon>&nbsp;Delete sut</h6>
    </template>

    <template slot="default">
      Are you sure you want to delete sut '{{this.sut.location}}'.
    </template>

    <template slot="modal-footer" slot-scope="{ cancel }">
      <div class="button-group-right">
        <b-button size="sm" variant="outline-danger" @click="deleteSut()">
          <b-icon icon="trash" font-scale="1"></b-icon>&nbsp; delete
        </b-button>
        <b-button size="sm" variant="outline-secondary" @click="cancel()">
          <b-icon icon="backspace" font-scale="1"></b-icon>&nbsp; cancel
        </b-button>
      </div>
    </template>
  </b-modal>
</template>

<script>
  import Store from '../../store';
  import RestService from '../../shared/services/rest-service';
  import MessageService from '../../shared/services/message-service';

  export default {
    data() {
      return {
      restService: new RestService(),
      messageService: new MessageService(this)
      }
    },
    computed: {
      sut() { return Store.getters.suts.current; }
    },
    methods: {
      async deleteSut() {
        await this.restService.deleteSut(this.sut)
          .then(response => {
            this.messageService.info("Delete sut", `Sut ${response.data.location} deleted successful.`);
            Store.commit('sut_set', { sut: null });
            // TODO FIX MELDING NIET IN BEELD
          })
          .catch(error => {
            this.messageService.error("An error occured while deleting sut", error); 
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
      }
    }
  }
</script>
