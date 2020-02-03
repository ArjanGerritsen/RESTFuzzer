import Vue from 'vue';
import App from "./app.vue";

import router from './router';
import store from "./shared/store";

/* bootstrap */
import { BootstrapVue, IconsPlugin }  from 'bootstrap-vue'

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

/* filters */
import './filters/date'
import './filters/other'

/* other */
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");