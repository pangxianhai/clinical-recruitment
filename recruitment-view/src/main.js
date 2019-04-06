import Vue from 'vue';
import Vant from 'vant';
import router from './router/Index';
import Vuex from 'vuex'
import 'vant/lib/index.css';
import App from './App.vue';
import components from './components/Index'
import {UserConstants} from '@/constants/Global'

Vue.config.productionTip = false;
Vue.use(Vant);
Vue.use(Vuex);
Vue.prototype.UserConstants = UserConstants;

components.map(component => {
  Vue.component(component.name, component);
});

new Vue({
  el: '#app',
  router: router,
  render: h => h(App)
});
