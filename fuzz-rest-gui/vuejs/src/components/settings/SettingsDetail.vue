<template>
  <b-card v-if="this.setting !== null" no-body>
    <b-tabs nav-tabs card>
      <b-tab active>
        <template slot="title">
          <font-awesome-icon icon="info-circle" size="1x" />&nbsp;General
        </template>        
        <b-card-text>
          <div class="row">
            <div class="col" style="margin:5px 0px 15px 0px;">
              <b-button type="submit" v-b-modal.settings-delete variant="outline-danger"><font-awesome-icon icon="trash-alt" size="xs" />&nbsp;delete</b-button>
            </div>
          </div>
          <div class="row">
            <div class="col">
              <dl class="dl-horizontal">
                <dt>Scope</dt>
                <dd>{{this.setting.scope}}</dd>
                <dt>Key</dt>
                <dd>{{this.setting.key}}</dd>
                <dt>Type</dt>
                <dd>{{this.setting.type}}</dd>
                <dt>Value</dt>
                <dd>{{this.setting.value}}</dd>
              </dl>
            </div>
            <div class="col">
              <dl class="dl-horizontal">
                <dt>Description</dt>
                <dd>{{this.setting.description}}</dd>
                <dt>Created @</dt>
                <dd>{{this.setting.createdAt | formatDate }}</dd>
                <dt>Updated @</dt>
                <dd>{{this.setting.updatedAt | formatDate }}</dd>
              </dl>
            </div>
          </div>
        </b-card-text>
      </b-tab>
    </b-tabs>
  </b-card>
</template>

<script>
import Store from '../shared/Store';

export default {
  components: { },
  data() { 
    return { }
  },
  methods: { },
  computed: { 
    setting() { return Store.getters.admin_setting; }    
  },
  created: function () { }
}
</script>