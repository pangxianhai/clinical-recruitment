import {ApiUtil} from '../util/Util';

export default {
  getRecruitment: async (params) => {
    return await ApiUtil.get('/recruitment', params);
  },
  getRecruitmentById: async (recruitmentId) => {
    return await ApiUtil.get('/recruitment/' + recruitmentId, {});
  },
  addRecruitment: async (params) => {
    return await ApiUtil.post('/recruitment', params);
  },
  getRecruitmentCenterById: async (recruitmentId) => {
    return await ApiUtil.get('/recruitment/' + recruitmentId + "/center", {});
  },
  getRecruitmentApplication: async (params) => {
    return await ApiUtil.get('/recruitmentapplication', params);
  },
  getRecruitmentApplicationById: async (applicationId) => {
    return await ApiUtil.get('/recruitmentapplication/' + applicationId, {});
  }
}
