import {ApiUtil} from '../util/Util';

export default {
  getRecruitment: async (params) => {
    return await ApiUtil.get('/recruitment', params);
  },
  getRecruitmentApplication: async (params) => {
    return await ApiUtil.get('/recruitmentapplication', params);
  }
}
