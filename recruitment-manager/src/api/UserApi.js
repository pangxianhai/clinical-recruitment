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
  isLogin: function () {
    let userId = CookieUtil.getCookie('userId');
    return typeof userId !== 'undefined' && null != userId && userId.length > 0;
  },
  saveUserId: function (userId) {
    CookieUtil.setCookie('userId', userId + "");
  },
  logOut: function () {
    CookieUtil.deleteCookie('userId');
  },
  manageLogin: async (params) => {
    return await ApiUtil.post('/user/manager/login', params);
  },
  getManager: async (params) => {
    return await ApiUtil.get('/user/manager', params);
  },
  freezeUser: async (userId) => {
    return await ApiUtil.post('/user/status/' + userId + "/freeze");
  },
  unfreezeUser: async (userId) => {
    return await ApiUtil.post('/user/status/' + userId + "/unfreeze");
  },

}

export default UserApi;
