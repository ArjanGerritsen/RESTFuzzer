<template>
  <div>
    <Menu></Menu>
    <div id="container">
      <router-view />
    </div>
  </div>
</template>

<script>
import Menu from './components/Menu'

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
    width: 95%;
  }
</style>

<style>
  .path {
    margin: 30px 0px 20px 0px;
  }
</style>