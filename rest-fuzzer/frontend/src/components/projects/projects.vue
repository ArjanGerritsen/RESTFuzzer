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

    <projects-detail></projects-detail>
    <projects-add></projects-add>
  </div>
</template>

<script>
  import TasksProgress from "../tasks/tasks-progress";
  
  import ProjectsAdd from "./projects-add";
  import ProjectsList from "./projects-list";
  import ProjectsDetail from "./projects-detail";

  export default {
    components: {
      TasksProgress, ProjectsAdd, ProjectsList, ProjectsDetail
    },
    data() {
      return { data: {} };
    },
  };
</script>
