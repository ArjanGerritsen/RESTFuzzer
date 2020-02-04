<template>
  <div>
    <div class="row">
      <div class="col-4">
        <tasks-progress></tasks-progress>
      </div>
      <div class="col-8">
        <fuzzing-list></fuzzing-list>
      </div>
    </div>
  </div>
</template>

<script>
  import TasksProgress from "../tasks/tasks-progress";
  import FuzzingList from "./fuzzing-list";

  export default {
    components: {
      TasksProgress, FuzzingList
    },
    data() {
      return { data: {} };
    },
  };
</script>
