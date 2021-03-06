import {ApiUtil} from '../util/Util';

export default {
  getRecruitmentApplication: async (params) => {
    return await ApiUtil.get('/recruitmentapplication', params);
  },

  getRecruitmentApplicationDetail: async (applicationId) => {
    return await ApiUtil.get('/recruitmentapplication/' + applicationId, {});
  },

  updateApplicationStatus: async (applicationId, status) => {
    return await ApiUtil.patch('/recruitmentapplication/status/' + applicationId,
        {status: status});
  }
}
