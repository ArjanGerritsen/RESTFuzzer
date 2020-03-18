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
        <div class="row">
          <div class="col">
            <div class="button-group-left">
              <b-button
                :disabled="tasksQueuedOrRunning"
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
      </b-tab>
      <b-tab :disabled="this.actionsCount === 0" :title="actionsTitle">
        <SutsDetailActions :sut="sut" :fields="fields" :formatters="formatters"></SutsDetailActions>
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
    refreshData() {
      if (!this.tasksQueuedOrRunning) {
        this.startedRefresh = null;
        clearTimeout(this.refreshTimeout);
        this.$store.dispatch("findSut", this.sut.id);
        this.$store.dispatch("findAllSuts");
        this.$root.$emit("bv::refresh::table", "sut-actions");
        return;
      }

      this.timeoutRefresh = setTimeout(this.refreshData, 1000);
      this.$store
        .dispatch("countSutRunningOrQueuedTasks", this.sut.id)
        .catch(error => {
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
          this.$store
            .dispatch("countSutRunningOrQueuedTasks", this.sut.id)
            .then(() => {
              this.startedRefresh = new Date();
              this.refreshData();
            });
        });
    }
  },
  computed: {
    sut() {
      return this.$store.getters.suts.current;
    },
    tasksQueuedOrRunning() {
      return (
        this.$store.getters.suts.current_queued_or_running_tasks_count !==
          null &&
        this.$store.getters.suts.current_queued_or_running_tasks_count > 0
      );
    },
    actionsTitle() {
      let title = "REST model description";
      if (this.actionsCount > 0) {
        title += ` [${this.actionsCount}]`;
      }
      return title;
    },
    actionsCount() {
      const count = this.$store.getters.suts.current_actions.total;
      return count !== null && count > 0 ? count : 0;
    }
  },
  created: function() {},
  destroyed: function() {
    clearTimeout(this.timeoutRefresh);
  }
};
</script>
