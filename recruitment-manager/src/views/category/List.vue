<template>
    <div class="category-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>类目管理</el-breadcrumb-item>
            <el-breadcrumb-item>类目列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form ref="queryInfo" :model="queryInfo" :inline="true">
            <el-form-item label="类目ID:" prop="categoryId">
                <el-input v-model.number="queryInfo.categoryId"
                          size="mini" type="number" clearable></el-input>
            </el-form-item>
            <el-form-item label="名称:" prop="categoryNameLike">
                <el-input v-model="queryInfo.categoryNameLike" size="mini" clearable></el-input>
            </el-form-item>
            <el-form-item label="类目等级:" prop="level">
                <el-input v-model.number="queryInfo.level"
                          size="mini" type="number" clearable></el-input>
            </el-form-item>
            <el-form-item label="类目父ID:" prop="parentId">
                <el-input v-model="queryInfo.parentId" type="number" size="mini"
                          clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button size="mini" type="primary" @click="loadCategoryInfoAction('queryInfo')"
                           icon="el-icon-search">查询
                </el-button>
            </el-form-item>
        </el-form>
        <el-table
            :data="categoryList"
            border
            style="width: 100%">
            <el-table-column
                fixed
                prop="categoryId"
                label="类目ID">
            </el-table-column>
            <el-table-column
                fixed
                prop="categoryName"
                label="类目名称">
            </el-table-column>
            <el-table-column
                fixed
                prop="level"
                label="类目级别">
            </el-table-column>
            <el-table-column
                fixed
                prop="parentId"
                label="父类目ID">
            </el-table-column>
            <el-table-column
                width="120"
                fixed="right"
                label="操作">
                <template slot-scope="scope">
                    <el-row type="flex">
                        <el-tooltip effect="dark" content="更新" placement="top"
                                    :hide-after="1000">
                            <el-col>
                                <el-button
                                    icon="iconfont icon-bianji"
                                    type="primary"
                                    @click="onCategoryUpdate(scope.row)"
                                    size="mini">
                                    更新
                                </el-button>
                            </el-col>
                        </el-tooltip>
                    </el-row>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @current-change="loadCategoryInfo"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="prev, pager, next"
            :total="totalRecord">
        </el-pagination>
    </div>
</template>

<style>
    .category-list .el-form {
        margin-top: 10px;
        padding: 10px 8px 10px 8px;
        background: #dddef5;
        border-radius: 4px;
    }

    .category-list .el-table {
        margin-top: 20px;
    }

    .category-list .el-table .cell {
        text-align: center;
    }

    .category-list .el-pagination {
        float: right;
    }

    .category-list .el-row {
        margin-top: 5px;
    }
</style>
<script>
  import CategoryApi from '@/api/CategoryApi';

  export default {
    data: function () {
      return {
        categoryList: [],
        currentPage: 1,
        pageSize: 10,
        totalRecord: 0,
        queryInfo: {
          categoryNameLike: '',
          categoryId: '',
          level: '',
          parentId: ''
        }
      }
    },
    created: function () {
      this.loadCategoryInfo();
    },
    methods: {
      loadCategoryInfoAction(formName) {
        this.$refs[formName].validate((valid) => {
          if (!valid) {
            return;
          }
          this.loadCategoryInfo();
        });
      },
      loadCategoryInfo() {
        this.$router.replace({
          query: Object.assign(this.queryInfo, {
            currentPage: this.currentPage,
            pageSize: this.pageSize
          })
        }, function () {
        });
        CategoryApi.getCategory(this.queryInfo).then(data => {
          this.categoryList = data.data;
          this.totalRecord = data.paginator.totalRecord;
        });
      },
      onCategoryUpdate(categoryInfo) {
        this.$router.push({
          path: `/category/update/${categoryInfo.categoryId}`
        });
      }
    }
  }
</script>