<template>
<div>
  <b-card header-tag="header">
    <span slot="header">
     <b-icon icon="list-task" font-scale="1"></b-icon>&nbsp;Tasks progress
    </span>
    <b-card-text>
      Queued:
      <list :fields="fields" :items="queuedTasks" :formatters="formatters"></list>

      Running:
      <list :fields="fields" :items="runningTasks" :formatters="formatters"></list>

      Completed:
      <list :fields="fields" :items="completedTasks" :formatters="formatters"></list>
    </b-card-text>
  </b-card>
</div>
</template>

<script>
  import List from "../shared/list/list";
  
  import Store from "../../store/index";
  import RestService from "../../shared/services/rest-service";
  import MessageService from "../../shared/services/message-service";

  export default {
    components: { List },
    data() {
      return {
        formatters: [
          { field: 'createdAt', as: 'date' },
        ],
        fields: [
          { key: 'id', label: '#', thStyle: 'width: 30px;' },
          { key: 'name', thStyle: 'width: 200px;' },
          { key: 'startedAt', label: 'Started @', thStyle: 'width: 150px;' },
          { key: 'creashedAt', label: 'Crashed @', thStyle: 'width: 150px;' },
          { key: 'finishedAt', label: 'Finished @', thStyle: 'width: 150px;' }
        ],
        restService: new RestService(),
        messageService: new MessageService(this)
      }
    },
    methods: { 
      getQueuedTasks: function() {
        this.restService.getQueuedTasks()
          .then(response => {
            Store.commit('tasks_queued_set', { tasks: response.data });
          })
          .catch(error => {
            this.messageService.error("Couldn't retrieve queued tasks", error);
            Store.commit('tasks_queued_set', { tasks: [] } );
          }
        );
      },
      getRunningTasks: function() {
        this.restService.getRunningTasks()
          .then(response => {
            Store.commit('tasks_running_set', { tasks: response.data });
          })
          .catch(error => {
            this.messageService.error("Couldn't retrieve running tasks", error);
            Store.commit('tasks_running_set', { tasks: [] } );
          }
        );
      },
      getCompletedTasks: function() {
        this.restService.getCompletedTasks()
          .then(response => {
            Store.commit('tasks_completed_set', { tasks: response.data });
          })
          .catch(error => {
            this.messageService.error("Couldn't retrieve completed tasks", error);
            Store.commit('tasks_completed_set', { tasks: [] } );
          }
        );
      },      
    },
    computed: { 
      queuedTasks() { return Store.getters.tasks.queued },
      runningTasks() { return Store.getters.tasks.running },
      completedTasks() { return Store.getters.tasks.completed },
    },
    mounted: function () {
      setInterval(() => {
        this.getQueuedTasks();
        this.getRunningTasks();
        this.getCompletedTasks();
      }, 100)
    },
    created: function () { },
  }
</script>
