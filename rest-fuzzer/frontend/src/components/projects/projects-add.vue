<template>
  <b-modal id="projects-add" ref="modal" size="md">
    <template slot="modal-header">
      <h6>
        <b-icon icon="plus" font-scale="1"></b-icon>&nbsp;Add fuzzing project
      </h6>
    </template>

    <b-form>
      <b-form-group id="input-group-1" label="Type:" label-for="input-1" description="Select type">
        <b-form-select id="input-1" :options="types" v-model="project.type" required>
          <template v-slot:first>
            <b-form-select-option :value="null" disabled>-- select a type --</b-form-select-option>
          </template>
        </b-form-select>
      </b-form-group>

      <div v-if="project.type === 'BASIC_FUZZER'">
        <b-form-group
          id="input-group-2"
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
      </div>

      <b-form-group
        id="input-group-2"
        label="System under test:"
        label-for="input-2"
        description="Select system under test"
      >
        <b-form-select id="input-2" :options="suts" v-model="project.sut.id" required>
          <template v-slot:first>
            <b-form-select-option :value="null" disabled>-- select a system under test --</b-form-select-option>
          </template>
        </b-form-select>
      </b-form-group>
    </b-form>

    <template slot="modal-footer" slot-scope="{ cancel }">
      <div class="button-group-right">
        <b-button size="sm" type="submit" variant="primary" @click="addProject()">
          <b-icon icon="plus" font-scale="1"></b-icon>&nbsp;add
        </b-button>
        <b-button size="sm" type="cancel" variant="outline-secondary" @click="cancel()">
          <b-icon icon="backspace" font-scale="1"></b-icon>&nbsp;cancel
        </b-button>
      </div>
    </template>
  </b-modal>
</template>

<script>
const DEFAULT_META = {
  repetitions: 1
};

export default {
  data() {
    return {
      project: {
        type: null,
        sut: {
          id: null
        },
        metaDataTuplesJson: null
      },
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
    resetForm() {
      this.project.type = null;
      this.metaDataTuplesJson = DEFAULT_META;
    },
    hide() {
      this.$nextTick(() => {
        this.$refs.modal.hide();
      });
    },
    cancel() {
      this.resetForm();
      this.hide();
    },
    setMetaDataTuplesJson() {
      this.metaDataTuplesJson.repetitions = Number(
        this.metaDataTuplesJson.repetitions
      );
      this.project.metaDataTuplesJson = JSON.stringify(this.metaDataTuplesJson);
    },
    addProject() {
      this.setMetaDataTuplesJson();
      this.$store.dispatch("addProject", this.project).then(() => {
        this.cancel();
        this.$store.dispatch("findAllProjects");
      });
    },
    async findAllSuts() {
      if (this.$store.getters.suts.all == null) {
        await this.$store.dispatch("findAllSuts");
      }
    }
  },
  computed: {
    suts() {
      this.findAllSuts();
      return this.$store.getters.sutsForPullDown;
    }
  }
};
</script>
