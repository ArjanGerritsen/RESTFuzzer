<template>
  <div>
    <Menu></Menu>
    <div id="container">
      <router-view />
    </div>
    <Message></Message>
  </div>
</template>

<script>
import Menu from "./components/menu/menu";
import Message from "./components/shared/message";

export default {
  components: {
    Menu,
    Message
  },
  data() {
    return { data: {} };
  },
  created: function() {}
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
.button-group-left button,
.button-group-left a {
  margin-right: 10px;
}

.button-group-right {
  float: right;
  margin-bottom: 15px;
}
.button-group-right button,
.button-group-right a {
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

body {
  background-image: url("assets/background.jpg");
}

@import "../node_modules/bootstrap/scss/bootstrap";
@import "../node_modules/bootstrap-vue/src/index.scss";
</style>