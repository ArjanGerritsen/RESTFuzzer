<template>
<b-modal
scrollable
id="suts-detail"
ref="modal"
size="lg"
>
<template slot="modal-header">
  <h6>
    <b-icon icon="eye" font-scale="1"></b-icon>
    &nbsp;System under test
  </h6>
</template>

    <b-tabs v-if="this.sut !== null" nav-tabs card>
      <b-tab title="Information" active>
        <b-card-text>
          <div class="row">
            <div class="col">
              <div class="button-group-left">
                <b-button size="sm" type="submit" variant="primary" title="start task to extract REST model description from OAS"  v-on:click="addExtractorTask">
                  <b-icon icon="download" font-scale="1"></b-icon>
                  &nbsp;start extract task
                </b-button>
                <b-button size="sm" type="submit" v-b-modal.suts-delete variant="outline-danger" title="delete this SUT">
                  <b-icon icon="trash" font-scale="1"></b-icon>
                  &nbsp;delete
                </b-button>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col">
              <dl class="dl-horizontal">
                <dt>Identifier</dt>
                <dd>{{this.sut.id}}</dd>
                <dt>Title</dt>
                <dd>{{this.sut.title ? this.sut.title : '-'}}</dd>
                <dt>OAS location</dt>
                <dd><b-link :href="this.sut.location" target="_blank">{{this.sut.location}}</b-link></dd>
              </dl>
            </div>
            <div class="col">
              <dl class="dl-horizontal">
                <dt>Description</dt>
                <dd>{{this.sut.description ? this.sut.description : '-'}}</dd>
                <dt>Created @</dt>
                <dd>{{this.sut.createdAt | date }}</dd>
              </dl>
            </div>
          </div>
        </b-card-text>
      </b-tab>
      <b-tab :disabled="this.sut.actions.length === 0" title="REST model description">
        <b-card-text>
          <list @select-item="selectAction" :fields="fields" :items="sut.actions" :formatters="formatters"></list>
        </b-card-text>
      </b-tab>
    </b-tabs>
    <suts-delete></suts-delete>

<template slot="modal-footer" slot-scope="{ cancel }">
  <div class="button-group-right">
    <b-button size="sm" type="cancel" variant="outline-secondary" @click="cancel()">
      <b-icon icon="backspace" font-scale="1"></b-icon>
      &nbsp;close
    </b-button>
  </div>
</template>
</b-modal>

</template>

<script>
  import Store from "../../store";
  import Constants from '../../shared/constants';
  import RestService from "../../shared/services/rest-service";
  import MessageService from "../../shared/services/message-service";

  import List from "../shared/list/list";

  import SutsDelete from "./suts-delete";

  export default {
    components: { List, SutsDelete },
    data() {
      return {
        formatters: [ ],
        fields: [
          { key: 'id', label: '#', thStyle: 'width: 50px;' },
          { key: 'path', thStyle: 'width: 350px;' },
          { key: 'httpMethod', label: 'Http method' }
        ],        
        restService: new RestService(),
        messageService: new MessageService(this)
      }
    },
    methods: {
      selectAction(value) { console.log(value) },
      addExtractorTask() {
        this.restService.addTask(Constants.TASK_EXTRACTOR, { sut_id : this.sut.id })
          .then(response => {
            this.messageService.info("Add task", `Task (extractor) added succesful`);
          })
          .catch(error => {
            this.messageService.error("Couldn't add task (extractor)", error);
          }
        );
      }
    },
    computed: {
      sut() {
        return Store.getters.suts.current;
      }
    },
  }
</script>
