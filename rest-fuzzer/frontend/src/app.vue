<template>
  <div>
    <Menu></Menu>
    <div id="container">
      <router-view />
    </div>
  </div>
</template>

<script>
import Menu from "./components/menu/menu";

export default {
  data() {
    return { data: {} };
  },
  components: {
    Menu
  }
};
</script>

<style scoped>
  #container {
    margin: auto;
    width: 98%;
  }
</style>

<style lang="scss">
  .button-group-left {
    float: left;
    margin-bottom: 15px;
  }
  .button-group-left button, .button-group-left a {
    margin-right: 10px;
  }
  
  .button-group-right {
    float: right;
    margin-bottom: 15px;
  }
  .button-group-right button, .button-group-right a {
	margin-left: 10px;
  }

  .card-with-top-margin {
    margin-top: 35px;
  }
 
  th {
	font-size: 0.875rem;
  }
  
  td {
	font-size: 0.875rem;	  
  }

  $font-size-base: 0.875rem;
  $body-bg: #dddddd;

  @import '../node_modules/bootstrap/scss/bootstrap';
  @import '../node_modules/bootstrap-vue/src/index.scss';
</style>