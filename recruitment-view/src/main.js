import Vue from 'vue';
import Vuex from 'vuex'
import App from './App.vue';
import router from './router/Index';
Vue.config.productionTip = false;
Vue.use(Vuex);
new Vue({
  el: '#app',
  router: router,
  render: h => h(App)
});
