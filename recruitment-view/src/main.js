import Vue from 'vue';
import Vuex from 'vuex'
import App from './App.vue';
import router from './router/Index';
// import components from './components/Index'
// import {UserConstants} from '@/constants/Global'

Vue.config.productionTip = false;
Vue.use(Vuex);
// Vue.prototype.UserConstants = UserConstants;

// components.map(component => {
//   Vue.component(component.name, component);
// });

new Vue({
  el: '#app',
  router: router,
  render: h => h(App)
});
