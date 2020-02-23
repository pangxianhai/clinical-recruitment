import {ApiUtil, CookieUtil, StringUtil} from '@/util/Util';
import {CookieNameConstant} from '@/constants/Global';

let UserApi = {

  data: {
    userInfo: undefined
  },

  getLogInfo: async () => {
    if (typeof UserApi.data.userInfo === 'undefined') {
      await ApiUtil.get('/user/current', {}).then((userInfo) => {
        UserApi.data.userInfo = userInfo;
        return UserApi.data.userInfo;
      });
    }
    return UserApi.data.userInfo;
  },
  getCurrentUserInfo: async () => {
    return await ApiUtil.get('/user/current', {});
  },
  getWxLoginUrl: async (redirectURL) => {
    return await ApiUtil.get('/user/login/wx', {
      redirectUrl: redirectURL
    });
  },
  wxLogin: async (code) => {
    return await ApiUtil.post('/user/login/wx', {
      code: code
    });
  },
  isLogin: function () {
    let userName = CookieUtil.getCookie(CookieNameConstant.USER_NAME);
    let token = CookieUtil.getCookie(CookieNameConstant.TOKEN_NAME);
    return StringUtil.isNotEmpty(userName) && StringUtil.isNotEmpty(token);
  },
  saveLoginInfo: function (loginInfo) {
    CookieUtil.setCookie(CookieNameConstant.USER_NAME,
        String(loginInfo.userName));
    CookieUtil.setCookie(CookieNameConstant.TOKEN_NAME,
        String(loginInfo.token));
  },
  logout: function () {
    CookieUtil.deleteCookie(CookieNameConstant.USER_NAME);
    CookieUtil.deleteCookie(CookieNameConstant.TOKEN_NAME);
  }
};

export default UserApi;
