<template>
  <div>
    <div class="row" style="margin-bottom:20px">
      <div class="col">
        <h6>Other:</h6>
        Created @: {{response.createdAt | date}}
        <br />
        HTTP status: {{response.statusCode}}
        <br />
        HTTP status description: {{response.statusDescription}}
        <br />
        Exception: {{response.failureReason === null ? '-' : response.failureReason}}
      </div>
    </div>
    <div class="row" style="margin-bottom:20px">
      <div class="col">
        <h6>Body:</h6>
        <div class="json fixed" :inner-html.prop="response.body | json"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["response"]
};
</script>