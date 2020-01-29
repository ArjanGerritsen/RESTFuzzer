<template>
  <div>
	<b-card header-tag="header">
	  <span slot="header">
	    <b-icon icon="hammer" font-scale="1"></b-icon>
	    &nbsp;Actions
	  </span>
	  <b-button type="submit" variant="primary" v-b-modal.suts-add>
  	  <b-icon icon="plus" font-scale="1"></b-icon>
	    &nbsp;add system under test
	  </b-button>
	</b-card>
	<suts-add></suts-add>
	<suts-detail></suts-detail>
  </div>
</template>

<script>
  import SutsAdd from "./suts-add";
  import SutsDetail from "./suts-detail";

  export default {
    components: {
      SutsAdd, SutsDetail
    },
    data() {
      return { data: {} };
    },
  };
</script>
