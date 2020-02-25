import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: __dirname,
  routes: [
    {
      path: '/',
      redirect: '/recruitment/list',
      menu: false,
      index: "0"
    },
    {
      path: '/recruitment',
      component: () => import("@/views/recruitment/Layout"),
      children: [
        {
          path: 'list',
          component: () => import("@/views/recruitment/List"),
        },
        {
          path: 'detail/:recruitmentId',
          component: () => import("@/views/recruitment/Detail"),
        },
        {
          path: 'applicationList',
          component: () => import("@/views/recruitment/ApplicationList"),
        },
        {
          path: ':recruitmentId/application/',
          component: () => import("@/views/recruitment/Application"),
        },
        {
          path: 'diseaseApplication',
          component: () => import("@/views/recruitment/DiseaseApplication"),
        },
        {
          path: 'application/detail/:applicationId',
          component: () => import("@/views/recruitment/ApplicationDetail"),
        }
      ]
    },
    {
      path: '/patient',
      component: () => import("@/views/patient/Layout"),
      children: [
        {
          path: 'info',
          component: () => import("@/views/patient/PatientInfo"),
        },
        {
          path: 'register',
          component: () => import("@/views/patient/Register"),
        }
      ]
    },
    {
      path: '/user',
      component: () => import("@/views/user/Layout"),
      children: [
        {
          path: 'login',
          component: () => import("@/views/user/Login"),
        },
        {
          path: 'wxlogin',
          component: () => import("@/views/user/WxLogin"),
        },
        {
          path: 'me',
          component: () => import("@/views/user/Me"),
        }
      ]
    },
    {
      path: '/site',
      component: () => import("@/views/site/Layout"),
      children: [
        {
          path: 'contactUs',
          component: () => import("@/views/site/ContactUs")
        },
        {
          path: 'serviceAgreement',
          component: () => import("@/views/site/ServiceAgreement")
        },
      ]
    },
    {
      path: '/reference',
      component: () => import("@/views/reference/Layout"),
      children: [
        {
          path: 'register',
          component: () => import("@/views/reference/Register")
        }
      ]
    }
  ]
});
