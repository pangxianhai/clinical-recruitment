import {ApiUtil} from '../util/Util';

let PatientApi = {
  data: {
    patientInfo: undefined
  },
  getCurrentPatientInfo: async () => {
    if (typeof PatientApi.data.patientInfo === 'undefined') {
      await ApiUtil.get('/patient/currentInfo', {})
      .then((patientInfo) => {
        PatientApi.data.patientInfo = patientInfo;
        return patientInfo;
      });
    }
    return PatientApi.data.patientInfo;
  },
  registerPatient: async (params) => {
    return await ApiUtil.post('/patient/register', params);
  }
}

export default PatientApi;
