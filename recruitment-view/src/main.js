import Vue from 'vue';
import Vuex from 'vuex'
import App from './App.vue';
import router from './router/Index';
import AddressSelect from "@/components/AddressSelect";
import Footer from '@/components/Footer';
import {
  NavBar,
  Button,
  List,
  Icon,
  Search,
  Row,
  Col,
  Popup,
  Swipe,
  SwipeItem,
  Panel,
  Tabbar,
  TabbarItem,
  Area,
  Tag,
  Tab,
  Tabs,
  Cell,
  Divider,
  Grid,
  GridItem,
  GoodsAction,
  GoodsActionIcon,
  GoodsActionButton,
  Image,
  CellGroup,
  Uploader,
  Field,
  Picker,
  Checkbox,
  CheckboxGroup,
  ImagePreview,
  Notify,
  Toast
} from 'vant';

Vue.config.productionTip = false;

Vue.use(Vuex);
Vue.use(NavBar);
Vue.use(Button);
Vue.use(List);
Vue.use(Icon);
Vue.use(Search);
Vue.use(Row);
Vue.use(Col);
Vue.use(Popup);
Vue.use(Swipe);
Vue.use(SwipeItem);
Vue.use(Panel);
Vue.use(Tabbar);
Vue.use(TabbarItem);
Vue.use(Area);
Vue.use(Tag);
Vue.use(Tab);
Vue.use(Tabs);
Vue.use(Cell);
Vue.use(Divider);
Vue.use(Grid);
Vue.use(GridItem);
Vue.use(GoodsAction);
Vue.use(GoodsActionIcon);
Vue.use(GoodsActionButton);
Vue.use(Image);
Vue.use(CellGroup);
Vue.use(Uploader);
Vue.use(Field);
Vue.use(Picker);
Vue.use(Checkbox);
Vue.use(CheckboxGroup);
Vue.use(ImagePreview);
Vue.use(Notify);
Vue.use(Toast);

Vue.component(AddressSelect.name, AddressSelect);
Vue.component(Footer.name, Footer);

new Vue({
  el: '#app',
  router: router,
  render: h => h(App)
});
