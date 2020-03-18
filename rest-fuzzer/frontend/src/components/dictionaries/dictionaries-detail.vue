<template>
  <div v-if="this.dictionary !== null">
    <b-card header-tag="header">
      <span slot="header">
        <b-icon icon="eye" font-scale="1"></b-icon>&nbsp;Dictionary
      </span>
      <b-card-text>
        <b-tabs nav-tabs card>
          <b-tab title="Information" active>
            <div class="row">
              <div class="col">
                <div class="button-group-left">
                  <b-button
                    size="sm"
                    type="submit"
                    v-b-modal.dictionaries-delete
                    variant="outline-danger"
                    title="delete this dictionary"
                  >
                    <b-icon icon="trash" font-scale="1"></b-icon>&nbsp;delete
                  </b-button>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col">
                <dl class="dl-horizontal">
                  <dt>Identifier</dt>
                  <dd>{{this.dictionary.id}}</dd>
                  <dt>Name</dt>
                  <dd>{{this.dictionary.name ? this.dictionary.name : '-'}}</dd>
                </dl>
              </div>
            </div>
          </b-tab>
          <b-tab :title="itemTitle()">
            <div class="row">
              <div class="col">
                <dl class="dl-horizontal">
                  <dt>Items</dt>
                  <dd>
                    <li
                      class="list-inline-item item code"
                      v-for="(value, key) in this.dictionary.items"
                      :key="key"
                    >{{value}}</li>
                  </dd>
                </dl>
              </div>
            </div>
          </b-tab>
        </b-tabs>
      </b-card-text>
    </b-card>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    itemTitle() {
      return `Items [${this.dictionary.items.length}]`;
    }
  },
  computed: {
    dictionary() {
      return this.$store.getters.dictionaries.current;
    }
  },
  created: function() {}
};
</script>

<style scoped>
.item {
  vertical-align: top;
  margin: 5px;
  width: 350px;
}
</style>