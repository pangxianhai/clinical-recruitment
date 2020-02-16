<template>
    <div class="reference-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>推荐人管理</el-breadcrumb-item>
            <el-breadcrumb-item>推荐人列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form ref="queryInfo" :model="queryInfo" :inline="true">
            <el-form-item label="姓名:" prop="realName">
                <el-input v-model="queryInfo.realName" clearable size="mini"></el-input>
            </el-form-item>
            <el-form-item label="手机号:" prop="phoneLike">
                <el-input v-model="queryInfo.phoneLike" clearable size="mini"></el-input>
            </el-form-item>
            <el-form-item label="状态:" prop="status">
                <el-select v-model="queryInfo.status" size="mini" clearable placeholder="状态">
                    <el-option label="全部" value=""></el-option>
                    <el-option label="正常" value="1"></el-option>
                    <el-option label="冻结" value="2"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button size="mini" type="primary" @click="loadReferenceInfo()"
                           icon="el-icon-search">查询
                </el-button>
            </el-form-item>
        </el-form>
        <el-table
            :data="referenceList"
            border
            style="width: 100%">
            <el-table-column
                fixed
                prop="userInfoRes.realName"
                label="姓名">
            </el-table-column>
            <el-table-column
                prop="userInfoRes.phone"
                label="手机号">
            </el-table-column>
            <el-table-column
                prop="userInfoRes.nickname"
                label="微信昵称">
            </el-table-column>
            <el-table-column
                prop="userInfoRes.gender.desc"
                label="性别">
            </el-table-column>

            <el-table-column
                prop="address"
                label="地址">
            </el-table-column>
            <el-table-column
                prop="medicalInstitution"
                label="执业机构">
            </el-table-column>
            <el-table-column
                prop="medicalCategory"
                label="执业类别">
            </el-table-column>
            <el-table-column
                prop="status.desc"
                label="状态">
            </el-table-column>
            <el-table-column
                width="60"
                fixed="right"
                label="操作">
                <template slot-scope="scope">
                    <el-row type="flex">
                        <el-tooltip effect="dark" content="更新" placement="bottom"
                                    :hide-after="500">
                            <el-col>
                                <el-button
                                    icon="el-icon-edit"
                                    type="success"
                                    @click="onReferenceUpdate(scope.row)"
                                    size="mini" circle>
                                </el-button>
                            </el-col>
                        </el-tooltip>
                    </el-row>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @current-change="loadReferenceInfo"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="totalRecord">
        </el-pagination>
    </div>
</template>
<style>
    .reference-list .el-form {
        margin-top: 10px;
        padding: 10px 8px 10px 8px;
        background: #dddef5;
        border-radius: 4px;
    }

    .reference-list .el-table {
        margin-top: 20px;
    }

    .reference-list .el-table .cell {
        text-align: center;
    }

    .reference-list .el-pagination {
        float: right;
    }

    .reference-list .el-row {
        margin-top: 5px;
    }
</style>

<script>
  import ReferenceApi from '@/api/ReferenceApi';

  export default {
    data: function () {
      return {
        pageSize: 10,
        queryInfo: {
          realName: '',
          phoneLike: '',
          status: ''
        },
        referenceList: [],
        totalRecord: 0,
        currentPage: 1
      }
    },
    created: function () {
      Object.assign(this.queryInfo, this.$route.query);
      this.loadReferenceInfo();
    },
    methods: {
      loadReferenceInfo() {
        this.$router.replace({
          query: Object.assign(this.queryInfo, {
            currentPage: this.currentPage,
            pageSize: this.pageSize
          })
        }, function () {
        });
        let queryParam = Object.assign({}, this.queryInfo);
        ReferenceApi.getReference(queryParam).then(data => {
          this.referenceList = data.data;
          this.totalRecord = data.paginator.totalRecord;
        });
      },
      onReferenceUpdate(reference) {
        this.$router.push({
          path: `/reference/update/${reference.referenceId}`
        }, function () {

        });
      }
    }
  }
</script>