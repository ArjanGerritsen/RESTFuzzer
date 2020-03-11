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
          <ProjectDetailRequest :item="row.item"></ProjectDetailRequest>
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
import ProjectDetailRequest from "./projects-detail-request";

export default {
  components: { ProjectDetailRequest },
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
          return this.$store.getters.projects.requests.list;
        })
        .catch(() => {
          return [];
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
      return this.$store.getters.projects.requests.count;
    },
    displayPagination() {
      return this.totalRows > this.perPage;
    }
  },
  created: function() {}
};
</script>
