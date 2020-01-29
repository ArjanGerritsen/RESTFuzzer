<template>
  <b-card class="card-with-top-margin" v-if="this.sut !== null" no-body>
    <b-tabs nav-tabs card>
      <b-tab title="Information" active>
        <b-card-text>
          <div class="row">
            <div class="col" style="margin:5px 0px 15px 0px;">
              <b-button type="submit" variant="primary" title="start task to extract REST model description from OAS" style="margin-right:15px;" v-on:click="addExtractorTask"><b-icon icon="download" font-scale="1"></b-icon>&nbsp;start extract task</b-button>
              <b-button type="submit" v-b-modal.suts-delete variant="outline-danger" title="delete this SUT"><b-icon icon="trash" font-scale="1"></b-icon>&nbsp;delete</b-button>
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
                <dd>{{this.sut.createdAt | formatDate }}</dd>
              </dl>
            </div>
          </div>
        </b-card-text>
      </b-tab>
      <!-- <b-tab title="Running">
        <b-card-text>
          <div class="text-center text-primary my-2">
            <b-spinner type="border" class="align-middle" small></b-spinner>
            <span style="margin-left:10px;">Loading...</span>
          </div>
        </b-card-text>
      </b-tab>-->
      <b-tab disabled title="REST model description">
        <b-card-text>
        </b-card-text>
      </b-tab>
    </b-tabs>
    <suts-delete></suts-delete>
  </b-card>

</template>

<script>
  import Store from "../../store/index";
  import Constants from '../../shared/constants';
  import RestService from "../../shared/services/rest-service";
  import MessageService from "../../shared/services/message-service";

  import SutsDelete from "./suts-delete";

  export default {
    components: { SutsDelete },
    data() {
      return {
        restService: new RestService(),
        messageService: new MessageService(this)
      }
    },
    computed: {
      sut() {
        return Store.getters.sut;
      }
    },
    methods: {
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
    }
  }
</script>
