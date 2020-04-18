<template>
    <div>
        <el-cascader style="width:100%"
                     v-model="currentValue"
                     :options="categoryList"
                     :props="categoryProps"
                     :placeholder="placeholder"
                     @change="handleChange"
                     clearable filterable>
        </el-cascader>
    </div>
</template>

<script>
  import {mapGetters, mapState, mapActions} from 'vuex'
  import {CollectionUtil} from "../util/Util";

  export default {
    props: {
      value: Array,
      placeholder: String
    },
    data: function () {
      return {
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
      this.$store.dispatch('CategorySelect/initCategoryInfoList', {});
    },
    ...mapState({}),
    computed: {
      ...mapGetters('CategorySelect', {
        categoryList: 'categoryList'
      }),

    },
    methods: {
      ...mapActions('CategorySelect', {
        getCategoryChildren: 'getCategoryChildren'
      }),
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
        this.getCategoryChildren({
          parentId: parentId
        }).then(categoryChildren => {
          resolve(categoryChildren);
        });
      },
      handleChange(value) {
        this.$emit('input', value)
      }
    },
    watch: {
      value(newValue) {
        this.$store.dispatch('CategorySelect/initCategoryInfoList', {
          parentIds: newValue
        });
        this.currentValue = newValue;
      }
    }
  }
</script>
