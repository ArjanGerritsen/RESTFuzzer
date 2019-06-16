<template>
  <div>
    <div class="path">
      <h5>Projects</h5>
    </div>

    <b-button type="submit" variant="primary">add project</b-button>

    <div class="row">
      <div class="col-sm">
        <project-list></project-list>
      </div>
      <div class="col-sm">
        <project></project>
      </div>
    </div>
  </div>
</template>

<script>
  import ProjectList from './List'
  import Project from './Detail'

  export default {
    components: {
      ProjectList, Project
    },
    data() {
      return {
        form: {
          email: '',
          name: '',
          checked: []
        }
      }
    },
    methods: { },
    computed: { },
    created: function () { }
  }
</script>

<style scoped>
  .btn {
    margin-right: 10px;
  }
</style>
