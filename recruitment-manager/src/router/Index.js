import Vue from 'vue';
import Router from 'vue-router';
import UserApi from '@/api/UserApi'

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
      component: () => import("@/views/manager/Login"),
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
      ]
    },
    {
      path: '/doctor',
      component: () => import("@/views/Layout"),
      icon: 'iconfont icon-yisheng',
      menu: true,
      name: '医生管理',
      index: "3",
      children: [
        {
          path: 'list',
          name: '医生列表',
          meta: {
            needLogin: true
          },
          menu: true,
          index: "3-1",
          component: () => import("@/views/doctor/List"),
        },
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
