<template>
  <default-list :fields="fields" :items="this.eventEnriched" :formatters="formatters"></default-list>
</template>

<script>
  import DefaultList from './default-list'

  export default {
    props: ['events'],
    components: {
      DefaultList
    },
    data() {
      return {
        formatters: [
          { field: 'createdAt', as: 'date' },
        ],
        fields: [
          { key: 'type', thStyle: 'width: 50px;' },
          { key: 'content' },
          { key: 'createdAt', label: 'Created @', thStyle: 'width: 150px;' },
        ],
      }
    },
    methods: {
      addTypeColouring(event) {
        switch (event.type) {
          case 'ERROR': event._rowVariant = 'danger' 
          break
          case 'WARN': event._rowVariant = 'warning'
          break
        }
      }
    },
    computed: {
      eventEnriched() {
        if (this.events !== null) {
          this.events.forEach(event => { this.addTypeColouring(event) } )
        }
        return this.events
      },
    },
    created: function () { },
  }
</script>