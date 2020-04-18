import {ApiUtil} from '@/util/Util';

let CategoryApi = {
  getCategory: async (params) => {
    return await ApiUtil.get('/category', params);
  },
  getCategoryByName: async (name) => {
    return await ApiUtil.get('/category/getByName', {
      name: name
    });
  },
  getCategoryByParentId: async (parentId) => {
    return await ApiUtil.get('/category/' + parentId + '/children');
  },
  getCategoryById: async (id) => {
    return await ApiUtil.get('/category/' + id, {});
  },
  addCategory: async (params) => {
    return await ApiUtil.post('/category', params);
  },
  updateCategory: async (categoryId, params) => {
    return await ApiUtil.put('/category/' + categoryId, params);
  }
};

export default CategoryApi;