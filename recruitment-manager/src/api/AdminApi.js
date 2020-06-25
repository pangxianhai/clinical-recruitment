import {ApiUtil} from '@/util/Util';

let AdminApi = {

  adminLogin: async (params) => {
    return await ApiUtil.post('/administrator/login', params);
  },
  getAdmin: async (params) => {
    return await ApiUtil.get('/administrator/', params);
  },
  getAdminById: async (adminId) => {
    return await ApiUtil.get('/administrator/' + adminId);
  },
  freezeAdmin: async (adminId) => {
    return await ApiUtil.put('/administrator/' + adminId + "/freeze");
  },
  unfreezeAdmin: async (adminId) => {
    return await ApiUtil.put('/administrator/' + adminId + "/unfreeze");
  },

  addAdmin: async (params) => {
    return await ApiUtil.post('/administrator/', params);
  },
  updateAdmin: async (adminId, adminInfo) => {
    return await ApiUtil.put('/administrator/' + adminId, adminInfo);
  }
};

export default AdminApi;
