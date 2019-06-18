<template>
  <div>
    <b-pagination style="float:right;" v-model="currentPage" :total-rows="rows" :per-page="perPage" aria-controls="project_table"></b-pagination>

    <b-table id="project_table" 
      show-empty
      :busy="isBusy"
      selectable select-mode="single" selectedVariant="primary" @row-selected="selectRow" striped hover 
      :items="items"
      :fields="fields"
      :current-page="currentPage" :per-page="perPage">

      <div slot="table-busy" class="text-center text-primary my-2">
        <b-spinner type="border" class="align-middle" small></b-spinner>
        <strong style="margin-left:10px;">Loading...</strong>
      </div>

      <template v-for="formatter in formatters" :slot="formatter.field" slot-scope="row">
        <template v-if="formatter.as === 'date'">
          {{ row.value | formatDate }}
        </template>
      </template>

      <template slot="empty">
        <h6>No data present.</h6>
      </template>      
    </b-table>
  </div>
</template>

<script>
export default {
  name: 'FrTable',
  props: ['items', 'fields', 'formatters'],
  data() {
     return {
       perPage: 5,
       currentPage: 1
     }
  },
  methods: {
    selectRow(item) {
      if (item.length == 0) { 
        return; 
      }
      this.$emit('select-item', item[0]);
    },
    linkGen(pageNum) {
      return pageNum === 1 ? '?' : `?page=${pageNum}`
    },
  },
  computed: {
    rows() {
      return (this.items === null ? 0 : this.items.length);
    },
    isBusy() {
      return this.items === null;
    }
  }, 
  created: function() { }
}
</script>