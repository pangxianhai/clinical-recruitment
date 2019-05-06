import {ApiUtil} from '@/util/Util';

let PatientApi = {
  getPatient: async (params) => {
    return await ApiUtil.get('/patient', params);
  }
};

export default PatientApi;
