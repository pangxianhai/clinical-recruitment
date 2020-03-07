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
  getRecruitmentDepartment: async (recruitmentId) => {
    return await ApiUtil.get('/recruitment/' + recruitmentId + "/department",
        {});
  },
  getRecruitmentApplication: async (params) => {
    return await ApiUtil.get('/recruitment/application', params);
  },
  getRecruitmentApplicationById: async (applicationId) => {
    return await ApiUtil.get('/recruitment/application/' + applicationId, {});
  },
  recruitmentApplication: async (recruitmentId, params) => {
    return await ApiUtil.post('/recruitment/' + recruitmentId + '/application',
        params);
  }
}
