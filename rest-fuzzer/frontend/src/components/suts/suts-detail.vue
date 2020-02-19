<template>
  <b-modal scrollable id="suts-detail" ref="modal" size="lg">
    <template slot="modal-header">
      <h6>
        <b-icon icon="eye" font-scale="1"></b-icon>&nbsp;System under test
      </h6>
    </template>

    <div v-if="this.sut === null" class="text-center text-primary my-2">
      <b-spinner type="border" class="align-middle" small></b-spinner>
      <span style="margin-left:10px;">Loading...</span>
    </div>

    <b-tabs v-if="this.sut !== null" nav-tabs card>
      <b-tab title="Information" active>
        <b-card-text>
          <div class="row">
            <div class="col">
              <div class="button-group-left">
                <b-button
                  :disabled="extractTaskDisabled()"
                  size="sm"
                  type="submit"
                  variant="primary"
                  title="start task to extract REST model description from OAS"
                  v-on:click="addExtractorTask"
                >
                  <b-icon icon="play" font-scale="1"></b-icon>&nbsp;start extract task
                </b-button>
                <b-button
                  size="sm"
                  type="submit"
                  v-b-modal.suts-delete
                  variant="outline-danger"
                  title="delete this SUT"
                >
                  <b-icon icon="trash" font-scale="1"></b-icon>&nbsp;delete
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
                <dd>
                  <b-link :href="this.sut.location" target="_blank">{{this.sut.location}}</b-link>
                </dd>
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
          <SutsDetailActions
            @select-item="selectAction"
            :fields="fields"
            :items="sut.actions"
            :formatters="formatters"
            :displayFilter="true"
          ></SutsDetailActions>
        </b-card-text>
      </b-tab>
    </b-tabs>

    <suts-delete></suts-delete>

    <template slot="modal-footer" slot-scope="{ cancel }">
      <div class="button-group-right">
        <b-button size="sm" type="cancel" variant="outline-secondary" @click="cancel()">
          <b-icon icon="backspace" font-scale="1"></b-icon>&nbsp;close
        </b-button>
      </div>
    </template>
  </b-modal>
</template>

<script>
import Constants from "../../shared/constants";

import SutsDelete from "./suts-delete";
import SutsDetailActions from "./suts-detail-actions.vue";

export default {
  components: { SutsDetailActions, SutsDelete },
  data() {
    return {
      formatters: [],
      fields: [
        { key: "id", label: "#", thStyle: "width: 50px;" },
        { key: "path" },
        { key: "httpMethod", label: "Http method", thStyle: "width: 110px;" },
        { key: "details", label: "Details", thStyle: "width: 60px;" }
      ],
      startedRefresh: null,
      timeoutRefresh: null
    };
  },
  methods: {
    selectAction(value) {
      console.log("value: " + value);
    },
    extractTaskDisabled() {
      return this.sut.actions.length !== 0 || this.startedRefresh !== null;
    },
    refreshData() {
      if (
        this.sut.actions.length !== 0 ||
        new Date() - this.startedRefresh > 20000
      ) {
        this.startedRefresh = null;
        clearTimeout(this.refreshTimeout);
        return;
      }

      this.timeoutRefresh = setTimeout(this.refreshData, 1000);
      this.$store.dispatch("findSut", this.sut.id).catch(error => {
        this.startedRefresh = null;
        clearTimeout(this.timeoutRefresh);
      });
    },
    addExtractorTask() {
      this.$store
        .dispatch("addTask", {
          name: Constants.TASK_EXTRACTOR,
          metaDataTuples: { sut_id: this.sut.id }
        })
        .then(() => {
          this.startedRefresh = new Date();
          this.refreshData();
        });
    }
  },
  computed: {
    sut() {
      return this.$store.getters.suts.current;
    },
    canExecuteTask() {
      return this.sut.actions.length !== 0;
    }
  },
  created: function() {},
  destroyed: function() {
    clearTimeout(this.timeoutRefresh);
  }
};
</script>
