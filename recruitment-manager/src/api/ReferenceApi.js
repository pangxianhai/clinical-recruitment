import {ApiUtil} from '@/util/Util';

let ReferenceApi = {
  getReference: async (params) => {
    return await ApiUtil.get('/reference', params);
  },
  getReferenceDetail: async (referenceId) => {
    return await ApiUtil.get('/reference/' + referenceId);
  },
  addReference: async (params) => {
    return await ApiUtil.post('/reference', params);
  },
  updateReference: async (referenceId, params) => {
    return await ApiUtil.put('/reference/' + referenceId, params);
  },
  updateReferenceStatus: async (referenceId, status) => {
    return await ApiUtil.put('/reference/' + referenceId + '/status/' + status,
        {});
  }
};

export default ReferenceApi;