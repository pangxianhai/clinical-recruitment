import Vue from 'vue'
import App from './App.vue'
import router from './router/Index'
import './element-variables.scss'
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import ElementUI from 'element-ui';

Vue.use(ElementUI);

Vue.use(VueQuillEditor);

Vue.config.productionTip = false;

new Vue({
  router: router,
  render: h => h(App),
}).$mount('#app');
