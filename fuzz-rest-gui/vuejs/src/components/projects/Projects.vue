<template>
  <div>
    <div class="path">
      <h5>Projects</h5>
    </div>

    <div class="row">
      <div class="col-8">
        <project-list></project-list>
      </div>
      <div class="col-4">
        <b-card header="Actions">
          <b-button type="submit" variant="primary"><font-awesome-icon icon="plus" size="xs" />&nbsp;add project</b-button>
        </b-card>
        <project-add></project-add>
        <project-detail></project-detail>
      </div>
    </div>
  </div>
</template>

<script>
  import ProjectAdd from './Add'
  import ProjectList from './List'
  import ProjectDetail from './Detail'
  import RestService from '../shared/RestService'

  export default {
    components: {
      ProjectAdd,
      ProjectList,
      ProjectDetail
    },
    data() {
      return {
        restService: new RestService(this.$bvToast)
      }
    },
    methods: { },
    computed: { },
    created: function() {
      this.restService.getProjects()
    },
  }
</script>

<style>
  .card { margin-bottom: 20px; }
  .btn { margin-right: 10px; }
</style>
