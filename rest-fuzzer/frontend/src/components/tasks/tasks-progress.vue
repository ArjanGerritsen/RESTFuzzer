<template>
<div>
  <b-card header-tag="header">
    <span slot="header">
      <b-icon icon="list-task" font-scale="1"></b-icon>
      Tasks progress
    </span>

    <b-card-text>
      <template>
        <div>
          <b-table id="progress-tasks"
            class="table-sm"
            show-empty
            :busy="this.tasksProgress === null"
            striped 
            :items="tasksProgress"
            :fields="fields"
            :borderless="true">

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
              <template>
                {{ data.value | dateShort }}
              </template>
            </template>

            <template v-slot:cell(endedAt)="data">
              <template>
                {{ data.value | dateShort }}
              </template>
            </template>

            <template slot="empty">
              <h6>No data present.</h6>
            </template>      
          </b-table>
        </div>
      </template>

    </b-card-text>
  </b-card>
</div>
</template>

<script> 
  import Store from "../../store/index";
  import Constants from "../../shared/constants";
  import RestService from "../../shared/services/rest-service";
  import MessageService from "../../shared/services/message-service";

  export default {
    components: { Constants },
    data() {
      return {
        fields: [
          { key: 'id', label: '#', thStyle: 'width: 50px;' },
          { key: 'name' },
          { key: 'status', thStyle: 'text-align:center; width: 70px;' },
          { key: 'startedAt', label: 'Started @', thStyle: 'width: 100px;' },
          { key: 'endedAt', label: 'Ended @', thStyle: 'width: 100px;' },
        ],
        constants: Constants,
        restService: new RestService(),
        messageService: new MessageService(this),
        timeoutTasks: null
      }
    },
    methods: { 
      getTasksProgress: function() {
        this.restService.getTasksProgress()
          .then(response => {
            Store.commit('tasks_progress_set', { tasks: response.data });
            this.timeoutTasks = setTimeout(this.getTasksProgress, 2500);
          })
          .catch(error => {
            this.messageService.error("Couldn't retrieve tasks (progress)", error);
            Store.commit('tasks_progress_set', { tasks: [] } );
            clearTimeout(this.timeoutTasks);
          }
        );
      }    
    },
    computed: { 
      tasksProgress() { return Store.getters.tasks.progress },
    },
    destroyed: function() {
      clearTimeout(this.timeoutTasks);
    },
    created: function () { 
      this.getTasksProgress();
    },
  }
</script>
