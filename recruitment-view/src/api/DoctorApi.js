import {ApiUtil} from '@/util/Util';

let DoctorApi = {
  data: {
    doctorInfo: undefined
  },

  registerDoctor: async (params) => {
    return await ApiUtil.post('/doctor', params);
  },
  getCurrentDoctorInfo: async () => {
    if (typeof  DoctorApi.data.doctorInfo === 'undefined') {
      return await ApiUtil.get('/doctor/currentInfo', {})
      .then((doctorInfo) => {
        DoctorApi.data.doctorInfo = doctorInfo;
        return doctorInfo;
      });
    } else {
      return DoctorApi.data.doctorInfo;
    }
  },
}

export default DoctorApi;
