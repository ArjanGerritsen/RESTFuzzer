<template>
  <div>
    <div class="path">
      <h5>Administrative tasks</h5>
    </div>

    <div class="row">
      <div class="col-8">
        <b-card header-tag="header">
          <span slot="header"><font-awesome-icon icon="list" size="1x" />&nbsp;Overview</span>
          <b-card-text>
            <task-list></task-list>
          </b-card-text>
        </b-card>
      </div>
      <div class="col-4">
        <b-card header-tag="header">
          <span slot="header"><font-awesome-icon icon="hammer" size="1x" />&nbsp;Actions</span>
          <b-button type="submit" variant="primary"><font-awesome-icon icon="plus" size="xs" />&nbsp;add task</b-button>
          <b-button type="submit" variant="primary"><font-awesome-icon icon="cloud-download-alt" size="xs" />&nbsp;backup</b-button>
          <b-button type="submit" variant="danger"><font-awesome-icon icon="cloud-upload-alt" size="xs" />&nbsp;restore</b-button>
        </b-card>
        <!--<project-add></project-add> -->
        <task-detail></task-detail>
      </div>
    </div>
  </div>
</template>

<script>
  //   import ProjectAdd from './Add'
  import TaskList from './task-detail'
  import TaskDetail from './task-detail'
  import RestService from '../../shared/service/rest-service'

  export default {
    components: {
    //   ProjectAdd,
      TaskList,
      TaskDetail,
    },
    data() {
      return {
        restService: new RestService(this.$bvToast)
      }
    },
    methods: { },
    computed: { },
    created: function() {
      this.restService.getAdminTasks()
    },
  }
</script>

<style>
  .card { margin-bottom: 20px; }
  .btn { margin-right: 10px; }
</style>