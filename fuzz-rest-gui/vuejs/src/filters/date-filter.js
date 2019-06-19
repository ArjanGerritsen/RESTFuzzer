import Vue from 'vue'
import moment from 'moment'

Vue.filter('formatDate', function(value, pattern='DD-MM-YYYY HH:mm') {
  return moment(Number(value)).format(pattern)
})