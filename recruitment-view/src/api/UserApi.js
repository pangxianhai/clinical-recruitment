import {ApiUtil, CookieUtil, StringUtil} from '@/util/Util';
import {CookieNameConstant} from '@/constants/Global';

let UserApi = {
  getCurrentUserInfo: async () => {
    return await ApiUtil.get('/user/current', {});
  },
  getUserInfoById: async (userId) => {
    return await ApiUtil.get('/user/' + userId, {});
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
  getUserId: function () {
    let userId = CookieUtil.getCookie(CookieNameConstant.USER_ID);
    if (StringUtil.isEmpty(userId)) {
      userId = '';
    }
    return userId;
  },
  isLogin: function () {
    let userName = CookieUtil.getCookie(CookieNameConstant.USER_NAME);
    let token = CookieUtil.getCookie(CookieNameConstant.TOKEN_NAME);
    let userId = CookieUtil.getCookie(CookieNameConstant.USER_ID);
    return StringUtil.isNotEmpty(userName) && StringUtil.isNotEmpty(token)
        && StringUtil.isNotEmpty(userId);
  },
  saveLoginInfo: function (loginInfo) {
    CookieUtil.setCookie(CookieNameConstant.USER_NAME,
        String(loginInfo.userName));
    CookieUtil.setCookie(CookieNameConstant.TOKEN_NAME,
        String(loginInfo.token));
    CookieUtil.setCookie(CookieNameConstant.USER_ID, loginInfo.userId);
  },
  logout: function () {
    CookieUtil.deleteCookie(CookieNameConstant.USER_NAME);
    CookieUtil.deleteCookie(CookieNameConstant.TOKEN_NAME);
    CookieUtil.deleteCookie(CookieNameConstant.USER_ID);
  }
};

export default UserApi;
