<template>
  <div>
    <div class="row">
      <div class="col-8">
        <suts-list></suts-list>
      </div>
      <div class="col-4">
        <tasks-progress></tasks-progress>
      </div>
    </div>

    <suts-detail></suts-detail>
    <suts-add></suts-add>
  </div>
</template>

<script>
  import TasksProgress from "../tasks/tasks-progress";

  import SutsAdd from "./suts-add";
  import SutsList from "./suts-list";
  import SutsDetail from "./suts-detail";

  export default {
    components: {
      TasksProgress, SutsAdd, SutsList, SutsDetail
    },
    data() {
      return { data: {} };
    },
  };
</script>
