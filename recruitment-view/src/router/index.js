import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: __dirname,
  routes: [
    {
      path: '/a',
      component: (resolve) => require(['../views/a/a'], resolve)
    },
    {
      path: '/recruitment',
      component: (resolve) => require(['../views/recruitment/layout'], resolve),
      children: [
        {
          path: 'list',
          component: (resolve) => require(['../views/recruitment/list'],
            resolve),
        }
      ]
    }
  ]
});
