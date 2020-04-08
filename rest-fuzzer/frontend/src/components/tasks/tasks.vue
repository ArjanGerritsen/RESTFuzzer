<template>
  <div>
    <div class="row">
      <div class="col-6">
        <tasks-active></tasks-active>
        <div style="height:13px;"></div>
        <tasks-archive></tasks-archive>
      </div>
      <div class="col-6">
        <tasks-detail></tasks-detail>
      </div>
    </div>
  </div>
</template>

<script>
import TasksActive from "./tasks-active";
import TasksArchive from "./tasks-archive";
import TasksDetail from "./tasks-detail";

export default {
  components: { TasksActive, TasksArchive, TasksDetail }
};
</script>