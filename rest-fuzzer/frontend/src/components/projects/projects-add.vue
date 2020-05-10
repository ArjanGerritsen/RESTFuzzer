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
        
        <hr />

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
          description="Configuration for project (select none or one), configuration is copied to this project"
        >
          <b-form-checkbox
            switch
            id="input-configuration"
            v-for="config in configurationsForSelection"
            v-model="configuration"
            :key="config.value"
            :value="config.value"
          >{{ config.text }}</b-form-checkbox>
        </b-form-group>
        
        <hr />

        <b-form-group v-if="project.type === 'BASIC_FUZZER'"
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
          <hr />
        </b-form-group>

        <div v-if="project.type === 'MB_FUZZER' || project.type === 'MB_DICTIONARY_FUZZER'">
          <b-form-group
            label="Maximum sequence length:"
            label-for="sequence-length"
            description="Set maximum length of generated sequences"
          >
            <b-form-input
              id="sequence-length"
              v-model="metaDataTuplesJson.maxSequenceLength"
              type="range"
              min="1"
              max="10"
            ></b-form-input>
            <div class="mt-2">Maximum sequence length: {{ metaDataTuplesJson.maxSequenceLength }}</div>
          </b-form-group>

          <hr />
        </div>

        <div v-if="project.type === 'MB_FUZZER' || project.type === 'MB_DICTIONARY_FUZZER'">
          <b-form-group
            label="Maximum number of requests:"
            label-for="max-requests"
            description="Set maximum of requests to be executed"
          >
            <b-form-input
              id="max-requests"
              v-model="metaDataTuplesJson.maxNumRequests"
              type="range"
              min="1000"
              max="500000"
              step="1000"
            ></b-form-input>
            <div class="mt-2">Maximum number of requests: {{ metaDataTuplesJson.maxNumRequests }}</div>
          </b-form-group>

          <hr />
        </div>        

        <b-form-group
          v-if="(project.type === 'DICTIONARY_FUZZER' || project.type === 'MB_DICTIONARY_FUZZER') && dictionariesForSelection.length > 0"
          label="Dictionaries:"
          description="Dictionaries for project (select one or more)">
          <b-form-checkbox-group
            switches
            stacked
            :options="dictionariesForSelection"
            v-model="dictionaries">
          </b-form-checkbox-group>
        </b-form-group>

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
  repetitions: 1,
  maxSequenceLength: 1,
  maxNumRequests: 1000
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
      dictionaries: [],
      metaDataTuplesJson: DEFAULT_META,
      types: [
        { value: "BASIC_FUZZER", text: "Basic" },
        { value: "MB_FUZZER", text: "ModelBased" },
        { value: "DICTIONARY_FUZZER", text: "Dictionary" },
        { value: "MB_DICTIONARY_FUZZER", text: "ModelBasedDictionary" }
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

      if (this.project.type === "BASIC_FUZZER") {
        this.metaDataTuplesJson.repetitions = Number(
          this.metaDataTuplesJson.repetitions
        );
      } else {
        delete this.metaDataTuplesJson.repetitions;
      }

      if (
        this.project.type === "MB_FUZZER" ||
        this.project.type === "MB_DICTIONARY_FUZZER"
      ) {
        this.metaDataTuplesJson.maxSequenceLength = Number(
          this.metaDataTuplesJson.maxSequenceLength
        );
        this.metaDataTuplesJson.maxNumRequests = Number(
          this.metaDataTuplesJson.maxNumRequests
        );
      } else {
        delete this.metaDataTuplesJson.maxSequenceLength;
        delete this.metaDataTuplesJson.maxNumRequests;
      }

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
    async findAllDictionaries() {
      if (this.$store.getters.dictionaries.all.items === null) {
        await this.$store.dispatch("findAllDictionaries");
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
    },
    dictionariesForSelection() {
      this.findAllDictionaries();
      console.log(this.$store.getters.dictionariesForSelection);
      return this.$store.getters.dictionariesForSelection;
    }
  }
};
</script>