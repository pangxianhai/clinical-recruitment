import {ApiUtil} from '@/util/Util';

let DepartmentApi = {
  getDepartment: async (params) => {
    return await ApiUtil.get('/hospital/department', params);
  },
  getDepartmentByHid: async (hid) => {
    return await ApiUtil.get('/hospital/' + hid + '/department', {});
  },
  addDepartment: async (params) => {
    return await ApiUtil.post('/hospital/department', params);
  }
};
export default DepartmentApi;