import {ApiUtil} from '@/util/Util';

let OrganizationApi = {
  getOrganization: async (params) => {
    return await ApiUtil.get('/organization', params);
  },
  addOrganization: async (params) => {
    return await ApiUtil.post('/organization', params);
  },
  updateOrganization: async (organizationId, patientInfo) => {
    return await ApiUtil.put('/organization/' + organizationId, patientInfo);
  }
};
export default OrganizationApi;