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

/* init Vue */
new Vue({
  router,
  render: h => h(App)
}).$mount("#app");