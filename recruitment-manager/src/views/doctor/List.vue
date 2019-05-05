<template>
    <div class="doctor-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>医生管理</el-breadcrumb-item>
            <el-breadcrumb-item>医生列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-table
            :data="doctorList"
            border
            height="500"
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
                prop="userInfoVO.status.desc"
                label="状态">
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
        </el-table>
        <el-pagination
            @current-change="loadDoctorInfo"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="totalRecord">
        </el-pagination>
    </div>
</template>
<style scoped>
    .doctor-list .el-table {
        margin-top: 20px;
    }

    .doctor-list .el-table th.is-leaf {
        color: #858585;
    }

    .doctor-list .el-table thead .cell {
        text-align: center;
    }

    .doctor-list .el-pagination {
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
  } from 'element-ui';
  import DoctorApi from '@/api/DoctorApi'

  export default {
    components: {
      [Breadcrumb.name]: Breadcrumb,
      [BreadcrumbItem.name]: BreadcrumbItem,
      [Table.name]: Table,
      [TableColumn.name]: TableColumn,
      [Pagination.name]: Pagination,
    },
    data: function () {
      return {
        doctorList: [],
        currentPage: 1,
        totalRecord: 0,
        pageSize: 10,
      }
    },
    created: function () {
      this.loadDoctorInfo();
    },
    methods: {
      loadDoctorInfo: function () {
        DoctorApi.getDoctor({
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }).then(data => {
          this.doctorList = data.data;
          this.totalRecord = data.paginator.totalRecord;
        });
      }
    }
  }
</script>
