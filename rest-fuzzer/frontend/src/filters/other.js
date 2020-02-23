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

Vue.filter('json', function(json) {
  json = JSON.stringify(JSON.parse(json), null, 2);
  
  // keys
  json = json.replace(/"[\w]*":/g, function (match) {
    return '<span class="key">' + match.substring(1, match.length - 2) + '</span>:';
  });

  // strings
  json = json.replace(/ "[\w]*"/g, function (match) {
    return ' "<span class="string">' + match.substring(2, match.length - 1) + '</span>"';
  });

  // numbers
  json = json.replace(/ ([0-9]*[.])?[0-9]+/g, function (match) {
    return ' <span class="number">' + match.substring(1, match.length) + '</span>';
  });

  // booleans (true)
  json = json.replace(/ (true)/g, function (match) {
    return ' <span class="true">' + match.substring(1, match.length) + '</span>';
  });

  // booleans (false)
  json = json.replace(/ (false)/g, function (match) {
    return ' <span class="false">' + match.substring(1, match.length) + '</span>';
  });

  return json;
})