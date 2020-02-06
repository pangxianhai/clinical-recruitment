import Vue from 'vue'
import App from './App.vue'
import router from './router/Index'
import './element-variables.scss'
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

import {
  Breadcrumb,
  BreadcrumbItem,
  Form,
  FormItem,
  Input,
  InputNumber,
  Cascader,
  RadioGroup,
  Radio,
  Button,
  Icon,
  Message,
  Table,
  TableColumn,
  Pagination,
  Tag,
  Select,
  Option,
  Tooltip,
  Row,
  Col,
  Container,
  Header,
  Aside,
  Main,
  Footer,
  Menu,
  Submenu,
  MenuItem,
  MenuItemGroup,
  RadioButton,
  Loading,
  DatePicker,
} from 'element-ui';

Vue.use(Breadcrumb);
Vue.use(BreadcrumbItem);
Vue.use(Form);
Vue.use(FormItem);
Vue.use(Input);
Vue.use(InputNumber);
Vue.use(Cascader);
Vue.use(RadioGroup);
Vue.use(Radio);
Vue.use(Button);
Vue.use(Icon);
Vue.use(Table);
Vue.use(TableColumn);
Vue.use(Pagination);
Vue.use(Tag);
Vue.use(Select);
Vue.use(Option);
Vue.use(Tooltip);
Vue.use(Row);
Vue.use(Col);
Vue.use(Container);
Vue.use(Header);
Vue.use(Aside);
Vue.use(Main);
Vue.use(Footer);
Vue.use(Menu);
Vue.use(Submenu);
Vue.use(MenuItem);
Vue.use(MenuItemGroup);
Vue.use(RadioButton);
Vue.use(DatePicker);
Vue.use(Loading.directive);
Vue.prototype.$message = Message;
Vue.prototype.$loading = Loading.service;

Vue.use(VueQuillEditor);

Vue.config.productionTip = false;

new Vue({
  router: router,
  render: h => h(App),
}).$mount('#app');
