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
  updateRecruitment: async (recruitmentId, params) => {
    return await ApiUtil.put('/recruitment/' + recruitmentId, params);
  },
  getRecruitmentCenterById: async (recruitmentId) => {
    return await ApiUtil.get('/recruitment/' + recruitmentId + "/center", {});
  },
  getRecruitmentDepartmentById: async (recruitmentId) => {
    return await ApiUtil.get('/recruitment/' + recruitmentId + "/department",
        {});
  },
  getRecruitmentApplication: async (params) => {
    return await ApiUtil.get('/recruitmentapplication', params);
  },
  getRecruitmentApplicationById: async (applicationId) => {
    return await ApiUtil.get('/recruitmentapplication/' + applicationId, {});
  },
  recruitmentBegin: async (recruitmentId) => {
    return await ApiUtil.put('/recruitment/' + recruitmentId + "/begin", {});
  },
  recruitmentEnd: async (recruitmentId) => {
    return await ApiUtil.put('/recruitment/' + recruitmentId + "/end", {});
  }
}
