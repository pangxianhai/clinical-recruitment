import {ApiUtil} from '@/util/Util';

export default {
  uploadFile: async (params) => {
    return await ApiUtil.post('/oss', params);
  }
}
