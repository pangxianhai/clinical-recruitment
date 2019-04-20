import {ApiUtil, CookieUtil} from '@/util/Util';

let UserApi = {

  data: {
    userInfo: undefined
  },

  getLogInfo: async () => {
    if (typeof  UserApi.data.userInfo === 'undefined') {
      await ApiUtil.get('/user', {}).then((userInfo) => {
        UserApi.data.userInfo = userInfo;
        return UserApi.data;
      });
    }
    return UserApi.data.userInfo;
  },
  getWxLoginUrl: async (redirectURL) => {
    return await ApiUtil.get('/user/login/wx', {
      redirectURL: redirectURL
    });
  },
  wxLogin: async (code) => {
    return await ApiUtil.post('/user/login/wx', {
      code: code
    });
  },
  isLogin: function () {
    let userId = CookieUtil.getCookie('userId');
    return typeof userId !== 'undefined' && null != userId && userId.length
        > 0;
  },
  saveUserId: function (userId) {
    CookieUtil.setCookie('userId', userId + "");
  }
}

export default UserApi;
