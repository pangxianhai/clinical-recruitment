import {ApiUtil} from '@/util/Util';

let PatientApi = {
  getPatient: async (params) => {
    return await ApiUtil.get('/patient', params);
  },
  addPatient: async (params) => {
    return await ApiUtil.post('/patient', params);
  },
  getPatientById: async (patientId) => {
    return await ApiUtil.get('/patient/' + patientId);
  }
};
export default PatientApi;
