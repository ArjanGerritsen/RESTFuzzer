<template>
  <div>
    <b-table id="list"
      class="table-sm"
      show-empty
      :busy="isBusy"
      selectable select-mode="single" selectedVariant="primary" @row-selected="selectRow" @row-clicked="rowClicked" striped hover 
      :items="items"
      :fields="fields"
      :borderless="true"
      :current-page="currentPage" :per-page="perPage">

      <div slot="table-busy" class="text-center text-primary my-2">
        <b-spinner type="border" class="align-middle" small></b-spinner>
        <span style="margin-left:10px;">Loading...</span>
      </div>

      <template v-for="formatter in formatters" v-slot:[`cell(${formatter.field})`]="data">
        <template>
          {{ data.value | dynamicFilter($options.filters[formatter.as]) }}
        </template>
      </template>

      <template slot="empty">
        <h6>No data present.</h6>
      </template>      
    </b-table>

    <b-pagination v-if="displayPagination" style="float:right;" v-model="currentPage" :total-rows="rows" :per-page="perPage" aria-controls="list"></b-pagination>
  </div>
</template>

<script>
  export default {
    props: ['items', 'fields', 'formatters'],
    data() {
       return {
         perPage: 18,
         currentPage: 1,
       }
    },
    methods: {
      selectRow(item) {
        if (item.length == 0) { 
          return; 
        }
        this.$emit('select-item', item[0]);
      },
      rowClicked(item) {
        this.$emit('deselect-item');
      },
      linkGen(pageNum) {
        return pageNum === 1 ? '?' : `?page=${pageNum}`
      }
    },
    computed: {
      rows() {
        return (this.items === null ? 0 : this.items.length)
      },
      isBusy() {
        return this.items === null
      },
      displayPagination() {
        if (this.items === null) {
          return false;
        } else {
          return this.items.length > this.perPage;
        }
      },
    }, 
    created: function() { }
  }
</script>
