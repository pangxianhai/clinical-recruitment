import {ApiUtil} from '@/util/Util';

let ResearcherApi = {
  addResearcher: async (param) => {
    return await ApiUtil.post('/researcher', param);
  },
  getResearcher: async (param) => {
    return await ApiUtil.get('/researcher', param);
  }
}

export default ResearcherApi;