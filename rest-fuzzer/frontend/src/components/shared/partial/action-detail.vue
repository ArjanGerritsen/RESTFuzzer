<template>
  <div>
    <h6>Parameters:</h6>
    <div v-for="(parameter) in action.parameters" :key="getKey('prm', parameter)">
      <ParameterDetail :parameter="parameter"></ParameterDetail>
    </div>

    <hr />

    <h6>Responses:</h6>
    <div v-for="(response) in action.responses" :key="getKey('res1', response)">
      <ActionResponseDetail :response="response"></ActionResponseDetail>
    </div>

    <hr />

    <h6>Dependencies:</h6>
    <ul>
      <li
        class="list-inline-item"
        style="vertical-align:top; margin:8px; width: 250px;"
        v-for="(dependency) in action.dependencies"
        :key="getKey('dep', dependency)"
      >
        <b>#{{ dependency.id }}</b>
        &nbsp;
        <b-badge variant="primary">{{dependency.discoveryModus}}</b-badge>
        <br />
        parameter: {{ dependency.parameter.name }} (#{{ dependency.parameter.id }})
        <br />
        depends on action: {{ dependency.actionDependsOn | ppAction }}
        <br />
        depends on parameter: {{ dependency.parameterDependsOn }}
      </li>
      <li
        v-if="action.dependencies.length === 0"
        class="list-inline-item"
        style="margin:8px; width: 190px;"
      >Not present.</li>
    </ul>
  </div>
</template>

<script>
import ParameterDetail from "../../shared/partial/parameter-detail";
import ActionResponseDetail from "../../shared/partial/action-response-detail";

export default {
  props: ["action"],
  components: { ParameterDetail, ActionResponseDetail },
  methods: {
    getKey: function(prefix, object) {
      return `${prefix}_${object.id}`;
    }
  }
};
</script>

<style scoped>
ul {
  margin: 0px;
  padding: 0px;
}
</style>