import Vue from 'vue'
import moment from 'moment'

Vue.filter('formatDate', function(value, pattern='DD-MM-YYYY hh:mm') {
  return moment(String(value)).format(pattern)
})