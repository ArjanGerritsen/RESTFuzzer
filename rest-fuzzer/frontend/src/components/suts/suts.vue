<template>
  <div>
    <div class="row">
      <div class="col-3">
        <tasks-progress></tasks-progress>
      </div>
      <div class="col-5">
        <suts-list></suts-list>
        <suts-actions></suts-actions>
      </div>
      <div class="col-4">
        <suts-detail></suts-detail>
      </div>
    </div>

    <suts-add></suts-add>
  </div>
</template>

<script>
  import TasksProgress from "../tasks/tasks-progress";

  import SutsAdd from "./suts-add";
  import SutsList from "./suts-list";
  import SutsDetail from "./suts-detail";
  import SutsActions from "./suts-actions";

  export default {
    components: {
      TasksProgress, SutsAdd, SutsList, SutsDetail, SutsActions
    },
    data() {
      return { data: {} };
    },
  };
</script>
