<template>
  <div>
    <b-card header-tag="header">
      <span slot="header">
        <b-icon icon="list-task" font-scale="1"></b-icon>
        &nbsp;Systems under test
      </span>
      <b-card-text>
        <list @select-item="selectSut" :fields="fields" :items="suts" :formatters="formatters"></list>
      </b-card-text>
    </b-card>
  </div>
</template>

<script>
import List from "../shared/list/list";
import Store from "../../store/index";
import RestService from "../../shared/services/rest-service";

export default {
  components: { List },
  data() {
    return {
      formatters: [
        { field: 'createdAt', as: 'date' },
      ],
      fields: [
        { key: 'id', label: '#', thStyle: 'width: 30px;' },
        { key: 'title', thStyle: 'width: 200px;' },
        { key: 'location', label: 'OAS location' },
        { key: 'createdAt', label: 'Created @', thStyle: 'width: 150px;' }
      ],
      restService: new RestService(this.$bvToast)
    }
  },
  methods: { 
    selectSut(value) { Store.commit('sut_set', { sut: value } ) },
  },
  computed: { 
    suts() { return Store.getters.suts },
  },
  created: function () {
	  this.restService.getSuts()
      .then(response => {
        Store.commit('suts_set', { suts: response.data });
      })
      .catch(error => {
        this.messageService.error("Couldn't retrieve suts", error);
        Store.commit('suts_set', { suts: [] } );
      }
    );
  },
}
</script>
