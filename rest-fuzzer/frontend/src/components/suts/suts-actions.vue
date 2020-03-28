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
      id="sut-actions"
      class="table-sm"
      show-empty
      :busy="isBusy"
      striped
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
          <h6>Parameters:</h6>
          <li
            class="list-inline-item"
            style="vertical-align:top; margin:8px; width: 190px;"
            v-for="(value, key) in row.item.parameters"
            :key="key"
          >
            <b>#{{ value.id }} </b>
            <b-badge v-if="value.required" variant="primary">required</b-badge>
            <br />
            name: {{ value.name}}
            <br />
            context: {{ value.context }}
            <br />
            type: {{ value.type }}
            <br />
            extra: {{ value.metaDataTuplesJson === "{}" ? "-" : value.metaDataTuplesJson }}
          </li>

          <hr />

          <h6>Responses:</h6>
          <li
            class="list-inline-item"
            style="margin:8px; width: 190px;"
            v-for="(value, key) in row.item.responses"
            :key="key"
          >
            <b>#{{ value.id }}</b>
            <br />
            http status: {{ value.statusCode }}
            <br />
            description: {{ value.description }}
          </li>
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
  props: ["sut", "fields", "formatters"],
  data() {
    return {
      isBusy: false,
      filter: null,
      perPage: 15,
      currentPage: 1,
      filterShadow: null
    };
  },
  methods: {
    restProvider(context, callback) {
      if (this.filter !== this.filterShadow) {
        this.currentPage = 1;
      } else {
        this.currentPage = context.currentPage;
      }
      this.filterShadow = this.filter;

      return this.$store
        .dispatch("findSutActions", {
          sut_id: this.sut.id,
          context: context
        })
        .then(() => {
          return this.$store.getters.suts.current_actions.list;
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
      return this.$store.getters.suts.current_actions.count;
    },
    displayPagination() {
      return this.totalRows > this.perPage;
    }
  },
  created: function() {}
};
</script>
