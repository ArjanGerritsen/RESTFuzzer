<template>
  <b-modal scrollable id="projects-detail" ref="modal" size="lg">
    <template slot="modal-header">
      <h6>
        <b-icon icon="eye" font-scale="1"></b-icon>&nbsp;Fuzzing project
      </h6>
    </template>

    <b-tabs v-if="this.project !== null" nav-tabs card>
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
                  title="start task to genereate http requests"
                  v-on:click="addGeneratorTask"
                >
                  <b-icon icon="play" font-scale="1"></b-icon>&nbsp;start generate task
                </b-button>
                  <b-button
                  :disabled="extractTaskDisabled()"
                  size="sm"
                  type="submit"
                  variant="primary"
                  title="start task to execute http requests and capture responses"
                  v-on:click="addExecutorTask"
                >
                  <b-icon icon="play" font-scale="1"></b-icon>&nbsp;start execute task
                </b-button>                
                <b-button
                  size="sm"
                  type="submit"
                  v-b-modal.projects-delete
                  variant="outline-danger"
                  title="delete this fuzzing project"
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
                <dd>{{this.project.id}}</dd>
                <dt>Type</dt>
                <dd>{{this.project.type | enumToHuman }}</dd>
                <dt>System under test</dt>
                <dd>
                  <b-link :href="this.project.sut.location" target="_blank">{{this.project.sut.location}}</b-link>
                </dd>
              </dl>
            </div>
            <div class="col">
            </div>
          </div>
        </b-card-text>
      </b-tab>
      <b-tab :disabled="true" title="Http requests">
        <b-card-text>
          TODO
        </b-card-text>
      </b-tab>
      <b-tab :disabled="true" title="Http responses">
        <b-card-text>
          TODO
        </b-card-text>
      </b-tab>
    </b-tabs>

    <projects-delete></projects-delete>

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

import ProjectsDelete from "./projects-delete";
//import SutsDetailActions from "./suts-detail-actions.vue";

export default {
  components: { ProjectsDelete },
  data() {
    return {
      startedRefresh: null,
      timeoutRefresh: null
    };
  },
  methods: {
    selectAction(value) {
      console.log("value: " + value);
    },
    extractTaskDisabled() {
      return this.startedRefresh !== null;
    },
    refreshData() {
    },
    addGeneratorTask() {
      this.addTask(Constants.TASK_GENERATOR);
    },
    addExecutorTask() {
      this.addTask(Constants.TASK_EXECUTOR);
    },
    addTask(name) {
      this.$store
        .dispatch("addTask", {
          name: name,
          metaDataTuples: { project_id: this.project.id }
        })
        .then(() => {
          this.startedRefresh = new Date();
          this.refreshData();
      });
    },
  },
  computed: {
    project() {
      return this.$store.getters.projects.current;
    },
    canExecuteTask() {
      return true;
    }
  },
  created: function() {},
  destroyed: function() {
    clearTimeout(this.timeoutRefresh);
  }
};
</script>
