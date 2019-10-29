/* imports Vue */
import '@babel/polyfill'
import 'mutationobserver-shim'
import Vue from "vue";
import App from "./app.vue";
import router from "./router";

/* bootstrap */
import './plugins/bootstrap-vue'

/* filters */
import './filters/date-filter'

/* other */
Vue.config.productionTip = false

/* vuex */
import store from './store/store'

/* font-awesome */
import { library } from '@fortawesome/fontawesome-svg-core'
import { faPlus, faCloudDownloadAlt, faCloudUploadAlt, faTrashAlt, faRunning, faList, faHammer, faInfoCircle, faTimesCircle, faWrench } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(faPlus, faCloudDownloadAlt, faCloudUploadAlt, faTrashAlt, faRunning, faList, faHammer, faInfoCircle, faTimesCircle, faWrench )

Vue.component('font-awesome-icon', FontAwesomeIcon)

/* init Vue */
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");