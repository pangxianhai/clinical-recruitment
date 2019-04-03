import {ApiUtil} from '../util/Util';

export default {
  getLoginfo: async () => {
    return await ApiUtil.get('/user/login/', {});
  },
}
