import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: __dirname,
  routes: [
    {
      path: '/recruitment',
      component: (resolve) => require(["@/views/recruitment/Layout"], resolve),
      children: [
        {
          path: 'list',
          component: (resolve) => require(["@/views/recruitment/List"],
              resolve),
        },
        {
          path: 'applicationList',
          component: (resolve) => require(
              ["@/views/recruitment/ApplicationList"], resolve),
        },
        {
          path: 'application',
          component: (resolve) => require(["@/views/recruitment/Application"],
              resolve),
        }
      ]
    },
    {
      path: '/patient',
      component: (resolve) => require(["@/views/patient/Layout"], resolve),
      children: [
        {
          path: 'info',
          component: (resolve) => require(["@/views/patient/PatientInfo"],
              resolve),
        },
        {
          path: 'register',
          component: (resolve) => require(["@/views/patient/Register"],
              resolve),
        }
      ]
    },
    {
      path: '/user',
      component: (resolve) => require(["@/views/user/Layout"], resolve),
      children: [
        {
          path: 'login',
          component: (resolve) => require(["@/views/user/Login"], resolve),
        },
        {
          path: 'wxlogin',
          component: (resolve) => require(["@/views/user/WxLogin"], resolve),
        },
      ]
    },
    {
      path: '/site',
      component: (resolve) => require(["@/views/site/Layout"], resolve),
      children: [
        {
          path: 'contactUs',
          component: (resolve) => require(["@/views/site/ContactUs"], resolve)
        },
      ]
    },
    {
      path: '/doctor',
      component: (resolve) => require(["@/views/doctor/Layout"], resolve),
      children: [
        {
          path: 'register',
          component: (resolve) => require(["@/views/doctor/Register"], resolve),
        },
        {
          path: 'info',
          component: (resolve) => require(["@/views/doctor/DoctorInfo"],
              resolve),
        }
      ]
    }
  ]
});
