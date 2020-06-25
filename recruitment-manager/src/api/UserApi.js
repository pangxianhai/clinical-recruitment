import {ApiUtil, CookieUtil} from '@/util/Util';

let UserApi = {
  data: {
    loginInfo: undefined
  },

  getUserByPhone: async (phone) => {
    return await ApiUtil.get('/user/phone/' + phone, {});
  },

  getLogInfo: async () => {
    if (typeof UserApi.data.loginInfo === 'undefined') {
      await ApiUtil.get('/user/current/info', {}).then((loginInfo) => {
        if (typeof loginInfo === 'undefined') {
          //获取不到登录信息则可能令牌失效 删除令牌
          UserApi.logOut();
        }
        UserApi.data.loginInfo = loginInfo;
        return UserApi.data;
      });
    }
    return UserApi.data.loginInfo;
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

  updatePassword: async (passwordInfo) => {
    return await ApiUtil.put('/user/current/updatePassword', passwordInfo);
  }
};

export default UserApi;