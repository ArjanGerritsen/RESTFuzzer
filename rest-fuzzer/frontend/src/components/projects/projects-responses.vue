<template>
  <div>
    <b-row style="margin-bottom:5px;">
      <b-col lg="12">
        <b-card bg-variant="white" header-tag="header" class="float-right clearfix">
          <template v-slot:header>
            <h6 class="mb-0">
              Filter: displaying
              <b>{{ totalRows }}</b> results.
            </h6>
          </template>
          <b-card-text>
            <div class="float-left" style="margin-right:25px;">
              <b-form-group label-size="sm" label="HTTP method(s):" label-for="input-http-method">
                <b-form-checkbox
                  class="float-left"
                  style="margin-right:15px;"
                  size="sm"
                  v-model="filter.httpMethods"
                  v-for="method in httpMethods"
                  :key="method"
                  :value="method"
                >{{ method }}</b-form-checkbox>
              </b-form-group>
              <b-link @click="filter.httpMethods = constants.HTTP_METHODS">select all</b-link>/
              <b-link @click="filter.httpMethods = []">select none</b-link>
            </div>
            <div class="float-left" style="margin-right:25px;">
              <b-form-group
                label-size="sm"
                label="HTTP response code ranges:"
                label-for="input-http-response-range"
              >
                <b-form-checkbox
                  class="float-left"
                  style="margin-right:15px;"
                  size="sm"
                  v-model="filter.httpResponseRanges"
                  v-for="range in httpResponseRanges"
                  :key="range"
                  :value="range"
                >{{ range }}</b-form-checkbox>
              </b-form-group>
              <b-link @click="filter.httpResponseRanges = constants.HTTP_RESPONSE_RANGES">select all</b-link>/
              <b-link @click="filter.httpResponseRanges = []">select none</b-link>
            </div>
            <div class="float-left" style="margin-right:25px;">
              <b-form-group label-size="sm" label="Path:" label-for="input-path">
                <b-input-group size="sm" label-for="input-path">
                  <b-form-input
                    id="input-path"
                    v-model="filter.path"
                    size="sm"
                    type="search"
                    placeholder="type to filter path"
                  ></b-form-input>
                  <b-input-group-append>
                    <b-button :disabled="!filter.path" @click="filter.path = ''">clear</b-button>
                  </b-input-group-append>
                </b-input-group>
              </b-form-group>
            </div>
          </b-card-text>
        </b-card>
      </b-col>
    </b-row>

    <b-table
      id="project-responses"
      class="table-sm"
      show-empty
      striped
      :busy.sync="isBusy"
      :items="restProvider"
      :fields="fields"
      :borderless="true"
      :filter="filterToJson"
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
        <b-card no-body>
          <b-tabs card pills>
            <b-tab title="Response" active>
              <b-card-text>
                <dl class="dl-horizontal">
                  <dt>Created @:</dt>
                  <dd>{{row.item.createdAt | date}}</dd>
                  <dt>HTTP status:</dt>
                  <dd>{{row.item.statusCode}}</dd>
                  <dt>HTTP status description:</dt>
                  <dd>{{row.item.statusDescription}}</dd>
                  <dt>Exception:</dt>
                  <dd>{{row.item.failureReason === null ? '-' : row.item.failureReason}}</dd>
                </dl>
                <div class="row">
                  <div class="col" style="margin-bottom:20px">
                    <h6>Body:</h6>
                    <div class="json" :inner-html.prop="row.item.body | json"></div>
                  </div>
                </div>
              </b-card-text>
            </b-tab>
            <b-tab title="Request">
              <b-card-text>
                <ProjectDetailRequest :item="row.item.request"></ProjectDetailRequest>
              </b-card-text>
            </b-tab>
            <b-tab title="REST model description">
              <b-card-text>TODO</b-card-text>
            </b-tab>
          </b-tabs>
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
import Constants from "../../shared/constants";

import ProjectDetailRequest from "./projects-detail-request";

export default {
  components: { ProjectDetailRequest },
  props: ["project", "fields", "formatters"],
  data() {
    return {
      constants: Constants,
      isBusy: false,
      perPage: Constants.PER_PAGE,
      currentPage: 1,
      httpMethods: Constants.HTTP_METHODS,
      httpResponseRanges: Constants.HTTP_RESPONSE_RANGES,
      filter: {
        httpMethods: Constants.HTTP_METHODS,
        httpResponseRanges: Constants.HTTP_RESPONSE_RANGES,
        path: ""
      },
      filterCopy: null
    };
  },
  methods: {
    restProvider(context, callback) {
      if (this.filterCopy != this.filterToJson) {
        this.currentPage = 1;
        context.currentPage = 1;
        this.filterCopy = this.filterToJson;
      } else {
        this.currentPage = context.currentPage;
      }

      return this.$store
        .dispatch("findProjectResponses", {
          id: this.project.id,
          context: context
        })
        .then(() => {
          return this.$store.getters.projects.current.responses.items;
        })
        .catch(() => {
          return [];
        });
    },
    linkGen(pageNum) {
      return pageNum === 1 ? "?" : `?page=${pageNum}`;
    }
  },
  computed: {
    filterToJson() {
      return encodeURI(JSON.stringify(this.filter));
    },
    totalRows() {
      return this.$store.getters.projects.current.responses.count;
    },
    displayPagination() {
      return this.totalRows > this.perPage;
    }
  },
  created: function() {}
};
</script>
