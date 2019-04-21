import {ApiUtil} from '../util/Util';

export default {
  getRecruitment: async (params) => {
    return await ApiUtil.get('/recruitment', params);
  },
  getRecruitmentById: async (recruitmentId) => {
    return await ApiUtil.get('/recruitment/' + recruitmentId, {});
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
