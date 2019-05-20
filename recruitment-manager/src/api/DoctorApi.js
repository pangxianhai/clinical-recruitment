import {ApiUtil} from '@/util/Util';

let DoctorApi = {
  getDoctor: async (params) => {
    return await ApiUtil.get('/doctor', params);
  },
  addDoctor: async (params) => {
    return await ApiUtil.post('/doctor', params);
  },
  getDoctorById: async (doctorId) => {
    return await ApiUtil.get('/doctor/' + doctorId);
  },
  updateDoctor: async (doctorId, doctorInfo) => {
    return await ApiUtil.put('/doctor/' + doctorId, doctorInfo);
  }
};

export default DoctorApi;
