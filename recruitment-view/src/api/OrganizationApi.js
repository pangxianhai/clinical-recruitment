import {ApiUtil} from '@/util/Util';

let OrganizationApi = {
  getOrganizationList: async (params) => {
    return await ApiUtil.get('/organization', params);
  },
  getDepartmentByOrganization: async (organizationId) => {
    return await ApiUtil.get('/organization/' + organizationId + '/department',
        {});
  }
};

export default OrganizationApi;