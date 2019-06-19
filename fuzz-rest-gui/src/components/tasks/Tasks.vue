<template>
  <div>
    <div class="path">
      <h5>Tasks</h5>
    </div>

    <b-button type="submit" variant="primary">Add task</b-button>

    <div class="row">
      <div class="col">
        <task-list></task-list>
      </div>
    </div>
    <div class="row">
      <div class="col">
        <!-- <project></project> -->
      </div>
    </div>
  </div>
</template>

<script>
   import TaskList from './TaskList'
//   import Project from './Detail'

  export default {
    components: {
      TaskList
    },
    data() {
      return { }
    },
    methods: { },
    computed: { },
    created: function () { }
  }
</script>