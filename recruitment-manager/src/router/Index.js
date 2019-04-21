import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: __dirname,
  routes: [
    {
      path: '/manager',
      component: () => import("@/views/manager/Layout"),
      children: [
        {
          path: 'login',
          component: () => import("@/views/manager/Login"),
        }
      ]
    }
  ]
});
