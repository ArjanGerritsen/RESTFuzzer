<template>
  <b-card header-tag="header">
    <template v-slot:header>
      <b-icon icon="list-task" font-scale="1"></b-icon>&nbsp;Tasks archive
    </template>

    <b-card-text>
      <b-table
        id="progress-tasks"
        class="table-sm"
        show-empty
        :busy="tasksProgress === null"
        striped
        :items="tasksProgress"
        :fields="fields"
        :borderless="true"
      >
        <div slot="table-busy" class="text-center text-primary my-2">
          <b-spinner type="border" class="align-middle" small></b-spinner>
          <span style="margin-left:10px;">Loading...</span>
        </div>

        <template v-slot:cell(status)="data">
          <div style="text-align:center;">
            <template v-if="data.value === constants.TASK_STATUS_QUEUED">
              <b-icon icon="clock" variant="success" font-scale="1"></b-icon>
            </template>
            <template v-if="data.value === constants.TASK_STATUS_RUNNING">
              <b-spinner small variant="success" type="grow"></b-spinner>
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