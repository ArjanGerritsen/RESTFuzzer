/* imports Vue */
import '@babel/polyfill'
import 'mutationobserver-shim'
import Vue from "vue";
import App from "./App.vue";
import router from "./router";

/* bootstrap */
import './plugins/bootstrap-vue'

/* filters */
import './filters/date-filter'

/* other */
Vue.config.productionTip = false

/* vuex */
import store from './components/shared/Store'

/* font-awesome */
import { library } from '@fortawesome/fontawesome-svg-core'
import { faPlus, faCloudDownloadAlt, faCloudUploadAlt, faTrashAlt, faRunning, faList, faHammer, faInfoCircle, faTimesCircle } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(faPlus, faCloudDownloadAlt, faCloudUploadAlt, faTrashAlt, faRunning, faList, faHammer, faInfoCircle, faTimesCircle )

Vue.component('font-awesome-icon', FontAwesomeIcon)

/* validation */
import Vuelidate from 'vuelidate'
Vue.use(Vuelidate)

/* init Vue */
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");