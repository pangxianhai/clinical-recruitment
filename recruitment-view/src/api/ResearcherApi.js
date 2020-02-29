import {ApiUtil} from '@/util/Util';

let ResearcherApi = {
  registerResearcher: async (params) => {
    return await ApiUtil.post('/researcher/register', params);
  },
};
export default ResearcherApi;