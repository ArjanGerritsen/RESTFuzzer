<template>
  <b-card v-if="display" header-tag="header" footer-tag="footer">
    <template v-slot:header>
      <b-icon icon="plus" font-scale="1"></b-icon>&nbsp;Add configuration
    </template>

    <b-card-text>
      <b-form>
        <b-form-group label="Name:" label-for="configuration-name" description="Name">
          <b-form-input
            id="configuration-name"
            v-model="configuration.name"
            placeholder="enter the name"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          label="Configuration:"
          label-for="configuration-items"
          description="Configuration (default loaded)"
        >
          <b-form-textarea
            id="configuration-items"
            prop="String"
            v-model="configuration.itemsJson"
            placeholder="enter the configuration"
            rows="12"
          ></b-form-textarea>
        </b-form-group>
      </b-form>
    </b-card-text>

    <template v-slot:footer>
      <div class="button-group-right">
        <b-button size="sm" variant="primary" @click="add()">
          <b-icon icon="plus" font-scale="1"></b-icon>&nbsp;add
        </b-button>
        <b-button size="sm" variant="outline-secondary" @click="cancel()">
          <b-icon icon="backspace" font-scale="1"></b-icon>&nbsp;cancel
        </b-button>
      </div>
    </template>
  </b-card>
</template>

<script>
const DEFAULT_CONFIGURATION = {
  includeActions: [{ actions: [{ path: "*", httpMethod: "*" }] }],
  excludeActions: [{ actions: [{ path: "*", httpMethod: "*" }] }],
  skipParameters: [
    { actions: [{ path: "*", httpMethod: "*" }], parameters: [{ name: "*" }] }
  ]
};

export default {
  data() {
    return {
      configuration: {
        name: null,
        itemsJson: JSON.stringify(DEFAULT_CONFIGURATION, null, 4)
      }
    };
  },
  computed: {
    display() {
      return (
        this.$store.getters.configurations.display !== null &&
        this.$store.getters.configurations.display === "add"
      );
    }
  },
  methods: {
    reset() {
      this.configuration.name = "";
      this.configuration.itemsJson = JSON.stringify(DEFAULT_CONFIGURATION, null, 4);
    },
    cancel() {
      this.reset();
      this.$store.commit("set_configuration_display", { display: null });
    },
    add() {
      this.$store.dispatch("addConfiguration", this.configuration).then(() => {
        this.cancel();
        this.$store.dispatch("findAllConfigurations");
      });
    }
  }
};
</script>