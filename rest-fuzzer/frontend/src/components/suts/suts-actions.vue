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
  </div>
</template>

<script>
  export default {
    components: { },
    data() {
      return { data: {} };
    },
  };
</script>
