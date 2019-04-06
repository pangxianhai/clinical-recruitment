import {ApiUtil} from '../util/Util';

export default {
  getCurrentPatientInfo: async () => {
    return await ApiUtil.get('/patient/currentInfo', {});
  },
  registerPatient: async (params) => {
    return await ApiUtil.post('/patient/register', params);
  }
}
