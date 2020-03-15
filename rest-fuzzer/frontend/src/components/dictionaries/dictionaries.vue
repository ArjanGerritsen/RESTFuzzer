<template>
  <div>
    <div class="row">
      <div class="col-8">
        <dictionaries-list></dictionaries-list>
      </div>
      <div class="col-4">
        <dictionaries-add></dictionaries-add>
        <dictionaries-detail></dictionaries-detail>
      </div>
    </div>
  </div>
</template>

<script> 
  import DictionariesList from "./dictionaries-list";
  import DictionariesAdd from "./dictionaries-add";
  import DictionariesDetail from "./dictionaries-detail";

  export default {
    components: {
    	DictionariesList, DictionariesAdd, DictionariesDetail
    },
    data() {
      return { data: {} };
    },
  };
</script>
