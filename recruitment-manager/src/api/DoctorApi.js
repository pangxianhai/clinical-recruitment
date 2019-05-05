import {ApiUtil} from '@/util/Util';

let DoctorApi = {
  getDoctor: async (params) => {
    return await ApiUtil.get('/doctor', params);
  }
};

export default DoctorApi;
