<template>
  <div>
    <div class="path">
      <h5>Administrative tasks</h5>
    </div>

    <div class="row">
      <div class="col-8">
        <task-list></task-list>
      </div>
      <div class="col-4">
        <b-card header="Actions">
          <b-button type="submit" variant="primary"><font-awesome-icon icon="plus" size="xs" />&nbsp;add task</b-button>
          <b-button type="submit" variant="primary"><font-awesome-icon icon="cloud-download-alt" size="xs" />&nbsp;backup</b-button>
          <b-button type="submit" variant="danger"><font-awesome-icon icon="cloud-upload-alt" size="xs" />&nbsp;restore</b-button>
        </b-card>
        <!-- <project-add></project-add>
        <project-detail></project-detail> -->
      </div>
    </div>
  </div>
</template>

<script>
//   import ProjectAdd from './Add'
  import TaskList from './TaskList'
//   import ProjectDetail from './Detail'
  import RestService from '../../shared/RestService'

  export default {
    components: {
    //   ProjectAdd,
      TaskList,
    //   ProjectDetail
    },
    data() {
      return {
        restService: new RestService(this.$bvToast)
      }
    },
    methods: { },
    computed: { },
    created: function() {
      this.restService.getAdministrativeTasks()
    },
  }
</script>

<style>
  .card { margin-bottom: 20px; }
  .btn { margin-right: 10px; }
</style>