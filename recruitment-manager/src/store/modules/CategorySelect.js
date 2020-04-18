import CategoryApi from '@/api/CategoryApi';
import {CollectionUtil} from '@/util/Util'

const state = {
  categoryInfoList: [],
  categoryChild: {}
};

const getters = {
  categoryList: (state) => {
    return state.categoryInfoList;
  }
};

const actions = {
  initCategoryInfoList: async ({commit}, payload) => {
    let categoryList = await methods.getCategoryChildren(0);

    if (CollectionUtil.isEmpty(payload.parentIds)) {
      commit('setCategoryInfo', categoryList);
      return;
    }
    let categoryListTmp = categoryList;
    for (let i = 0; i < payload.parentIds.length; ++i) {
      let parentId = payload.parentIds[i];
      let categoryItem = methods.findCategoryItem(categoryListTmp, parentId);
      if (typeof categoryItem === 'undefined' || categoryItem.leaf) {
        break;
      }
      categoryItem.children = await methods.getCategoryChildren(parentId);
      categoryListTmp = categoryItem.children;
    }
    commit('setCategoryInfo', categoryList);
  },
  getCategoryChildren: async ({state, commit}, payload) => {
    const parentId = payload.parentId;
    let categoryChildren = state.categoryChild[parentId];
    if (typeof categoryChildren !== 'undefined') {
      return categoryChildren;
    }
    categoryChildren = await methods.getCategoryChildren(parentId);
    commit('setCategoryChild', parentId, categoryChildren);
    return categoryChildren;
  }
};

const mutations = {
  setCategoryInfo(state, categoryInfoList) {
    state.categoryInfoList = categoryInfoList
  },
  setCategoryChild(state, parentId, categoryChildren) {
    state.categoryChild[parentId] = categoryChildren;
  }
};

const methods = {
  findCategoryItem(categoryList, parentId) {
    if (CollectionUtil.isEmpty(categoryList)) {
      return;
    }
    for (let i = 0; i < categoryList.length; ++i) {
      let categoryItem = categoryList[i];
      if (categoryItem.value === parentId) {
        return categoryItem;
      }
    }
  },
  getCategoryChildren: async (parentId) => {
    let categoryListTmp = [];
    await CategoryApi.getCategoryByParentId(parentId).then(categoryList => {
      for (let i = 0; i < categoryList.length; ++i) {
        let categoryItem = categoryList[i];
        categoryListTmp.push({
          label: categoryItem.categoryName,
          value: categoryItem.categoryId,
          leaf: !categoryItem.hasNexLevel
        });
      }
    });
    return categoryListTmp;
  }
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
}