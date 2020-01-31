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
  },
  getOrganizationDepartment: async (params) => {
    return await ApiUtil.get('/organization/department', params);
  },
  addOrganizationDepartment: async (params) => {
    return await ApiUtil.post('/organization/department', params);
  }

};
export default OrganizationApi;