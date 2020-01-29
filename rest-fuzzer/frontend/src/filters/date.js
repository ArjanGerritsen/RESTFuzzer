import Vue from 'vue'
import moment from 'moment'

Vue.filter('formatDate', function(value, pattern='DD-MM-YYYY HH:mm') {
  return (value === null || value === '' || value === '0') ? '-' : moment(value, moment.ISO_8601).format(pattern)
})