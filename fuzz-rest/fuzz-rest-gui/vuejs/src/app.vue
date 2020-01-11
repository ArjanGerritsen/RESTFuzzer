<template>
  <div>
    <Menu></Menu>
    <div id="container">
      <router-view />
    </div>
  </div>
</template>

<script>
import Menu from './components/other/menu'

export default {
  data() {
    return { data: {} }
  },
  components: {
    Menu,
  }  
}
</script>

<style scoped>
 #container {
    margin: auto;
    width: 98%;
  }
</style>

<style>
  body {
    background-color: #dddddd !important;
  }
  .path {
    margin: 90px 0px 20px 0px;
  }
</style>