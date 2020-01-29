import Vue from 'vue'

Vue.filter('dynamicFilter', function(value, filterFunction) {
  return filterFunction(value);
})