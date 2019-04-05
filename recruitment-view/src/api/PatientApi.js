import {ApiUtil} from '../util/Util';

export default {
  getCurrentPatientInfo: async () => {
    return await ApiUtil.get('/patient/currentInfo', {});
  }
}
