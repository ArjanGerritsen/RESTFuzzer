<template>
  <div>
    <b-row style="margin-bottom:5px;">
      <b-col lg="6" class="my-1">
        <b-input-group size="sm">
          <b-form-input
            v-model="filter"
            type="search"
            id="filterInput"
            placeholder="type to filter table"
          ></b-form-input>
          <b-input-group-append>
            <b-button :disabled="!filter" @click="filter = ''">clear</b-button>
          </b-input-group-append>
        </b-input-group>
      </b-col>
      <b-col lg="6" class="my-1"></b-col>
    </b-row>

    <b-table
      id="requests"
      class="table-sm"
      show-empty
      striped
      :busy.sync="isBusy"
      :items="restProvider"
      :fields="fields"
      :borderless="true"
      :filter="filter"
      @filtered="onFiltered"
      :current-page="currentPage"
      :per-page="perPage"
    >
      <div slot="table-busy" class="text-center text-primary my-2">
        <b-spinner type="border" class="align-middle" small></b-spinner>
        <span style="margin-left:10px;">Loading...</span>
      </div>

      <template
        v-for="formatter in formatters"
        v-slot:[`cell(${formatter.field})`]="data"
      >
        <template>{{ data.value | dynamicFilter($options.filters[formatter.as]) }}</template>
      </template>

      <template v-slot:cell(details)="row">
        <b-button size="sm" variant="primary" @click="row.toggleDetails">
          <b-icon v-if="row.detailsShowing" icon="x" font-scale="1"></b-icon>
          <b-icon v-if="!row.detailsShowing" icon="plus" font-scale="1"></b-icon>
        </b-button>
      </template>

      <template v-slot:row-details="row">
        <b-card>
          <div class="row">
            <div class="col" style="margin-bottom:20px">
              <h6>Form data parameters:</h6>
              <div class="json" :inner-html.prop="row.item.formdataParametersJson | json"></div>
            </div>
            <div class="col">
              <h6>Header parameters:</h6>
              <div class="json" :inner-html.prop="row.item.headerParametersJson | json"></div>
            </div>
          </div>
          <div class="row" style="margin-bottom:20px">
            <div class="col">
              <h6>Path parameters:</h6>
              <div class="json" :inner-html.prop="row.item.pathParametersJson | json"></div>
            </div>
            <div class="col">
              <h6>Query parameters:</h6>
              <div class="json" :inner-html.prop="row.item.queryParametersJson | json"></div>
            </div>
          </div>
          <dl class="dl-horizontal">
            <dt>Created @:</dt>
            <dd>{{row.item.createdAt | date}}</dd>
            <dt>Executed @:</dt>
            <dd>{{row.item.executedAt | date}}</dd>
          </dl>
        </b-card>
      </template>

      <template slot="empty">No data present.</template>
    </b-table>

    <b-pagination
      v-if="displayPagination"
      size="sm"
      style="float:right;"
      v-model="currentPage"
      :total-rows="totalRows"
      :per-page="perPage"
      aria-controls="list"
    ></b-pagination>
  </div>
</template>

<script>
export default {
  props: ["project", "fields", "formatters"],
  data() {
    return {
      isBusy: false,
      filter: null,
      perPage: 15,
      currentPage: 1
    };
  },
  methods: {
    restProvider(context, callback) {
      return this.$store
        .dispatch("findProjectRequests", {
          project_id: this.project.id,
          context: context
        })
        .then(() => {
          return this.$store.getters.projects.current_requests.visible;
        })
        .catch(() => {
          return this.$store.getters.projects.current_requests.visible;
        });
    },
    linkGen(pageNum) {
      return pageNum === 1 ? "?" : `?page=${pageNum}`;
    },
    onFiltered(filteredItems) {
      this.currentPage = 1;
    }
  },
  computed: {
    totalRows() {
      return this.$store.getters.projects.current_requests.count;
    },
    displayPagination() {
      return this.totalRows > this.perPage;
    }
  },
  created: function() {}
};
</script>
