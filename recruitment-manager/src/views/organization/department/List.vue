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
                <el-select v-model="queryInfo.organizationId" clearable filterable
                           style="width: 100%"
                           placeholder="请选择研究机构">
                    <el-option
                        v-for="organization in organizationList"
                        :key="organization.organizationId"
                        :label="organization.name"
                        :value="organization.organizationId">
                            <span
                                style="float: left;margin-right:10px">{{ organization.name }}</span>
                        <span
                            style="float: right; color: #8492a6; font-size: 13px">{{ organization.address }}</span>
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button size="mini" type="primary" @click="loadOrganizationDepartmentInfo()"
                           icon="el-icon-search">查询
                </el-button>
            </el-form-item>
        </el-form>
        <el-table
            :data="organizationDepartmentList"
            border
            style="width: 100%">
            <el-table-column
                fixed
                prop="name"
                label="科室名称">
            </el-table-column>
            <el-table-column
                fixed
                prop="organizationName"
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
  import {
    Breadcrumb,
    BreadcrumbItem,
    Table,
    TableColumn,
    Pagination,
    Button,
    Tag,
    Row,
    Col,
    Form,
    FormItem,
    Input,
    InputNumber,
    Select,
    Option,
    Cascader,
  } from 'element-ui';
  import AreaData from '@/util/AreaData';
  import OrganizationApi from '@/api/OrganizationApi';

  export default {
    components: {
      [Breadcrumb.name]: Breadcrumb,
      [BreadcrumbItem.name]: BreadcrumbItem,
      [Table.name]: Table,
      [TableColumn.name]: TableColumn,
      [Pagination.name]: Pagination,
      [Button.name]: Button,
      [Tag.name]: Tag,
      [Row.name]: Row,
      [Col.name]: Col,
      [Form.name]: Form,
      [FormItem.name]: FormItem,
      [Input.name]: Input,
      [InputNumber.name]: InputNumber,
      [Select.name]: Select,
      [Option.name]: Option,
      [Cascader.name]: Cascader,
    },
    data: function () {
      return {
        areaData: AreaData,
        organizationDepartmentList: [],
        organizationList: [],
        currentPage: 1,
        totalRecord: 0,
        pageSize: 10,
        queryInfo: {
          organizationId: ''
        }
      }
    },
    created: function () {
      Object.assign(this.queryInfo, this.$route.query);
      this.loadOrganization();
      this.loadOrganizationDepartmentInfo();
    },
    methods: {
      loadOrganization: function () {
        OrganizationApi.getOrganization({
          currentPage: 1,
          pageSize: 1000
        }).then(data => {
          data.data.forEach((organization) => {
            organization.organizationId = String(organization.organizationId);
          });
          this.organizationList = data.data;
        });
      },
      loadOrganizationDepartmentInfo: function () {
        this.$router.replace({
          query: Object.assign(this.queryInfo, {
            currentPage: this.currentPage,
            pageSize: this.pageSize
          })
        }).catch(e => e);
        OrganizationApi.getOrganizationDepartment(this.queryInfo).then(data => {
          this.organizationDepartmentList = data.data;
          this.totalRecord = data.paginator.totalRecord;
        });
      },
    }
  }

</script>
