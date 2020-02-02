<template>
  <div>
    <div class="row">
      <div class="col-4">
        <tasks-progress></tasks-progress>
      </div>
      <div class="col-8">
        TODO
      </div>
    </div>
  </div>
</template>

<script>
  import TasksProgress from "../tasks/tasks-progress";

  export default {
    components: {
      TasksProgress
    },
    data() {
      return { data: {} };
    },
  };
</script>
