<template>
  <b-card v-if="task !== null" header-tag="header">
    <template v-slot:header>
      <b-icon icon="eye" font-scale="1"></b-icon>&nbsp;Detail task
    </template>
    <b-card-text>
      <b-tabs nav-tabs card>
        <b-tab title="Information" active>
          <div class="row">
            <div class="col">
              <div class="button-group-left">
                <b-button
                  size="sm"
                  v-b-modal.tasks-delete
                  variant="outline-danger"
                  title="delete this task"
                >
                  <b-icon icon="trash" font-scale="1"></b-icon>&nbsp;delete
                </b-button>

                <b-button
                  size="sm"
                  variant="primary"
                  title="go to project"
                  :to="{ name: 'project', params: { id: 1 }}"
                >
                  <b-icon icon="link" font-scale="1"></b-icon>&nbsp;
                  go to project
                </b-button>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col">
              <dl class="dl-horizontal">
                <dt>Identifier</dt>
                <dd>{{task.id}}</dd>
                <dt>Name</dt>
                <dd>{{task.name}}</dd>
                <dt>Canonical name</dt>
                <dd>{{task.canonicalName}}</dd>
                <dt>Progress</dt>
                <dd>
                  <b-progress
                    :value="task.progress"
                    :max="100"
                    height="1.5em"
                    style="margin-top:2px; border:1px solid #ffffff;"
                  ></b-progress>
                </dd>
                <dt>Status</dt>
                <dd>
                  <b-icon
                    v-if="task.status === constants.TASK_STATUS_QUEUED"
                    icon="clock"
                    variant="success"
                    font-scale="1"
                  ></b-icon>
                  <b-spinner
                    v-if="task.status === constants.TASK_STATUS_RUNNING"
                    small
                    variant="primary"
                    type="grow"
                  ></b-spinner>
                  <b-icon
                    v-if="task.status === constants.TASK_STATUS_CRASHED"
                    icon="alert-circle-fill"
                    variant="danger"
                    font-scale="1"
                  ></b-icon>
                  <b-icon
                    v-if="task.status === constants.TASK_STATUS_FINISHED"
                    icon="check-circle"
                    variant="success"
                    font-scale="1"
                  ></b-icon>
                  ({{task.status | downCase }})
                </dd>
                <dt>Started @</dt>
                <dd>{{task.startedAt | dateShort}}</dd>
                <dt>Ended @</dt>
                <dd>{{task.endedAt | dateShort}}</dd>
              </dl>
            </div>
            <div class="col">
              <dl class="dl-horizontal">
                <dt>Meta data:</dt>
                <dd>
                  <div class="json" :inner-html.prop="task.metaDataTuplesJson | json"></div>
                </dd>
              </dl>
            </div>
          </div>
        </b-tab>
      </b-tabs>
    </b-card-text>
  </b-card>
</template>

<script>
import Constants from "../../shared/constants";

export default {
  components: { Constants },
  data() {
    return {
      constants: Constants
    };
  },
  computed: {
    display() {
      return this.task !== null;
    },
    task() {
      return this.$store.getters.tasks.current.item;
    }
  },
  methods: {
    refreshTask() {
      if (this.display) {
        this.$store
          .dispatch("findTask", this.$store.getters.tasks.current.item.id)
          .catch(error => {
            this.$timer.stop("refreshTask");
          });
      }
    }
  },
  timers: {
    refreshTask: {
      time: Constants.REFRESH_TIMEOUT,
      autostart: true,
      repeat: true
    }
  }
};
</script>