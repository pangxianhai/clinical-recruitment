import {ApiUtil} from '@/util/Util';

export default {
  registerDoctor: async (params) => {
    return await ApiUtil.post('/doctor', params);
  },
  getCurrentDoctorInfo: async () => {
    return await ApiUtil.get('/doctor/currentInfo', {});
  },
}
