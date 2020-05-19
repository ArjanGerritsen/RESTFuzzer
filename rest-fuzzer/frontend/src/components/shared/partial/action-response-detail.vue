<template>
  <div>
    <dl class="row">
      <dt class="col-sm-2">identifier</dt>
      <dd class="col-sm-10">
        <b>#{{response.id}}</b>
      </dd>
      <dt class="col-sm-2">HTTP status code</dt>
      <dd class="col-sm-10">{{response.httpStatusCode}}</dd>
      <dt class="col-sm-2">description</dt>
      <dd class="col-sm-10">{{response.description}}</dd>
      <dt class="col-sm-2">created @</dt>
      <dd class="col-sm-10">{{response.createdAt | date}}</dd>
    </dl>
  </div>
</template>

<script>
export default {
  props: ["response"]
};
</script>

<style scoped>
dl {
  margin: 20px 0px 20px 0px;
  line-height: 1.1em;
}
dt {
  font-weight: normal;
}
dd {
  font-style: italic;
}
</style>