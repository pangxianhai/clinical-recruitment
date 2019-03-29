import Vue from 'vue';
import Vant from 'vant';
import router from './router';
import 'vant/lib/index.css';
import App from './App.vue';

Vue.config.productionTip = false;
Vue.use(Vant);

new Vue({
  el: '#app',
  router: router,
  render: h => h(App)
});
