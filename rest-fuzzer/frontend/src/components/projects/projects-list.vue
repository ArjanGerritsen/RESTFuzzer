<template>
  <div>
    <b-card header-tag="header">
      <span slot="header">
        <b-icon icon="tools" font-scale="1"></b-icon>&nbsp;Fuzzing projects
      </span>
      <b-card-text>
        <div class="button-group-left">
          <b-button size="sm" type="submit" variant="primary" v-b-modal.projects-add>
            <b-icon icon="plus" font-scale="1"></b-icon>&nbsp;add
          </b-button>
        </div>
        <list @select-item="selectProject" :fields="fields" :items="projects" :formatters="formatters"></list>
      </b-card-text>
    </b-card>
  </div>
</template>

<script>
import List from "../shared/list/list";

export default {
  components: { List },
  data() {
    return {
      formatters: [{ field: "createdAt", as: "dateShort" }],
      fields: [
        { key: "id", label: "#", thStyle: "width: 50px;" },
        { key: "title", thStyle: "width: 250px;" },
        { key: "location", label: "OAS location" },
        { key: "createdAt", label: "Created @", thStyle: "width: 110px;" }
      ]
    };
  },
  methods: {
    selectProject(project) {
      this.$store.dispatch("findProject", project.id);
      this.$bvModal.show("project-detail");
    }
  },
  computed: {
    projects() {
      return this.$store.getters.projects.all;
    }
  },
  created: function() {
    this.$store.dispatch("findAllProjects");
  }
};
</script>
