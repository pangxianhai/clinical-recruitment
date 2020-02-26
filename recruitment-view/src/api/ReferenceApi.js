import {ApiUtil} from '@/util/Util';

let ReferenceApi = {
  registerReference: async (params) => {
    return await ApiUtil.post('/reference/register', params);
  },

};

export default ReferenceApi;