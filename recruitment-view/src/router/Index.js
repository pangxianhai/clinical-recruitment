import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: __dirname,
  routes: [
    {
      path: '/recruitment',
      component: (resolve) => require(['../views/recruitment/Layout'], resolve),
      children: [
        {
          path: 'list',
          component: (resolve) => require(['../views/recruitment/List'],
              resolve),
        }
      ]
    }, {
      path: '/patient',
      component: (resolve) => require(['../views/patient/Layout'], resolve),
      children: [
        {
          path: 'info',
          component: (resolve) => require(['../views/patient/PatientInfo'],
              resolve),
        }
      ]
    }
  ]
});
