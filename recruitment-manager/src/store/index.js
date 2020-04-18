import Vue from 'vue'
import Vuex from 'vuex'
import CategorySelect from './modules/CategorySelect'

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    CategorySelect
  }
})