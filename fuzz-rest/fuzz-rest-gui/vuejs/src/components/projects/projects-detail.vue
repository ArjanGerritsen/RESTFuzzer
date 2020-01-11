<template>
  <b-card v-if="this.project !== null" no-body>
    <b-tabs nav-tabs card>
      <b-tab title="General" active>
        <b-card-text>
          <div class="row">
            <div class="col">
              <dl class="dl-horizontal">
                <dt>Status</dt>
                <dd>{{this.project.status}}</dd>
                <dt>Description</dt>
                <dd>{{this.project.description}}</dd>
                <dt>OAS url</dt>
                <dd>{{this.project.oasUrl}}</dd>
                <dt>Created @</dt>
                <dd>{{this.project.createdAt | formatDate }}</dd>
              </dl>
            </div>
          </div>
        </b-card-text>
      </b-tab>
      <b-tab title="Running">
        <b-card-text>
          <div class="text-center text-primary my-2">
            <b-spinner type="border" class="align-middle" small></b-spinner>
            <span style="margin-left:10px;">Loading...</span>
          </div>
        </b-card-text>
      </b-tab>
      <b-tab disabled title="History">
        <b-card-text>
        </b-card-text>
      </b-tab>
    </b-tabs>
  </b-card>
</template>

<script>
import Store from '../../store/store'

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