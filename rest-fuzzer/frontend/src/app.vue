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
  // $font-size-base: 0.875rem;
  $body-bg: #dddddd;

  @import '../node_modules/bootstrap/scss/bootstrap';
  @import '../node_modules/bootstrap-vue/src/index.scss';
</style>