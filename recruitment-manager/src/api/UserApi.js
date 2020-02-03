import {ApiUtil} from '@/util/Util';

let UserApi = {
  getUserByPhone: async (phone) => {
    return await ApiUtil.get('/user/phone/' + phone, {});
  }
}

export default UserApi;