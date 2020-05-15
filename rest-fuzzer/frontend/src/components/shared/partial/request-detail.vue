<template>
  <div>
    <div v-if="request.formdataParametersJson !== '{}'" class="param-block">
      <h6>Form data parameters:</h6>
      <div class="json" :inner-html.prop="request.formdataParametersJson | json"></div>
    </div>

    <div v-if="request.headerParametersJson !== '{}'" class="param-block">
      <h6>Header parameters:</h6>
      <div class="json" :inner-html.prop="request.headerParametersJson | json"></div>
    </div>

    <div v-if="request.pathParametersJson !== '{}'" class="param-block">
      <h6>Path parameters:</h6>
      <div class="json" :inner-html.prop="request.pathParametersJson | json"></div>
    </div>

    <div v-if="request.queryParametersJson !== '{}'" class="param-block">
      <h6>Query parameters:</h6>
      <div class="json" :inner-html.prop="request.queryParametersJson | json"></div>
    </div>

    <div class="clearfix"></div>
  </div>
</template>

<script>
export default {
  props: ["request"]
};
</script>

<style scoped>
.param-block {
  float: left;
  vertical-align: top;
  width: 250px;
  margin-right: 50px;
}
</style>