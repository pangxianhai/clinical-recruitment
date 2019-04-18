import {ApiUtil} from '../util/Util';

let PatientApi = {
  data: {
    patientInfo: undefined
  },
  getCurrentPatientInfo: async () => {
    if (typeof  PatientApi.data.patientInfo === 'undefined') {
      return await ApiUtil.get('/patient/currentInfo', {})
      .then((patientInfo) => {
        PatientApi.data.patientInfo = patientInfo;
        return patientInfo;
      });
    } else {
      return PatientApi.data.patientInfo;
    }
  },
  registerPatient: async (params) => {
    return await ApiUtil.post('/patient', params);
  }
}

export default PatientApi;
