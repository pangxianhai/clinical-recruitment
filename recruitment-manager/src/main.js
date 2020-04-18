import Vue from 'vue'
import App from './App.vue'
import router from './router/Index'
import './element-variables.scss'
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import ElementUI from 'element-ui';
import Vuex from 'vuex'
import store from './store'
// import CategorySelect from './components/CategorySelect'

Vue.use(ElementUI);
Vue.use(VueQuillEditor);
Vue.use(Vuex);

// Vue.component(CategorySelect.name, CategorySelect);

Vue.config.productionTip = false;

new Vue({
  router: router,
  store,
  render: h => h(App),
}).$mount('#app');
