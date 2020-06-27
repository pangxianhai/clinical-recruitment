<template>
    <div class="patient-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>患者管理</el-breadcrumb-item>
            <el-breadcrumb-item>患者列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form ref="queryInfo" :model="queryInfo" :inline="true">
            <el-form-item label="姓名:" prop="realName">
                <el-input v-model="queryInfo.realName" size="mini" clearable></el-input>
            </el-form-item>
            <el-form-item label="手机号:" prop="phoneLike">
                <el-input v-model="queryInfo.phoneLike" size="mini" clearable></el-input>
            </el-form-item>
            <el-form-item label="地区:" prop="addressIds">
                <el-cascader
                    size="mini"
                    :options="areaData"
                    v-model="queryInfo.addressIds"
                    clearable
                    placeholder="地区">
                </el-cascader>
            </el-form-item>
            <el-form-item label="状态:" prop="status">
                <el-select v-model="queryInfo.status" size="mini" clearable placeholder="状态">
                    <el-option label="全部" value=""></el-option>
                    <el-option label="正常" value="1"></el-option>
                    <el-option label="冻结" value="2"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button size="mini" type="primary" @click="loadPatientInfo()"
                           icon="el-icon-search">查询
                </el-button>
            </el-form-item>
        </el-form>
        <el-table
            :data="patientList"
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
                prop="age"
                label="年龄">
            </el-table-column>
            <el-table-column
                label="状态">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.status.code===PatientStatus.NORMAL">
                        {{scope.row.status.desc}}
                    </el-tag>
                    <el-tag v-if="scope.row.status.code===PatientStatus.FREEZE" type="info">
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
                                    v-if="scope.row.status.code===PatientStatus.NORMAL"
                                    type="danger"
                                    icon="iconfont icon-cf-c09"
                                    @click="freezePatient(scope.row)"
                                    size="mini">
                                </el-button>
                            </el-col>
                        </el-tooltip>
                    </el-row>
                    <el-row type="flex">
                        <el-tooltip effect="dark" content="解冻" placement="top" :hide-after="1000">
                            <el-col>
                                <el-button
                                    v-if="scope.row.status.code===PatientStatus.FREEZE"
                                    type="primary"
                                    icon="iconfont icon-tubiaozhizuomoban--"
                                    @click="unfreezePatient(scope.row)"
                                    size="mini">
                                </el-button>
                            </el-col>
                        </el-tooltip>
                    </el-row>
                    <el-row type="flex">
                        <el-tooltip effect="dark" content="更新" placement="bottom" :hide-after="1000">
                            <el-col>
                                <el-button
                                    icon="iconfont icon-bianji"
                                    type="success"
                                    @click="onUpdateAction(scope.row)"
                                    size="mini">
                                </el-button>
                            </el-col>
                        </el-tooltip>
                    </el-row>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @current-change="loadPatientInfo"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="totalRecord">
        </el-pagination>
    </div>
</template>


<style>
    .patient-list .el-form {
        margin-top: 10px;
        padding: 10px 8px 10px 8px;
        background: #dddef5;
        border-radius: 4px;
    }

    .patient-list .el-table {
        margin-top: 20px;
    }

    .patient-list .el-table .cell {
        text-align: center;
    }

    .patient-list .el-pagination {
        float: right;
    }

    .patient-list .el-row {
        margin-top: 5px;
    }

</style>

<script>
  import PatientApi from '@/api/PatientApi';
  import {PatientStatus} from '@/constants/Global';
  import AreaData from '@/util/AreaData';

  export default {
    data: function () {
      return {
        areaData: AreaData,
        PatientStatus: PatientStatus,
        patientList: [],
        currentPage: 1,
        totalRecord: 0,
        pageSize: 10,
        queryInfo: {
          addressIds: [],
          realName: '',
          phoneLike: '',
          status: ''
        }
      }
    },
    created: function () {
      Object.assign(this.queryInfo, this.$route.query);
      let addressIds = this.queryInfo.addressIds;
      if (typeof addressIds !== 'undefined' && addressIds.length > 0) {
        let addIds = [];
        for (let i in addressIds) {
          addIds.push(parseInt(addressIds[i]));
        }
        this.queryInfo.addressIds = addIds;
      }
      this.loadPatientInfo();
    },
    methods: {
      loadPatientInfo: function () {
        this.$router.replace({
          query: Object.assign(this.queryInfo, {
            currentPage: this.currentPage,
            pageSize: this.pageSize
          })
        }, function () {

        });

        let queryParam = Object.assign({
          provinceId: this.queryInfo.addressIds[0],
          cityId: this.queryInfo.addressIds[1],
          districtId: this.queryInfo.addressIds[2],
        }, this.queryInfo);
        delete queryParam.addressIds;
        PatientApi.getPatient(queryParam).then(data => {
          this.patientList = data.data;
          this.totalRecord = data.paginator.totalRecord;
        });
      },
      freezePatient: function (patientInfo) {
        PatientApi.freezePatient(patientInfo.patientId).then(success => {
          if (success) {
            this.$message.success('操作成功!');
            this.loadPatientInfo();
          }
        });
      },
      unfreezePatient: function (patientInfo) {
        PatientApi.unfreezePatient(patientInfo.patientId).then(success => {
          if (success) {
            this.$message.success('操作成功!');
            this.loadPatientInfo();
          }
        });
      },
      onUpdateAction: function (patientInfo) {
        this.$router.push({
          path: `/patient/update/${patientInfo.patientId}`
        });
      }
    },
  }
</script>
