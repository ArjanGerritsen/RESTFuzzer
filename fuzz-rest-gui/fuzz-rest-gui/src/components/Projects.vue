<template>
  <div>
    <div class="path">
      <h5>Projects</h5>
    </div>

    <b-pagination style="float:right;" v-model="currentPage" :total-rows="rows" :per-page="perPage" aria-controls="project_table"></b-pagination>

    <b-table id="project_table" selectable select-mode="single" selectedVariant="primary" @row-selected="selectRow"
     striped hover :items="items" :fields="fields" :busy="isBusy"
     :current-page="currentPage" :per-page="perPage">
      <div slot="table-busy" class="text-center text-primary my-2">
        <b-spinner type="grow" class="align-middle"></b-spinner>
        <strong style="margin-left:10px;">Loading...</strong>
      </div>      
    </b-table>

    <div>{{this.selectedProject}}</div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        isBusy: true,
        perPage: 3,
        currentPage: 1,
        selectedProject: null,
        fields: [
          { key: 'status' }, 
          { key: 'oas_url', label: 'OpenAPI Specifcation' },
          { key: 'description' },
          { key: 'created_at', label: 'Created at' },
          { key: 'run_count', label: '# Runs' },
          { key: 'last_started_at', label: 'Started @' },
          { key: 'last_finished_at', label: 'Finished @' }
        ],
        items: [
          { status: 'running', description: 'Met config 8', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 7', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 6', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 5', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 4', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 3', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 2', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() },
          { status: 'running', description: 'Met config 1', oas_url: 'http://localhost/swagger.json', created_at: new Date(), run_count: 3, last_started_at: new Date(), last_finished_at: new Date() }
        ]
      }
    },
    methods: {
      selectRow(project) {
        this.selectedProject = project;
        console.log(project);
      },
      linkGen(pageNum) {
        return pageNum === 1 ? '?' : `?page=${pageNum}`
      }
    },
    computed: {
      rows() {
        return this.items.length
      }
    },
    created: function(){
      setTimeout(() => {
        this.isBusy = false;        
      }, 1500);
    }
  }
</script>