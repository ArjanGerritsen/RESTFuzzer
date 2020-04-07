<template>
  <div>
    <div class="row">
      <div class="col-6">
        <tasks-active></tasks-active>
        <div style="height:12px;"></div>
        <tasks-archive></tasks-archive>
      </div>
      <div class="col-6">

      </div>
    </div>
  </div>
</template>

<script>
import TasksActive from "./tasks-active";
import TasksArchive from "./tasks-archive";

export default {
  components: { TasksActive, TasksArchive }
};
</script>