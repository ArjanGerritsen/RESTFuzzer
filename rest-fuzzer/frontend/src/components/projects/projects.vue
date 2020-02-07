<template>
  <div>
    <div class="row">
      <div class="col-4">
        <tasks-progress></tasks-progress>
      </div>
      <div class="col-8">
        <projects-list></projects-list>
      </div>
    </div>
  </div>
</template>

<script>
  import TasksProgress from "../tasks/tasks-progress";
  import ProjectsList from "./projects-list";

  export default {
    components: {
      TasksProgress, ProjectsList
    },
    data() {
      return { data: {} };
    },
  };
</script>
