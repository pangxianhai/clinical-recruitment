<template>
    <div>
        <el-cascader style="width:100%"
                     v-model="currentValue"
                     :options="categoryInfoList"
                     :props="categoryProps"
                     :placeholder="placeholder"
                     @change="handleChange"
                     clearable filterable>
        </el-cascader>
    </div>
</template>

<script>
  import CategoryApi from '@/api/CategoryApi';
  import {CollectionUtil} from "../util/Util";

  export default {
    props: {
      value: Array,
      placeholder: String
    },
    data: function () {
      return {
        categoryInfoList: [],
        categoryChild: {},
        currentPlaceholder: this.placeholder,
        currentValue: this.value,
        categoryProps: {
          lazy: true,
          lazyLoad: this.loadCategoryLazy,
          checkStrictly: true
        }
      }
    },
    created() {
      this.initCategoryInfoList([]);
    },
    methods: {
      async initCategoryInfoList(parentIds) {
        let categoryInfoList = [];

        categoryInfoList = await this.getCategoryChildren(0);
       
        if (CollectionUtil.isEmpty(parentIds)) {
          this.categoryInfoList = categoryInfoList;
          return;
        }
        let categoryList = categoryInfoList;
        for (let i = 0; i < parentIds.length; ++i) {
          let parentId = parentIds[i];
          let categoryItem = this.findCategoryItem(categoryList, parentId);
          if (typeof categoryItem === 'undefined') {
            continue;
          }
          if (categoryItem.leaf) {
            continue;
          }
          if (CollectionUtil.isNotEmpty(categoryItem.children)) {
            continue;
          }
          categoryItem.children = await this.getCategoryChildren(parentId);
          categoryList = categoryItem.children;
        }
        this.categoryInfoList = categoryInfoList;
        window.console.log(this.categoryInfoList);
      },
      loadCategoryLazy(node, resolve) {
        const {data} = node;
        if (typeof data === 'undefined') {
          return;
        }
        if (data.leaf) {
          resolve();
          return;
        }
        if (CollectionUtil.isNotEmpty(data.children)) {
          resolve();
          return;
        }
        const parentId = data.value;
        this.getCategoryChildren(parentId).then(categoryChildren => {
          resolve(categoryChildren);
        });
      },
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
      },
      handleChange(value) {
        this.$emit('input', value)
      }
    },
    watch: {
      value(newValue) {
        this.initCategoryInfoList(newValue);
        this.currentValue = newValue;
        window.console.log(newValue);
      }
    }
  }
</script>
