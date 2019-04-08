import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

import DoctorLayout from '@/views/doctor/Layout';
import DoctorRegister from '@/views/doctor/Register';
import DoctorInfo from '@/views/doctor/DoctorInfo';
import SiteLayout from '@/views/site/Layout';
import SiteContactUs from '@/views/site/ContactUs';
import UserLayout from '@/views/user/Layout';
import UserLogin from '@/views/user/Login';
import UserWxLogin from '@/views/user/WxLogin';
import RecruitmentLayout from '@/views/recruitment/Layout';
import RecruitmentList from '@/views/recruitment/List';
import RecruitmentApplicationList from '@/views/recruitment/ApplicationList';
import RecruitmentApplication from '@/views/recruitment/Application';
import PatientLayout from '@/views/patient/Layout';
import PatientInfo from '@/views/patient/PatientInfo';
import PatientRegister from '@/views/patient/Register';

export default new Router({
  mode: 'history',
  base: __dirname,
  routes: [
    {
      path: '/recruitment',
      component: RecruitmentLayout,
      children: [
        {
          path: 'list',
          component: RecruitmentList,
        },
        {
          path: 'applicationList',
          component: RecruitmentApplicationList,
        },
        {
          path: 'application',
          component: RecruitmentApplication,
        }
      ]
    },
    {
      path: '/patient',
      component: PatientLayout,
      children: [
        {
          path: 'info',
          component: PatientInfo,
        },
        {
          path: 'register',
          component: PatientRegister,
        }
      ]
    },
    {
      path: '/user',
      component: UserLayout,
      children: [
        {
          path: 'login',
          component: UserLogin,
        },
        {
          path: 'wxlogin',
          component: UserWxLogin,
        },
      ]
    },
    {
      path: '/site',
      component: SiteLayout,
      children: [
        {
          path: 'contactUs',
          component: SiteContactUs
        },
      ]
    },
    {
      path: '/doctor',
      component: DoctorLayout,
      children: [
        {
          path: 'register',
          component: DoctorRegister
        },
        {
          path: 'info',
          component: DoctorInfo
        }
      ]
    }
  ]
});
