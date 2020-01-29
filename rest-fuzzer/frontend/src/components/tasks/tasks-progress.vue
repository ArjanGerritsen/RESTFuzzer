<template>
<div>
  <b-card header-tag="header">
    <span slot="header">
      <b-icon icon="list-task" font-scale="1"></b-icon>
      &nbsp;Tasks progress
    </span>
    <b-card-text>
      <list :fields="fields" :items="tasksProgress" :formatters="formatters"></list>
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
          { field: 'startedAt', as: 'dateShort' },
          { field: 'finishedAt', as: 'dateShort' }
        ],
        fields: [
          { key: 'id', label: '#', thStyle: 'width: 30px;' },
          { key: 'name' },
          { key: 'status', thStyle: 'width: 80px;' },
          { key: 'startedAt', label: 'Started @', thStyle: 'width: 110px;' },
          { key: 'finishedAt', label: 'Ended @', thStyle: 'width: 110px;' },
        ],
        restService: new RestService(),
        messageService: new MessageService(this)
      }
    },
    methods: { 
      getTasksProgress: function() {
        this.restService.getTasksProgress()
          .then(response => {
            Store.commit('tasks_progress_set', { tasks: response.data });
          })
          .catch(error => {
            this.messageService.error("Couldn't retrieve tasks (progress)", error);
            Store.commit('tasks_progress_set', { tasks: [] } );
          }
        );
      }    
    },
    computed: { 
      tasksProgress() { return Store.getters.tasks.progress },
    },
    mounted: function () {
      setInterval(() => {
        this.getTasksProgress();
      }, 2500)
    },
    created: function () { },
  }
</script>
