import {ApiUtil} from '../util/Util';

export default {
  getRecruitment: async (params) => {
    return await ApiUtil.get('/recruitment', params);
  },
}
