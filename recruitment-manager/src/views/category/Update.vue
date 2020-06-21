<template>
    <div class="category-update">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/category/list' }">类目管理</el-breadcrumb-item>
            <el-breadcrumb-item>更新类目</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form status-icon style="margin-top: 25px;width: 35%;"
                 :rules="categoryRules"
                 ref="categoryInfo"
                 :model="categoryInfo"
                 label-width="80px">
            <el-alert style="margin-left: 80px;margin-bottom: 5px; width: 79%;"
                      title="如需修改成一级类目请清空父类目选项"
                      type="info" :closable="false">
            </el-alert>
            <el-form-item label="父类目" prop="parentId">
                <CategorySelect v-model="categoryInfo.parentIdList"
                                placeholder="请选择父类目"></CategorySelect>
            </el-form-item>
            <el-alert style="margin-left: 80px;margin-bottom: 5px; width: 79%;"
                      title="更新后的类目自动归纳到父类目的下一级"
                      type="info" :closable="false">
            </el-alert>
            <el-form-item label="名称" prop="categoryName">
                <el-input v-model="categoryInfo.categoryName" placeholder="请求填入类目名称"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-edit"
                           @click="onUpdateCategoryAction('categoryInfo')">修改
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
  import {RouterUtil} from '@/util/Util';
  import CategorySelect from '@/components/CategorySelect'
  import CategoryApi from '@/api/CategoryApi';
  // import {StringUtil} from '@/util/Util';
  import {CollectionUtil} from "../../util/Util";

  export default {
    components: {
      CategorySelect: CategorySelect
    },
    data: function () {
      return {
        categoryInfo: {
          parentIdList: [],
          categoryName: ''
        },
        categoryRules: {
          categoryName: [
            {required: true, message: '请填写类目名称', trigger: 'blur'},
            {min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur'},
            {validator: this.validateName, trigger: 'blur'}
          ],
        }
      };
    },
    created() {
      let categoryId = this.$route.params.categoryId;
      this.onLoadCategory(categoryId);
    },
    methods: {
      onLoadCategory(categoryId) {
        CategoryApi.getCategoryById(categoryId).then(categoryInfo => {
          this.categoryInfo.categoryId = categoryInfo.categoryId;
          this.categoryInfo.categoryName = categoryInfo.categoryName;
          this.categoryInfo.parentIdList = categoryInfo.path;
        });
      },
      onUpdateCategoryAction(formName) {
        this.$refs[formName].validate((valid) => {
          if (!valid) {
            return false;
          }
          let categoryUpdateInfo = {};
          Object.assign(categoryUpdateInfo, this.categoryInfo);
          let parentIdList = this.categoryInfo.parentIdList;
          if (CollectionUtil.isNotEmpty(parentIdList)) {
            categoryUpdateInfo.parentId = parentIdList[parentIdList.length - 1];
          }
          CategoryApi.updateCategory(this.categoryInfo.categoryId, categoryUpdateInfo).then(
              result => {
                if (result) {
                  this.$message.success('更新成功');
                  RouterUtil.goToBack(this.$route, this.$router, '/category/list');
                }
              });
        });
      },
      validateName: function (rule, value, callback) {
        if (value === '') {
          callback(new Error('请填写类目名称'));
        } else {
          CategoryApi.getCategoryByName(value).then(categoryInfo => {
            if (!categoryInfo || !categoryInfo.categoryId) {
              callback();
              return;
            }
            if (categoryInfo.categoryId === this.categoryInfo.categoryId) {
              callback();
            } else {
              callback(new Error('该名称已使用'));
            }
          });
        }
      }
    }
  }

</script>