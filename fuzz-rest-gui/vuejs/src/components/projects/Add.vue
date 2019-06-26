<template>
  <b-card v-if="display" header="Add project" @submit="this.submit">
    <b-form>
      <b-form-group
        id="input-group-1"
        label="Description:"
        label-for="input-1"
      >
        <b-form-input
          id="input-1"
          v-model="project.description"
          required
          placeholder="Enter description"
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-2" label="OAS url:" label-for="input-2" description="Url to OpenAPI Specification">
        <b-form-input
          id="input-2"
          v-model="project.oasUrl"
          required
          placeholder="Enter OAS url"
        ></b-form-input>
      </b-form-group>

      <b-button type="submit" variant="primary">Submit</b-button>
      <b-button type="cancel" variant="secondary" @click="this.cancel">Cancel</b-button>
    </b-form>
  </b-card>
</template>

<script>
  import RestService from '../shared/RestService'

  export default {
    data() {
      return {
        project: {
          description: '',
          oasUrl: '',
        },
        display: true,
        restService: new RestService(this.$bvToast),
      }
    },
    methods: {
      reset() {
        this.project.description = ''
        this.project.oasUrl = ''
      },
      cancel() {
        this.reset
        this.display = false 
      },
      submit(event) { 
        event.preventDefault()
        this.restService.addProject(this.project)
      },
    }
  }
</script>