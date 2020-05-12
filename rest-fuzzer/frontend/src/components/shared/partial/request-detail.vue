<template>
  <div>
    <dl class="dl-horizontal">
      <dt>Created @:</dt>
      <dd>{{request.createdAt | date}}</dd>
    </dl>
    <div class="row">
      <div class="col" style="margin-bottom:20px">
        <h6>Form data parameters:</h6>
        <div class="json" :inner-html.prop="request.formdataParametersJson | json"></div>
      </div>
      <div class="col">
        <h6>Header parameters:</h6>
        <div class="json" :inner-html.prop="request.headerParametersJson | json"></div>
      </div>
    </div>
    <div class="row" style="margin-bottom:20px">
      <div class="col">
        <h6>Path parameters:</h6>
        <div class="json" :inner-html.prop="request.pathParametersJson | json"></div>
      </div>
      <div class="col">
        <h6>Query parameters:</h6>
        <div class="json" :inner-html.prop="request.queryParametersJson | json"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["request"]
};
</script>