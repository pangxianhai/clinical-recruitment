import {ApiUtil} from '@/util/Util';

let PatientApi = {
  getPatient: async (params) => {
    return await ApiUtil.get('/patient', params);
  },
  addPatient: async (params) => {
    return await ApiUtil.post('/patient', params);
  }
};
export default PatientApi;
