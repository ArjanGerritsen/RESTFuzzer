<template>
  <div>
    <div class="row">
      <div class="col-3">
        <tasks-progress></tasks-progress>
      </div>
      <div class="col-5">
        <suts-list></suts-list>
      </div>
      <div class="col-4">
        <suts-actions></suts-actions>
      </div>
    </div>
  </div>
</template>

<script>
  import TasksProgress from "../tasks/tasks-progress";

  import SutsList from "./suts-list";
  import SutsActions from "./suts-actions";

  export default {
    data() {
      return { data: {} };
    },
    components: {
      TasksProgress, SutsList, SutsActions
    }
  };
</script>
