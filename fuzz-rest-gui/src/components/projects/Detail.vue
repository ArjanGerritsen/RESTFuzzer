<template>
  <b-card v-if="this.project !== null" no-body>
    <b-tabs pills card>
      <b-tab title="General" active>
        <b-card-text>
          <div class="button-group">
            <b-button type="submit" variant="secondary">Parse OAS</b-button>
            <b-button type="submit" variant="secondary">Start fuzzing</b-button>
            <b-button type="submit" variant="secondary">Create report</b-button>
          </div>

          <div class="row">
            <div class="col">
              <dl class="dl-horizontal">
                <dt>Status</dt>
                <dd>{{this.project.status}}</dd>
                <dt>Description</dt>
                <dd>{{this.project.description}}</dd>
                <dt>OAS</dt>
                <dd>{{this.project.oas_url}}</dd>
                <dt>Created @</dt>
                <dd>{{this.project.created_at | formatDate }}</dd>
                <dt>Started @</dt>
                <dd>{{this.project.last_started_at | formatDate }}</dd>
                <dt>Finished @</dt>
                <dd>{{this.project.last_finished_at | formatDate }}</dd>
              </dl>
            </div>
            <div class="col">
              fsdfsd
            </div>
            <div class="col">
              fsdfsd
            </div>
          </div>
        </b-card-text>
      </b-tab>
      <b-tab title="Running">
        <b-card-text>
          <div class="text-center text-primary my-2">
            <b-spinner type="border" class="align-middle" small></b-spinner>
            <strong style="margin-left:10px;">Loading...</strong>
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