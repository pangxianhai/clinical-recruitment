<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>项目管理</el-breadcrumb-item>
            <el-breadcrumb-item>项目列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-table
            :data="recruitmentList"
            border
            height="500"
            style="width: 100%">
            <el-table-column
                fixed
                prop="title"
                width="150"
                label="标题">
            </el-table-column>
            <el-table-column
                prop="registerCode"
                label="登记编号">
            </el-table-column>
            <el-table-column
                prop="practiceStages"
                label="实验分期">
            </el-table-column>
            <el-table-column
                prop="indication"
                label="适应症">
            </el-table-column>
            <el-table-column
                prop="drugType"
                label="药物类型">
            </el-table-column>
            <el-table-column
                prop="drugName"
                label="药物名称">
            </el-table-column>
            <el-table-column
                prop="recruitmentNumber"
                label="招募人数">
            </el-table-column>
            <el-table-column
                prop="status.desc"
                label="招募状态">
            </el-table-column>
            <el-table-column
                width="200"
                label="启截时间">
                <template slot-scope="scope">
                    <span>{{ scope.row.startTime }}~{{ scope.row.stopTime }}</span>
                </template>
            </el-table-column>
            <el-table-column
                width="100"
                fixed="right"
                label="操作">
                <template slot-scope="scope">
                    <div class="operator-panel">
                        <el-row type="flex">
                            <el-col>
                                <el-button
                                    type="primary"
                                    size="mini">
                                    详情
                                </el-button>
                            </el-col>
                        </el-row>
                        <el-row type="flex">
                            <el-col>
                                <el-button
                                    size="mini"
                                    type="success">编辑
                                </el-button>
                            </el-col>
                        </el-row>
                        <el-row type="flex">
                            <el-col>
                                <el-button
                                    size="mini"
                                    type="warning">冻结
                                </el-button>
                            </el-col>
                        </el-row>
                        <el-row type="flex">
                            <el-col>
                                <el-button
                                    size="mini"
                                    type="danger">删除
                                </el-button>
                            </el-col>
                        </el-row>
                    </div>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @current-change="loadRecruitmentInfo"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="totalRecord">
        </el-pagination>
    </div>
</template>

<style>
    .el-table {
        margin-top: 20px;
    }

    .el-table th.is-leaf {
        background: #858585;
        color: #FFF;
    }

    .el-table thead .cell {
        text-align: center;
    }

    .el-pagination {
        float: right;
    }

    .operator-panel .el-row {
        margin-top: 5px;
    }
</style>

<script>
  import {
    Breadcrumb,
    BreadcrumbItem,
    Table,
    TableColumn,
    Button,
    Pagination,
    Row,
    Col,
  } from 'element-ui';
  import RecruitmentApi from '@/api/RecruitmentApi';

  export default {
    components: {
      [Breadcrumb.name]: Breadcrumb,
      [BreadcrumbItem.name]: BreadcrumbItem,
      [Table.name]: Table,
      [TableColumn.name]: TableColumn,
      [Button.name]: Button,
      [Pagination.name]: Pagination,
      [Row.name]: Row,
      [Col.name]: Col,
    },
    data: function () {
      return {
        recruitmentList: [],
        currentPage: 1,
        totalRecord: 0,
        pageSize: 10,
      }
    },
    created: function () {
      this.loadRecruitmentInfo();
    },
    methods: {
      loadRecruitmentInfo: function () {
        RecruitmentApi.getRecruitment({
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }).then(data => {
          this.recruitmentList = data.data;
          this.totalRecord = data.paginator.totalRecord;
        });
      }
    }
  }
</script>
