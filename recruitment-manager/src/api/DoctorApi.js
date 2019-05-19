import {ApiUtil} from '@/util/Util';

let DoctorApi = {
  getDoctor: async (params) => {
    return await ApiUtil.get('/doctor', params);
  },
  addDoctor: async (params) => {
    return await ApiUtil.post('/doctor', params);
  }
};

export default DoctorApi;
