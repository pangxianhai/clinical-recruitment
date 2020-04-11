import {ApiUtil} from '@/util/Util';

let HospitalApi = {
  getHospital: async (params) => {
    return await ApiUtil.get('/hospital', params);
  },
  addHospital: async (params) => {
    return await ApiUtil.post('/hospital', params);
  },
  updateHospital: async (hospitalId, hospitalInfo) => {
    return await ApiUtil.put('/hospital/' + hospitalId, hospitalInfo);
  }
};
export default HospitalApi;