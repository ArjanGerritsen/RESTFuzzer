<template>
  <b-card no-body>
    <b-tabs pills card>
      <b-tab title="General" active>
        <b-card-text>First select a project.</b-card-text>
          {{this.project}}
      </b-tab>
      <b-tab disabled title="Running">
        <b-card-text>
          {{this.project}}
        </b-card-text>
      </b-tab>
      <b-tab disabled title="History">
        <b-card-text>
          {{this.project}}
        </b-card-text>
      </b-tab>
    </b-tabs>
  </b-card>
</template>

<script>
import store from '../../plugins/veux'

export default {
  name: 'Project',
  data() {
     return { }
  },
  computed: {
    project() {
      return store.getters.project
    }
  }
}
</script>