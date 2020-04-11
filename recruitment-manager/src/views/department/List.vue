<template>
    <div class="organization-department-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>机构管理</el-breadcrumb-item>
            <el-breadcrumb-item>科室列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form ref="queryInfo" :model="queryInfo" :inline="true">
            <el-form-item label="名称:" prop="nameLike">
                <el-input v-model="queryInfo.nameLike" size="mini" clearable></el-input>
            </el-form-item>
            <el-form-item label="机构:" prop="organizationId">
                <el-select v-model="queryInfo.hospitalId" clearable filterable
                           style="width: 100%"
                           placeholder="请选择研究机构">
                    <el-option
                        v-for="hospitalInfo in hospitalList"
                        :key="hospitalInfo.hospitalId"
                        :label="hospitalInfo.name"
                        :value="hospitalInfo.hospitalId">
                        <span style="float: left;margin-right:10px">{{ hospitalInfo.name }}</span>
                        <span style="float: right; color: #8492a6; font-size: 13px">{{ hospitalInfo.address }}</span>
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button size="mini" type="primary" @click="loadDepartmentInfo()"
                           icon="el-icon-search">查询
                </el-button>
            </el-form-item>
        </el-form>
        <el-table
            :data="departmentList"
            border
            style="width: 100%">
            <el-table-column
                fixed
                prop="name"
                label="科室名称">
            </el-table-column>
            <el-table-column
                fixed
                prop="hospitalRes.name"
                label="机构名称">
            </el-table-column>
        </el-table>

    </div>
</template>

<style>
    .organization-department-list .el-form {
        margin-top: 10px;
        padding: 10px 8px 10px 8px;
        background: #dddef5;
        border-radius: 4px;
    }

    .organization-department-list .el-table {
        margin-top: 20px;
    }

    .organization-department-list .el-table .cell {
        text-align: center;
    }

    .organization-department-list .el-pagination {
        float: right;
    }

    .organization-department-list .el-row {
        margin-top: 5px;
    }

</style>

<script>
  import AreaData from '@/util/AreaData';
  import DepartmentApi from '@/api/DepartmentApi';
  import HospitalApi from '@/api/HospitalApi';

  export default {
    data: function () {
      return {
        areaData: AreaData,
        departmentList: [],
        hospitalList: [],
        currentPage: 1,
        totalRecord: 0,
        pageSize: 10,
        queryInfo: {
          hospitalId: ''
        }
      }
    },
    created: function () {
      Object.assign(this.queryInfo, this.$route.query);
      this.loadOrganization();
      this.loadDepartmentInfo();
    },
    methods: {
      loadOrganization: function () {
        HospitalApi.getHospital({
          currentPage: 1,
          pageSize: 1000
        }).then(data => {
          data.data.forEach((hospitalInfo) => {
            hospitalInfo.hospitalId = String(hospitalInfo.hospitalId);
          });
          this.hospitalList = data.data;
        });
      },
      loadDepartmentInfo: function () {
        this.$router.replace({
          query: Object.assign(this.queryInfo, {
            currentPage: this.currentPage,
            pageSize: this.pageSize
          })
        }, function () {

        });
        DepartmentApi.getDepartment(this.queryInfo).then(data => {
          this.departmentList = data.data;
          this.totalRecord = data.paginator.totalRecord;
        });
      },
    }
  }

</script>
