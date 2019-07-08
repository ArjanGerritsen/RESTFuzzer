<template>
  <b-card v-if="this.task !== null" no-body>
    <b-tabs nav-tabs card>
      <b-tab title="General" active>
        <b-card-text>
          <div class="row">
            <div class="col" style="margin:5px 0px 15px 0px;">
              <b-button type="submit" variant="outline-primary"><font-awesome-icon icon="running" size="xs" />&nbsp;execute</b-button>
              <b-button type="submit" variant="outline-danger"><font-awesome-icon icon="trash-alt" size="xs" />&nbsp;delete</b-button>
            </div>
          </div>
          <div class="row">
            <div class="col">
              <dl class="dl-horizontal">
                <dt>Status</dt>
                <dd>{{this.task.status}}</dd>
                <dt>Type</dt>
                <dd>{{this.task.type}}</dd>
              </dl>
            </div>
            <div class="col">
              <dl class="dl-horizontal">
                <dt>Description</dt>
                <dd>{{this.task.description}}</dd>
                <dt>Created @</dt>
                <dd>{{this.task.createdAt | formatDate }}</dd>
                <dt>Last started @</dt>
                <dd>{{this.task.lastStartedAt | formatDate }}</dd>
              </dl>
            </div>
          </div>
        </b-card-text>
      </b-tab>
      <b-tab title="Payload">
        <b-card-text>
          <div class="row">
            <div class="col">
              <dl class="dl-horizontal">
                <dt>Payload</dt>
                <dd>{{this.task.payload}}</dd>
              </dl>
            </div>
          </div>
        </b-card-text>
      </b-tab>
      <b-tab title="Events" @click="getEvents()">
        <b-card-text>
          <event-list :events="events"></event-list>
        </b-card-text>
      </b-tab>
    </b-tabs>
  </b-card>
</template>

<script>
import store from '../../shared/Store'
import axios from 'axios'
import EventList from '../../shared/EventList'
import RestService from '../../shared/RestService'

export default {
  components: {
    EventList
  },
  data() {
    return {
      restService: new RestService(this.$bvToast),
      events: null,
    }
  },
  methods: {
    getEvents() {
      // if (this.events !== null) {
      //   return
      // }
      axios
        .get(`rest/admin/tasks/${this.task.id}/events`)
        .then(response => { this.events = response.data })
        .catch(error => {
          RestService.handleError("Couldn't retrieve events for administrative task.", error)
          this.events = []
        }
      )
    },
  },
  computed: {
    task() {
      return store.getters.admin_task
    },
  },
  created: function () { },
}
</script>