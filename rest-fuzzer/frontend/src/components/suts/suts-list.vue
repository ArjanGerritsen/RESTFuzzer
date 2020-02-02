<template>
  <div>
    <b-card header-tag="header">
      <span slot="header">
        <b-icon icon="list-task" font-scale="1"></b-icon>
        Systems under test
      </span>
      <b-card-text>
        <div class="button-group-left">
          <b-button size="sm" type="submit" variant="primary" v-b-modal.suts-add>
            <b-icon icon="plus" font-scale="1"></b-icon>
            &nbsp;add
          </b-button>
        </div>
        <list @select-item="selectSut" :fields="fields" :items="suts" :formatters="formatters"></list>
      </b-card-text>
    </b-card>
  </div>
</template>

<script>
import List from "../shared/list/list";

import Store from "../../store/index";
import RestService from "../../shared/services/rest-service";
import MessageService from "../../shared/services/message-service";

export default {
  components: { List },
  data() {
    return {
      formatters: [
        { field: 'createdAt', as: 'dateShort' }
      ],
      fields: [
        { key: 'id', label: '#', thStyle: 'width: 50px;' },
        { key: 'title', thStyle: 'width: 250px;' },
        { key: 'location', label: 'OAS location' },
        { key: 'createdAt', label: 'Created @', thStyle: 'width: 110px;' }
      ],
      restService: new RestService(),
      messageService: new MessageService(this)
    }
  },
  methods: { 
	getSuts() {
	  this.restService.getSuts()
	  .then(response => {
	    Store.commit('suts_set', { suts: response.data });
      })
      .catch(error => {
	    this.messageService.error("Couldn't retrieve suts", error);
	    Store.commit('suts_set', { suts: [] } );
      }		    );
	},
	getSut(id) {
      this.restService.getSut(id)
	  .then(response => {
	     Store.commit('sut_set', { sut: response.data });
       })
      .catch(error => {
        this.messageService.error("Couldn't retrieve sut", error);
        Store.commit('sut_set', { sut: null } );
	  }	    );	
    },
    selectSut(sut) { 
      this.getSut(sut.id);
      this.$bvModal.show('suts-detail');
    },
  },
  computed: { 
    suts() { return Store.getters.suts.all },
  },
  created: function () {
	this.getSuts();
  },
}
</script>
