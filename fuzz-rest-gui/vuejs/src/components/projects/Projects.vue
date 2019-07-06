<template>
  <div>
    <div class="path">
      <h5>Projects</h5>
    </div>

    <div class="row">
      <div class="col-8">
        <b-card header-tag="header">
          <span slot="header"><font-awesome-icon icon="list" size="1x" />&nbsp;Overview</span>
          <b-card-text>
            <project-list></project-list>
          </b-card-text>
        </b-card>
      </div>
      <div class="col-4">
        <b-card header-tag="header">
          <span slot="header"><font-awesome-icon icon="hammer" size="1x" />&nbsp;Actions</span>
          <b-button type="submit" variant="primary" v-b-modal.projects-add><font-awesome-icon icon="plus" size="xs" />&nbsp;add project</b-button>
        </b-card>
        <project-add></project-add>
        <project-detail></project-detail>
      </div>
    </div>
  </div>
</template>

<script>
  import ProjectAdd from './ProjectsAdd'
  import ProjectList from './ProjectsList'
  import ProjectDetail from './ProjectsDetail'
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
