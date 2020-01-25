import Vue from 'vue';
import App from "./app.vue";

import router from './router';
import store from "./store";

/* bootstrap */
import { BootstrapVue, IconsPlugin }  from 'bootstrap-vue'

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

/* other */
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");