import Vue from 'vue';
import Router from 'vue-router';
import UserApi from '@/api/AdminApi'

Vue.use(Router);

const router = new Router({
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
      path: '/login',
      component: () => import("@/views/administrator/Login"),
      menu: false,
      index: "1"
    },
    {
      path: '/recruitment',
      component: () => import("@/views/Layout"),
      icon: 'iconfont icon-xiangmuguanli',
      menu: true,
      name: '项目管理',
      index: "2",
      children: [
        {
          path: 'list',
          name: '项目列表',
          meta: {
            needLogin: true
          },
          menu: true,
          index: "2-1",
          component: () => import("@/views/recruitment/List"),
        },
        {
          path: 'add',
          name: '添加项目',
          meta: {
            needLogin: true
          },
          menu: true,
          index: "2-2",
          component: () => import("@/views/recruitment/Add"),
        },
        {
          path: 'detail/:recruitmentId',
          name: '项目详情',
          meta: {
            needLogin: true
          },
          menu: false,
          index: "2-1",
          component: () => import("@/views/recruitment/Detail"),
        },
        {
          path: 'update/:recruitmentId',
          name: '编辑项目',
          meta: {
            needLogin: true
          },
          menu: false,
          index: "2-1",
          component: () => import("@/views/recruitment/Update"),
        },
        {
          path: 'applicationList',
          name: '申请记录',
          meta: {
            needLogin: true
          },
          menu: true,
          index: "2-3",
          component: () => import("@/views/recruitment/ApplicationList"),
        },
        {
          path: 'applicationDetail/:recruitmentApplicationId',
          name: '申请记录',
          meta: {
            needLogin: true
          },
          menu: false,
          index: "2-3",
          component: () => import("@/views/recruitment/ApplicationDetail"),
        },
      ]
    },
    {
      path: '/organization',
      component: () => import("@/views/Layout"),
      icon: 'iconfont icon-yisheng',
      menu: true,
      name: '机构管理',
      index: "6",
      children: [
        {
          path: 'list',
          name: '机构列表',
          meta: {
            needLogin: true
          },
          menu: true,
          index: "6-1",
          component: () => import("@/views/organization/List"),
        },
        {
          path: 'add',
          name: '添加机构',
          meta: {
            needLogin: true
          },
          menu: true,
          index: "6-2",
          component: () => import("@/views/organization/Add"),
        },
        {
          path: 'department/list',
          name: '科室列表',
          meta: {
            needLogin: true
          },
          menu: true,
          index: "6-3",
          component: () => import("@/views/organization/department/List"),
        },
        {
          path: 'department/add',
          name: '添加科室',
          meta: {
            needLogin: true
          },
          menu: true,
          index: "6-4",
          component: () => import("@/views/organization/department/Add"),
        },
      ]
    },
    {
      path: '/researcher',
      component: () => import("@/views/Layout"),
      icon: 'iconfont icon-yisheng',
      menu: true,
      name: '研究员管理',
      index: "3",
      children: [
        {
          path: 'list',
          name: '研究员列表',
          meta: {
            needLogin: true
          },
          menu: true,
          index: "3-1",
          component: () => import("@/views/researcher/List"),
        },
        {
          path: 'add',
          name: '添加研究员',
          meta: {
            needLogin: true
          },
          menu: true,
          index: "3-2",
          component: () => import("@/views/researcher/Add"),
        },
        {
          path: 'update/:researcherId',
          name: '更新研究员',
          meta: {
            needLogin: true
          },
          menu: false,
          index: "3-1",
          component: () => import("@/views/researcher/Update"),
        },
      ]
    },
    {
      path: '/patient',
      component: () => import("@/views/Layout"),
      icon: 'iconfont icon-huanzhe',
      menu: true,
      name: '患者管理',
      index: "4",
      children: [
        {
          path: 'list',
          name: '患者列表',
          meta: {
            needLogin: true
          },
          menu: true,
          index: "4-1",
          component: () => import("@/views/patient/List"),
        },
        {
          path: 'add',
          name: '添加患者',
          meta: {
            needLogin: true
          },
          menu: true,
          index: "4-2",
          component: () => import("@/views/patient/Add"),
        },
        {
          path: 'update/:patientId',
          name: '更新患者',
          meta: {
            needLogin: true
          },
          menu: false,
          index: "4-1",
          component: () => import("@/views/patient/Update"),
        },
      ]
    },
    {
      path: '/administrator',
      component: () => import("@/views/Layout"),
      icon: 'iconfont icon-shezhi',
      menu: true,
      name: '管理员',
      index: "5",
      children: [
        {
          path: 'list',
          name: '管理员列表',
          meta: {
            needLogin: true
          },
          menu: true,
          index: "5-1",
          component: () => import("@/views/administrator/List"),
        },
        {
          path: 'add',
          name: '添加管理员',
          meta: {
            needLogin: true
          },
          menu: true,
          index: "5-2",
          component: () => import("@/views/administrator/Add"),
        },
        {
          path: 'update/:userId',
          name: '更新管理员',
          meta: {
            needLogin: true
          },
          menu: false,
          index: "5-1",
          component: () => import("@/views/administrator/Update"),
        },
        {
          path: 'updatePassword',
          name: '修改密码',
          meta: {
            needLogin: true
          },
          menu: true,
          index: "5-3",
          component: () => import("@/views/administrator/UpdatePassword"),
        }
      ]
    }
  ]
});

router.beforeEach((to, from, next) => {
  let needLogin = to.meta.needLogin;
  let isLogin = UserApi.isLogin();
  let isLoginPage = '/login' === to.path;
  if (!needLogin) {
    if (isLogin && isLoginPage) {
      toRedirectURL(to, next);
    } else {
      next();
    }
  } else {
    if (isLogin) {
      if (isLoginPage) {
        toRedirectURL(to, next);
      } else {
        next();
      }
    } else {
      if (isLoginPage) {
        next();
      } else {
        toLogin(to, next);
      }
    }
  }
});

function toRedirectURL(to, next) {
  let redirectURL = to.query.redirectURL;
  if (typeof redirectURL === 'undefined' || redirectURL.length <= 0) {
    redirectURL = '/recruitment/list';
  }
  next(redirectURL);
}

function toLogin(to, next) {
  next({
    path: '/login',
    query: {
      redirectURL: to.fullPath
    }
  });
}

export default router;
