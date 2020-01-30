import {ApiUtil, CookieUtil} from '@/util/Util';

let AdminApi = {

  data: {
    loginInfo: undefined
  },

  getLogInfo: async () => {
    if (typeof AdminApi.data.userInfo === 'undefined') {
      await ApiUtil.get('/administrator/loginInfo', {}).then((loginInfo) => {
        if (typeof loginInfo === 'undefined') {
          //获取不到登录信息则可能令牌失效 删除令牌
          AdminApi.logOut();
        }
        AdminApi.data.loginInfo = loginInfo;
        return AdminApi.data;
      });
    }
    return AdminApi.data.loginInfo;
  },
  isLogin: function () {
    let userName = CookieUtil.getCookie('userName');
    let token = CookieUtil.getCookie('token');

    let userNameNotEmpty = typeof userName !== 'undefined' && null != userName
        && userName.length > 0;
    let tokenNotEmpty = typeof token !== 'undefined' && null != token
        && token.length > 0;
    return userNameNotEmpty && tokenNotEmpty;
  },
  saveLoginInfo: function (userName, token) {
    CookieUtil.setCookie('userName', userName);
    CookieUtil.setCookie('token', token);
  },

  logOut: function () {
    CookieUtil.deleteCookie('userName');
    CookieUtil.deleteCookie('token');
  },
  manageLogin: async (params) => {
    return await ApiUtil.post('/administrator/login', params);
  },
  getManager: async (params) => {
    return await ApiUtil.get('/user/administrator', params);
  },
  addManager: async (params) => {
    return await ApiUtil.post('/user/administrator', params);
  },
  getManagerByUserId: async (userId) => {
    return await ApiUtil.get('/user/administrator/' + userId);
  },
  updateManager: async (userId, manageInfo) => {
    return await ApiUtil.put('/user/administrator/' + userId, manageInfo);
  },
  updateManagerPassword: async (userInfo) => {
    return await ApiUtil.put('/user/password', userInfo);
  },
  freezeUser: async (userId) => {
    return await ApiUtil.put('/user/status/' + userId + "/freeze");
  },
  unfreezeUser: async (userId) => {
    return await ApiUtil.put('/user/status/' + userId + "/unfreeze");
  },
  getUserByPhone: async (phone) => {
    return await ApiUtil.get('/user/phone/', {
      phone: phone
    });
  }
}

export default AdminApi;
