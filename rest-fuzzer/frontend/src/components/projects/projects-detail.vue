<template>
  <b-modal scrollable id="projects-detail" ref="modal" size="lg">
    <template slot="modal-header">
      <h6>
        <b-icon icon="eye" font-scale="1"></b-icon>&nbsp;Fuzzing project
      </h6>
    </template>

    <div v-if="this.project === null" class="text-center text-primary my-2">
      <b-spinner type="border" class="align-middle" small></b-spinner>
      <span style="margin-left:10px;">Loading...</span>
    </div>

    <b-tabs v-if="this.project !== null" nav-tabs card>
      <b-tab title="Information" active>
        <div class="row">
          <div class="col">
            <div class="button-group-left">
              <b-button
                :disabled="tasksQueuedOrRunning"
                size="sm"
                type="submit"
                variant="primary"
                title="start task to fuzz SUT"
                v-on:click="addFuzzerTask"
              >
                <b-icon icon="play" font-scale="1"></b-icon>&nbsp;start fuzzing
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
                <b-link
                  :href="this.project.sut.location"
                  target="_blank"
                >{{this.project.sut.location}}</b-link>
              </dd>
              <dt>Meta data</dt>
              <dd>
                <div class="json" :inner-html.prop="this.project.metaDataTuplesJson | json"></div>
              </dd>
            </dl>
          </div>
          <div class="col"></div>
        </div>
      </b-tab>
      <b-tab :disabled="!requestsPresent" :title="requestsTitle">
        <ProjectsDetailRequests
          :project="this.project"
          :fields="requestFields"
          :formatters="requestFormatters"
        ></ProjectsDetailRequests>
      </b-tab>
      <b-tab :disabled="!responsesPresent" :title="responsesTitle">
        <ProjectsDetailResponses
          :project="this.project"
          :fields="responseFields"
          :formatters="responseFormatters"
        ></ProjectsDetailResponses>
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
import ProjectsDetailRequests from "./projects-detail-requests";
import ProjectsDetailResponses from "./projects-detail-responses";

export default {
  components: {
    ProjectsDelete,
    ProjectsDetailRequests,
    ProjectsDetailResponses
  },
  data() {
    return {
      requestFormatters: [],
      requestFields: [
        { key: "id", label: "#", thStyle: "width: 50px;" },
        { key: "path" },
        { key: "httpMethod", label: "Http method", thStyle: "width: 110px;" },
        { key: "details", label: "Details", thStyle: "width: 60px;" }
      ],
      responseFormatters: [],
      responseFields: [
        { key: "id", label: "#", thStyle: "width: 50px;" },
        { key: "request.path", label: "Path" },
        {
          key: "request.httpMethod",
          label: "HTTP method",
          thStyle: "width: 110px;"
        },
        { key: "statusCode", label: "HTTP status", thStyle: "width: 110px;" },
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
        this.$store.dispatch("findProject", { project_id: this.project.id });
        this.$root.$emit("bv::refresh::table", "project-requests");
        this.$root.$emit("bv::refresh::table", "project-responses");
        return;
      }

      this.timeoutRefresh = setTimeout(this.refreshData, 1000);
      this.$store
        .dispatch("countProjectRunningOrQueuedTasks", {
          project_id: this.project.id
        })
        .catch(error => {
          this.startedRefresh = null;
          clearTimeout(this.timeoutRefresh);
        });
    },
    addFuzzerTask() {
      this.$store
        .dispatch("addTask", {
          name: Constants.TASK_FUZZER,
          metaDataTuples: { project_id: this.project.id }
        })
        .then(() => {
          this.$store
            .dispatch("countProjectRunningOrQueuedTasks", {
              project_id: this.project.id
            })
            .then(() => {
              this.startedRefresh = new Date();
              this.refreshData();
            });
        });
    }
  },
  computed: {
    project() {
      return this.$store.getters.projects.current;
    },
    tasksQueuedOrRunning() {
      return (
        this.$store.getters.projects.current_queued_or_running_tasks_count !==
          null &&
        this.$store.getters.projects.current_queued_or_running_tasks_count > 0
      );
    },
    requestsPresent() {
      return this.requestsCount > 0;
    },
    requestsTitle() {
      let title = "Requests";
      if (this.requestsCount > 0) {
        title += ` [${this.requestsCount}]`;
      }
      return title;
    },
    requestsCount() {
      const count = this.$store.getters.projects.current_requests.total;
      return count !== null && count > 0 ? count : 0;
    },
    responsesPresent() {
      return this.responsesCount > 0;
    },
    responsesTitle() {
      let title = "Responses";
      if (this.responsesCount > 0) {
        title += ` [${this.responsesCount}]`;
      }
      return title;
    },
    responsesCount() {
      const count = this.$store.getters.projects.current_responses.total;
      return count !== null && count > 0 ? count : 0;
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
