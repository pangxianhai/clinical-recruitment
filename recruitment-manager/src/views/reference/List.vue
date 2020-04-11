<template>
    <div class="reference-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>推荐人管理</el-breadcrumb-item>
            <el-breadcrumb-item>推荐人列表</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="query-panel">
            <el-form ref="queryInfo" :model="queryInfo" :inline="true">
                <el-form-item label="姓名:" prop="realName">
                    <el-input v-model="queryInfo.realName" clearable size="mini"></el-input>
                </el-form-item>
                <el-form-item label="手机号:" prop="phoneLike">
                    <el-input v-model="queryInfo.phoneLike" clearable size="mini"></el-input>
                </el-form-item>
                <el-form-item label="状态:" prop="status">
                    <el-select v-model="queryInfo.status" size="mini" clearable placeholder="状态">
                        <el-option label="审核通过" :value="3"></el-option>
                        <el-option label="审核拒绝" :value="2"></el-option>
                        <el-option label="待审核" :value="1"></el-option>
                        <el-option label="冻结" :value="0"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button size="mini" type="primary" @click="loadReferenceInfo()"
                               icon="el-icon-search">查询
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
        <el-table
            :data="referenceList"
            border
            style="width: 100%"
            v-loading="loading">
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
                prop="departmentDetailRes.hospitalRes.name"
                label="机构">
            </el-table-column>
            <el-table-column
                prop="departmentDetailRes.hospitalRes.address"
                label="机构地址">
            </el-table-column>
            <el-table-column
                prop="departmentDetailRes.name"
                label="科室">
            </el-table-column>
            <el-table-column
                label="角色">
                <template slot-scope="scope">
                    <el-tag
                        v-if="scope.row.referenceRole.code===ReferenceRole.DOCTOR"
                        type=""
                        size="small"
                        effect="dark">
                        {{scope.row.referenceRole.desc}}
                    </el-tag>
                    <el-tag
                        v-if="scope.row.referenceRole.code===ReferenceRole.HEADS_OF_DEPARTMENT"
                        type="warning"
                        size="small"
                        effect="dark">
                        {{scope.row.referenceRole.desc}}
                    </el-tag>
                </template>
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
                <template slot-scope="scope">
                    <el-tag
                        v-if="scope.row.status.code===ReferenceStatus.FREEZE"
                        type="info"
                        size="small"
                        effect="light">
                        {{scope.row.status.desc}}
                    </el-tag>
                    <el-tag
                        v-if="scope.row.status.code===ReferenceStatus.UNAUDITED"
                        type="warning"
                        size="small"
                        effect="light">
                        {{scope.row.status.desc}}
                    </el-tag>
                    <el-tag
                        v-if="scope.row.status.code===ReferenceStatus.REFUSE"
                        type="danger"
                        size="small"
                        effect="light">
                        {{scope.row.status.desc}}
                    </el-tag>
                    <el-tag
                        v-if="scope.row.status.code===ReferenceStatus.ADOPT"
                        type=""
                        size="small"
                        effect="light">
                        {{scope.row.status.desc}}
                    </el-tag>
                </template>
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
                    <el-row type="flex">
                        <el-tooltip effect="dark" content="审核" placement="bottom"
                                    :hide-after="500">
                            <el-col>
                                <el-button
                                    icon="el-icon-star-off"
                                    type="danger"
                                    @click="onOpenAuditedDialog(scope.row)"
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
        <el-dialog :title="auditedInfo.auditedTitle"
                   :visible.sync="auditedInfo.dialogAuditedVisible"
                   center
                   width="40%">

            <el-form style="width: 230px; margin: auto">
                <el-form-item label="状态:">
                    <el-select size="mini" v-model="auditedInfo.status">
                        <el-option label="审核通过" :value="3"></el-option>
                        <el-option label="审核拒绝" :value="2"></el-option>
                        <el-option label="待审核" :value="1"></el-option>
                        <el-option label="冻结" :value="0"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button size="mini" icon="el-icon-circle-close"
                           @click="auditedInfo.dialogAuditedVisible = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="onAuditedAction()"
                           icon="el-icon-star-off">审核
                </el-button>
            </span>
        </el-dialog>
    </div>
</template>
<style>
    .reference-list .query-panel .el-form {
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
  import {ReferenceRole, ReferenceStatus} from '@/constants/Global';

  export default {
    data: function () {
      return {
        ReferenceRole: ReferenceRole,
        ReferenceStatus: ReferenceStatus,
        queryInfo: {
          realName: '',
          phoneLike: '',
          status: ''
        },
        referenceList: [],
        pageSize: 10,
        totalRecord: 0,
        currentPage: 1,
        auditedInfo: {
          dialogAuditedVisible: false,
          auditedTitle: '',
          status: 0,
          referenceId: 0
        },
        loading: false
      }
    },
    created: function () {
      Object.assign(this.queryInfo, this.$route.query);
      this.loadReferenceInfo();
    },
    methods: {
      loadReferenceInfo() {
        this.loading = true;
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
          this.loading = false;
        });
      },
      onReferenceUpdate(reference) {
        this.$router.push({
          path: `/reference/update/${reference.referenceId}`
        }, function () {

        });
      },
      onOpenAuditedDialog(reference) {
        this.auditedInfo.auditedTitle = '您正在审核' + reference.userInfoRes.realName + '的信息';
        this.auditedInfo.dialogAuditedVisible = true;
        this.auditedInfo.referenceId = reference.referenceId;
        this.auditedInfo.status = reference.status.code;
      },
      onAuditedAction() {
        let loading = this.$loading();
        ReferenceApi.updateReferenceStatus(this.auditedInfo.referenceId,
            this.auditedInfo.status).then(data => {
          if (data) {
            this.$message.success('操作成功');
            this.loadReferenceInfo();
            this.auditedInfo.dialogAuditedVisible = false;
            setTimeout(() => {
              loading.close();
            }, 200);
          }
        });
      }
    }
  }
</script>