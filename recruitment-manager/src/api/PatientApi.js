import {ApiUtil} from '@/util/Util';

let PatientApi = {
  getPatient: async (params) => {
    return await ApiUtil.get('/patient/', params);
  },
  addPatient: async (params) => {
    return await ApiUtil.post('/patient', params);
  },
  getPatientById: async (patientId) => {
    return await ApiUtil.get('/patient/' + patientId);
  },
  updatePatient: async (patientId, patientInfo) => {
    return await ApiUtil.put('/patient/' + patientId, patientInfo);
  },
  freezePatient: async (patientId) => {
    return await ApiUtil.put('/patient/' + patientId + '/freeze');
  },
  unfreezePatient: async (patientId) => {
    return await ApiUtil.put('/patient/' + patientId + '/unfreeze');
  }
};
export default PatientApi;
