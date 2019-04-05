import {ApiUtil} from '../util/Util';

export default {
  getLogInfo: async () => {
    return await ApiUtil.get('/user/login', {});
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
  }
}
