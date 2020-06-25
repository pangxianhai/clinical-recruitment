<template>
    <div class="manager-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>管理员列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form ref="queryInfo" :model="queryInfo" :inline="true">
            <el-form-item label="姓名:" prop="realNameLike">
                <el-input v-model="queryInfo.realNameLike" size="mini" clearable></el-input>
            </el-form-item>
            <el-form-item label="手机号:" prop="phoneLike">
                <el-input v-model="queryInfo.phoneLike" size="mini" clearable></el-input>
            </el-form-item>
            <el-form-item label="类型:" prop="type">
                <el-select v-model="queryInfo.type" size="mini" placeholder="类型">
                    <el-option label="全部" :value="0"></el-option>
                    <el-option label="系统管理员" :value="1"></el-option>
                    <el-option label="业务管理员" :value="2"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="状态:" prop="status">
                <el-select v-model="queryInfo.status" size="mini" placeholder="状态">
                    <el-option label="全部" :value="0"></el-option>
                    <el-option label="正常" :value="1"></el-option>
                    <el-option label="冻结" :value="2"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button size="mini" type="primary" @click="loadAdminInfo()"
                           icon="el-icon-search">查询
                </el-button>
            </el-form-item>
        </el-form>
        <el-table
            :data="managerList"
            border
            style="width: 100%">
            <el-table-column
                prop="realName"
                label="姓名">
            </el-table-column>
            <el-table-column
                prop="phone"
                label="手机号">
            </el-table-column>
            <el-table-column
                prop="gender.desc"
                label="性别">
            </el-table-column>
            <el-table-column
                prop="type.desc"
                label="类型">
            </el-table-column>
            <el-table-column
                label="状态">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.status.code===AdminStatus.NORMAL">
                        {{scope.row.status.desc}}
                    </el-tag>
                    <el-tag v-if="scope.row.status.code===AdminStatus.FREEZE" type="info">
                        {{scope.row.status.desc}}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column
                width="100"
                fixed="right"
                label="操作">
                <template slot-scope="scope">
                    <el-row type="flex">
                        <el-tooltip effect="dark" content="冻结" placement="top" :hide-after="1000">
                            <el-col>
                                <el-button
                                    v-if="scope.row.status.code===AdminStatus.NORMAL"
                                    type="danger"
                                    icon="iconfont icon-cf-c09"
                                    @click="freezeAdministrator(scope.row)"
                                    size="mini">
                                </el-button>
                            </el-col>
                        </el-tooltip>
                    </el-row>
                    <el-row type="flex">
                        <el-tooltip effect="dark" content="解冻" placement="top" :hide-after="1000">
                            <el-col>
                                <el-button
                                    v-if="scope.row.status.code===AdminStatus.FREEZE"
                                    type="primary"
                                    icon="iconfont icon-tubiaozhizuomoban--"
                                    @click="unfreezeAdministrator(scope.row)"
                                    size="mini">
                                </el-button>
                            </el-col>
                        </el-tooltip>
                    </el-row>
                    <el-row type="flex">
                        <el-tooltip effect="dark" content="编辑" placement="bottom"
                                    :hide-after="1000">
                            <el-col>
                                <el-button
                                    type="success"
                                    icon="iconfont icon-bianji"
                                    @click="onUpdateAdministratorAction(scope.row)"
                                    size="mini">
                                </el-button>
                            </el-col>
                        </el-tooltip>
                    </el-row>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @current-change="loadAdminInfo"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="totalRecord">
        </el-pagination>
    </div>
</template>

<style>
    .manager-list .el-form {
        margin-top: 10px;
        padding: 10px 8px 10px 8px;
        background: #dddef5;
        border-radius: 4px;
    }

    .manager-list .el-table {
        margin-top: 20px;
        text-align: center;
    }

    .manager-list .el-table .cell {
        text-align: center;
    }

    .manager-list .el-pagination {
        float: right;
    }

    .manager-list .el-row {
        margin-top: 5px;
    }
</style>

<script>
  import {AdminStatus} from '@/constants/Global';
  import AdminApi from '@/api/AdminApi';
  import {StringUtil} from "@/util/Util";

  export default {
    data: function () {
      return {
        AdminStatus: AdminStatus,
        managerList: [],
        currentPage: 1,
        totalRecord: 0,
        pageSize: 10,
        queryInfo: {
          type: 0,
          status: 0
        }
      }
    },
    created: function () {
      Object.assign(this.queryInfo, this.$route.query);
      if (StringUtil.isNotEmpty(this.queryInfo.type)) {
        this.queryInfo.type = parseInt(this.queryInfo.type)
      }
      if (StringUtil.isNotEmpty(this.queryInfo.status)) {
        this.queryInfo.status = parseInt(this.queryInfo.status)
      }
      this.loadAdminInfo();
    },
    methods: {
      loadAdminInfo: function () {
        this.$router.replace({
          query: Object.assign(this.queryInfo, {
            currentPage: this.currentPage,
            pageSize: this.pageSize
          })
        }, () => {
        });
        AdminApi.getAdmin(Object.assign(this.queryInfo), {
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }).then(data => {
          this.managerList = data.data;
          this.totalRecord = data.paginator.totalRecord;
        });
      },
      freezeAdministrator: function (adminInfo) {
        window.console.log(adminInfo);
        AdminApi.freezeAdmin(adminInfo.id).then(success => {
          if (success) {
            this.$message.success('操作成功!');
            this.loadAdminInfo();
          }
        })
      },
      unfreezeAdministrator: function (adminInfo) {
        window.console.log(adminInfo);
        AdminApi.unfreezeAdmin(adminInfo.id).then(success => {
          if (success) {
            this.$message.success('操作成功!');
            this.loadAdminInfo();
          }
        })
      },
      onUpdateAdministratorAction: function (adminInfo) {
        this.$router.push({
          path: `/administrator/update/${adminInfo.id}`
        });
      }
    }
  }
</script>
