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
      menu: false
    },
    {
      path: '/login',
      component: () => import("@/views/manager/Login"),
      menu: false
    },
    {
      path: '/recruitment',
      component: () => import("@/views/Layout"),
      icon: 'el-icon-document',
      menu: true,
      name: '项目管理',
      children: [
        {
          path: 'list',
          name: '项目列表',
          meta: {
            needLogin: true
          },
          menu: true,
          component: () => import("@/views/recruitment/List"),
        },
        {
          path: 'add',
          name: '添加项目',
          meta: {
            needLogin: true
          },
          menu: true,
          component: () => import("@/views/recruitment/Add"),
        },
      ]
    },
  ]
});

router.beforeEach((to, from, next) => {
  let needLogin = to.meta.needLogin;
  let isLogin = UserApi.isLogin();
  let isLoginPage = '/login' === to.path;
  if (isLoginPage && isLogin) {
    let redirectURL = to.query.redirectURL;
    if (typeof redirectURL === 'undefined' || redirectURL.length <= 0) {
      redirectURL = '/recruitment/list';
    }
    next(redirectURL);
  } else {
    if ((needLogin && isLogin) || isLoginPage) {
      next();
    } else {
      next({
        path: '/login',
        query: {
          redirectURL: to.fullPath
        }
      });
    }
  }
});

export default router;
