<template>
  <b-card v-if="display" header-tag="header" footer-tag="footer">
    <template v-slot:header>
      <b-icon icon="plus" font-scale="1"></b-icon>&nbsp;Add project
    </template>

    <b-card-text>
      <b-form>
        <b-form-group label="Description:" label-for="description" description="Describe project">
          <b-form-textarea id="description" v-model="project.description" required></b-form-textarea>
        </b-form-group>

        <b-form-group label="Type:" label-for="type" description="Select type">
          <b-form-select id="type" :options="types" v-model="project.type" required>
            <template v-slot:first>
              <b-form-select-option :value="null" disabled>-- select a type --</b-form-select-option>
            </template>
          </b-form-select>
        </b-form-group>

        <hr />

        <b-form-group
          v-if="configurationsForSelection.length > 0"
          label="Configuration:"
          label-for="input-configuration"
          description="Configuration for project (select zero or more items), configurations are merged and copied to this project"
        >
          <b-form-checkbox
            switch
            v-for="config in configurationsForSelection"
            v-model="configuration"
            :key="config.value"
            :value="config.value"
          >{{ config.text }}</b-form-checkbox>

          <hr />
        </b-form-group>

        <div v-if="project.type === 'BASIC_FUZZER'">
          <b-form-group
            label="Repetitions:"
            label-for="input-2"
            description="Set number of repetitions"
          >
            <b-form-input
              id="range-1"
              v-model="metaDataTuplesJson.repetitions"
              type="range"
              min="1"
              max="25000"
            ></b-form-input>
            <div class="mt-2">Repetitions: {{ metaDataTuplesJson.repetitions }}</div>
          </b-form-group>

          <hr />
        </div>

        <b-form-group
          label="System under test:"
          label-for="input-2"
          description="Select system under test"
        >
          <b-form-select id="input-2" :options="sutsForSelection" v-model="project.sut.id" required>
            <template v-slot:first>
              <b-form-select-option :value="null" disabled>-- select a system under test --</b-form-select-option>
            </template>
          </b-form-select>
        </b-form-group>
      </b-form>
    </b-card-text>

    <template v-slot:footer>
      <div class="button-group-right">
        <b-button size="sm" type="submit" variant="primary" @click="add()">
          <b-icon icon="plus" font-scale="1"></b-icon>&nbsp;add
        </b-button>
        <b-button size="sm" type="cancel" variant="outline-secondary" @click="cancel()">
          <b-icon icon="backspace" font-scale="1"></b-icon>&nbsp;cancel
        </b-button>
      </div>
    </template>
  </b-card>
</template>

<script>
const DEFAULT_META = {
  configuration: {},
  repetitions: 1
};

export default {
  data() {
    return {
      project: {
        description: null,
        type: null,
        sut: {
          id: null
        },
        metaDataTuplesJson: null
      },
      configuration: null,
      metaDataTuplesJson: DEFAULT_META,
      types: [
        { value: "BASIC_FUZZER", text: "Basic" },
        { value: "MBT_FUZZER", text: "ModelBased" },
        { value: "DICTIONARY_FUZZER", text: "Dictionary" },
        { value: "MBT_DICTIONARY_FUZZER", text: "ModelBasedDictionary" }
      ]
    };
  },
  methods: {
    reset() {
      this.project.description = null;
      this.project.type = null;
      this.metaDataTuplesJson = DEFAULT_META;
    },
    cancel() {
      this.reset();
      this.$store.commit("set_project_display", { display: null });
    },
    setMetaDataTuplesJson() {
      this.metaDataTuplesJson.configuration = this.getConfigurationJson();

      this.metaDataTuplesJson.repetitions = Number(
        this.metaDataTuplesJson.repetitions
      );

      this.project.metaDataTuplesJson = JSON.stringify(this.metaDataTuplesJson);
    },
    add() {
      this.setMetaDataTuplesJson();
      this.$store.dispatch("addProject", this.project).then(() => {
        this.cancel();
        this.$store.dispatch("findAllProjects");
      });
    },
    async findAllSuts() {
      if (this.$store.getters.suts.all === null) {
        await this.$store.dispatch("findAllSuts");
      }
    },
    async findAllConfigurations() {
      if (this.$store.getters.configurations.all.items === null) {
        await this.$store.dispatch("findAllConfigurations");
      }
    },
    getConfigurationJson() {
      let configurations = this.$store.getters.configurations.all.items.filter(
        config => {
          return this.configuration === config.id;
        }
      );

      let configurationsJson = {};

      if (configurations.length === 1) {
        configurationsJson = JSON.parse(configurations[0].itemsJson);
      }

      return configurationsJson;
    }
  },
  computed: {
    display() {
      return (
        this.$store.getters.projects.display !== null &&
        this.$store.getters.projects.display === "add"
      );
    },
    sutsForSelection() {
      this.findAllSuts();
      return this.$store.getters.sutsForSelection;
    },
    configurationsForSelection() {
      this.findAllConfigurations();
      return this.$store.getters.configurationsForSelection;
    }
  }
};
</script>