<template>
  <b-card header-tag="header">
    <template v-slot:header>
      <b-icon icon="list-task" font-scale="1"></b-icon>&nbsp;Tasks (queued and running)
    </template>

    <b-card-text>
      <b-table
        id="active-tasks"
        class="table-sm"
        hover
        striped
        show-empty
        @row-clicked="select"
        :busy="tasksActive === null"
        :items="tasksActive"
        :fields="fields"
        :borderless="true"
      >
        <div slot="table-busy" class="text-center text-primary my-2">
          <b-spinner type="border" class="align-middle" small></b-spinner>
          <span style="margin-left:10px;">Loading...</span>
        </div>

        <template v-slot:cell(progress)="data">
          <template>
            <b-progress
              :value="data.value"
              :max="100"
              height="1.5em"
              style="margin-top:2px; border:1px solid #ffffff;"
            ></b-progress>
          </template>
        </template>

        <template v-slot:cell(status)="data">
          <div style="text-align:center;">
            <template v-if="data.value === constants.TASK_STATUS_QUEUED">
              <b-icon icon="clock" variant="success" font-scale="1"></b-icon>
            </template>
            <template v-if="data.value === constants.TASK_STATUS_RUNNING">
              <b-spinner small variant="primary" type="grow"></b-spinner>
            </template>
            <template v-if="data.value === constants.TASK_STATUS_CRASHED">
              <b-icon icon="alert-circle-fill" variant="danger" font-scale="1"></b-icon>
            </template>
            <template v-if="data.value === constants.TASK_STATUS_FINISHED">
              <b-icon icon="check-circle" variant="success" font-scale="1"></b-icon>
            </template>
          </div>
        </template>

        <template v-slot:cell(startedAt)="data">
          <template>{{ data.value | dateShort }}</template>
        </template>

        <template v-slot:cell(endedAt)="data">
          <template>{{ data.value | dateShort }}</template>
        </template>

        <template slot="empty">No data present.</template>
      </b-table>
    </b-card-text>
  </b-card>
</template>

<script>
import Constants from "../../shared/constants";

export default {
  components: { Constants },
  data() {
    return {
      fields: [
        { key: "id", label: "#", thStyle: "width: 50px;" },
        { key: "name" },
        { key: "progress", thStyle: "width: 100px;" },
        { key: "status", thStyle: "text-align:center; width: 70px;" },
        { key: "startedAt", label: "Started @", thStyle: "width: 100px;" },
        { key: "endedAt", label: "Ended @", thStyle: "width: 100px;" }
      ],
      constants: Constants,
      timeoutTasks: null
    };
  },
  methods: {
    select(item) {
      this.$store.commit("set_task_item", { item: item });
    },
    getTasksActive: function() {
      this.timeoutTasks = setTimeout(this.getTasksActive, 1500);
      this.$store.dispatch("findTasksActive").catch(error => {
        clearTimeout(this.timeoutTasks);
      });
    }
  },
  computed: {
    tasksActive() {
      return this.$store.getters.tasks.active.list;
    }
  },
  destroyed: function() {
    clearTimeout(this.timeoutTasks);
  },
  created: function() {
    this.getTasksActive();
  }
};
</script>
