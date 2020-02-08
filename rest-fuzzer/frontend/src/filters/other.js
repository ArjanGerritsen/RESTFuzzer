import Vue from 'vue'
import constants from '../shared/constants';

Vue.filter('dynamicFilter', function(value, filterFunction) {
  return filterFunction(value);
})

Vue.filter('statusToIcon', function(status) {
  switch (status) {
    case constants.TASK_STATUS_QUEUED:
      return '<b-icon icon="trash" font-scale="1"></b-icon>';
    case constants.TASK_STATUS_RUNNING:
      return '<b-icon icon="trash" font-scale="1"></b-icon>';
    case constants.TASK_STATUS_ENDED:
      return '<b-icon icon="trash" font-scale="1"></b-icon>';        
    default:
      return status;
  }
})

Vue.filter('enumToHuman', function(string) {
	string = string.split("_").map(
		s => {
			return s[0].toUpperCase() + 
				s.substring(1).toLowerCase(); 
		}
	).join("");

	return string;
})