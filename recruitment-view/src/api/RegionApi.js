import {ApiUtil} from '../util/Util';

export default {
  getRegionByParentId: async (parentId) => {
    if (typeof parentId === 'undefined') {
      return await ApiUtil.get('/region/parent', {});
    } else {
      return await ApiUtil.get('/region/parent/' + parentId, {});
    }
  },
}
