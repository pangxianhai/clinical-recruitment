<template>
    <div class="research-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>研究员管理</el-breadcrumb-item>
            <el-breadcrumb-item>研究员列表</el-breadcrumb-item>
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
                <el-button size="mini" type="primary" @click="loadResearchInfo()"
                           icon="el-icon-search">查询
                </el-button>
            </el-form-item>
        </el-form>
        <el-table
            :data="researcherList"
            border
            style="width: 100%">
            <el-table-column
                fixed
                prop="userInfoRes.realName"
                label="姓名">
            </el-table-column>
            <el-table-column
                prop="userInfoRes.phone"
                label="电话号码">
            </el-table-column>
            <el-table-column
                prop="userInfoRes.gender.desc"
                label="性别">
            </el-table-column>
            <el-table-column
                prop="organizationRes.name"
                label="机构">
            </el-table-column>
            <el-table-column
                prop="organizationRes.address"
                label="机构地址">
            </el-table-column>
            <el-table-column
                prop="organizationDepartmentRes.name"
                label="科室">
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
                prop="remark"
                label="备注">
            </el-table-column>
            <el-table-column
                prop="status.desc"
                label="状态">
            </el-table-column>
        </el-table>
        <el-pagination
            @current-change="loadResearchInfo"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="totalRecord">
        </el-pagination>
    </div>
</template>

<style>
    .research-list .el-form {
        margin-top: 10px;
        padding: 10px 8px 10px 8px;
        background: #dddef5;
        border-radius: 4px;
    }

    .research-list .el-table {
        margin-top: 20px;
    }

    .research-list .el-table .cell {
        text-align: center;
    }

    .research-list .el-pagination {
        float: right;
    }

    .research-list .el-row {
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
    Form,
    FormItem,
    Input,
    Select,
    Option
  } from 'element-ui';
  import ResearcherApi from '@/api/ResearcherApi';

  export default {
    components: {
      [Breadcrumb.name]: Breadcrumb,
      [BreadcrumbItem.name]: BreadcrumbItem,
      [Table.name]: Table,
      [TableColumn.name]: TableColumn,
      [Pagination.name]: Pagination,
      [Button.name]: Button,
      [Tag.name]: Tag,
      [Form.name]: Form,
      [FormItem.name]: FormItem,
      [Input.name]: Input,
      [Select.name]: Select,
      [Option.name]: Option
    },
    data: function () {
      return {
        researcherList: [],
        currentPage: 1,
        totalRecord: 0,
        pageSize: 10,
        queryInfo: {}
      }
    },
    created: function () {
      this.loadResearchInfo();
    },
    methods: {
      loadResearchInfo: function () {
        ResearcherApi.getResearcher({}).then(data => {
          this.researcherList = data.data;
          this.totalRecord = data.paginator.totalRecord;
        });
      }
    }
  }
</script>