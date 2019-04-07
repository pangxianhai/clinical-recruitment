import {ApiUtil} from '../util/Util';

export default {
  getRecruitment: async (params) => {
    return await ApiUtil.get('/recruitment', params);
  },
  getRecruitmentById: async (recruitmentId) => {
    return await ApiUtil.get('/recruitment/' + recruitmentId, {});
  },
  getRecruitmentApplication: async (params) => {
    return await ApiUtil.get('/recruitmentapplication', params);
  },
  recruitmentApplication: async (params) => {
    return await ApiUtil.post('/recruitmentapplication', params);
  }
}
