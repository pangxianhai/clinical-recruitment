<template>
    <div class="patient-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>患者管理</el-breadcrumb-item>
            <el-breadcrumb-item>患者列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-table
            :data="patientList"
            border
            style="width: 100%">
            <el-table-column
                fixed
                prop="userInfoVO.realName"
                label="姓名">
            </el-table-column>
            <el-table-column
                prop="userInfoVO.phone"
                label="手机号">
            </el-table-column>
            <el-table-column
                prop="userInfoVO.nickname"
                label="微信昵称">
            </el-table-column>
            <el-table-column
                prop="userInfoVO.gender.desc"
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
                    <el-tag v-if="scope.row.userInfoVO.status.code===UserStatus.NORMAL">
                        {{scope.row.userInfoVO.status.desc}}
                    </el-tag>
                    <el-tag v-if="scope.row.userInfoVO.status.code===UserStatus.FREEZE" type="info">
                        {{scope.row.userInfoVO.status.desc}}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column
                width="80"
                fixed="right"
                label="操作">
                <template slot-scope="scope">
                    <el-button
                        v-if="scope.row.userInfoVO.status.code===UserStatus.NORMAL"
                        type="danger"
                        @click="freezeUser(scope.row.userInfoVO)"
                        size="mini">
                        冻结
                    </el-button>
                    <el-button
                        v-if="scope.row.userInfoVO.status.code===UserStatus.FREEZE"
                        type="primary"
                        @click="unfreezeUser(scope.row.userInfoVO)"
                        size="mini">
                        解冻
                    </el-button>
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
    .patient-list .el-table {
        margin-top: 20px;
    }

    .patient-list .el-table th.is-leaf {
        color: #858585;
    }

    .patient-list .el-table .cell {
        text-align: center;
    }

    .patient-list .el-pagination {
        float: right;
    }

</style>

<script>
  import {
    Breadcrumb,
    BreadcrumbItem,
    Table,
    TableColumn,
    Pagination,
    Button,
    Tag,
    Message
  } from 'element-ui';
  import PatientApi from '@/api/PatientApi';
  import {UserStatus} from '@/constants/Global';
  import UserApi from '@/api/UserApi';

  export default {
    components: {
      [Breadcrumb.name]: Breadcrumb,
      [BreadcrumbItem.name]: BreadcrumbItem,
      [Table.name]: Table,
      [TableColumn.name]: TableColumn,
      [Pagination.name]: Pagination,
      [Button.name]: Button,
      [Tag.name]: Tag,
    },
    data: function () {
      return {
        UserStatus: UserStatus,
        patientList: [],
        currentPage: 1,
        totalRecord: 0,
        pageSize: 10,
      }
    },
    created: function () {
      this.loadPatientInfo();
    },
    methods: {
      loadPatientInfo: function () {
        PatientApi.getPatient({
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }).then(data => {
          this.patientList = data.data;
          this.totalRecord = data.paginator.totalRecord;
        });
      },
      freezeUser: function (userInfo) {
        UserApi.freezeUser(userInfo.userId).then(success => {
          if (success) {
            Message.success('操作成功!');
            this.loadPatientInfo();
          } else {
            Message.error('操作失败');
          }
        });
      },
      unfreezeUser: function (userInfo) {
        UserApi.unfreezeUser(userInfo.userId).then(success => {
          if (success) {
            Message.success('操作成功!');
            this.loadPatientInfo();
          } else {
            Message.error('操作失败');
          }
        });
      }
    },
  }
</script>
