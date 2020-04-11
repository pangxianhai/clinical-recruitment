<template>
    <div class="organization-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>机构管理</el-breadcrumb-item>
            <el-breadcrumb-item>机构列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form ref="queryInfo" :model="queryInfo" :inline="true">
            <el-form-item label="名称:" prop="nameLike">
                <el-input v-model="queryInfo.nameLike" size="mini" clearable></el-input>
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
            <el-form-item>
                <el-button size="mini" type="primary" @click="loadOrganizationInfo()"
                           icon="el-icon-search">查询
                </el-button>
            </el-form-item>
        </el-form>
        <el-table
            :data="hospitalList"
            border
            style="width: 100%">
            <el-table-column
                fixed
                prop="name"
                label="机构名称">
            </el-table-column>
            <el-table-column
                fixed
                prop="provinceName"
                label="省">
            </el-table-column>
            <el-table-column
                fixed
                prop="cityName"
                label="市">
            </el-table-column>
            <el-table-column
                fixed
                prop="districtName"
                label="区">
            </el-table-column>
        </el-table>
        <el-pagination
            @current-change="loadOrganizationInfo"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="prev, pager, next"
            :total="totalRecord">
        </el-pagination>
    </div>
</template>

<style>
    .organization-list .el-form {
        margin-top: 10px;
        padding: 10px 8px 10px 8px;
        background: #dddef5;
        border-radius: 4px;
    }

    .organization-list .el-table {
        margin-top: 20px;
    }

    .organization-list .el-table .cell {
        text-align: center;
    }

    .organization-list .el-pagination {
        float: right;
    }

    .organization-list .el-row {
        margin-top: 5px;
    }

</style>

<script>
  import AreaData from '@/util/AreaData';
  import HospitalApi from '@/api/HospitalApi';

  export default {
    data: function () {
      return {
        areaData: AreaData,
        hospitalList: [],
        currentPage: 1,
        pageSize: 10,
        totalRecord: 0,
        queryInfo: {
          nameLike: '',
          addressIds: []
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
      this.loadOrganizationInfo();
    },
    methods: {
      loadOrganizationInfo: function () {
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
        HospitalApi.getHospital(queryParam).then(data => {
          this.hospitalList = data.data;
          this.totalRecord = data.paginator.totalRecord;
        });
      }
    }
  }

</script>
