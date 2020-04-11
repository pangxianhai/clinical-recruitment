import {ApiUtil} from '@/util/Util';

let DepartmentApi = {
  getDepartment: async (params) => {
    return await ApiUtil.get('/hospital/department', params);
  },
  addDepartment: async (params) => {
    return await ApiUtil.post('/hospital/department', params);
  }
};
export default DepartmentApi;