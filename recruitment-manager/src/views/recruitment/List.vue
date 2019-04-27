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
                width="162"
                prop="createdTime"
                label="添加时间">
            </el-table-column>
            <el-table-column
                width="100"
                fixed="right"
                label="操作">
                <template slot-scope="scope">
                    <div class="operator-panel">
                        <el-row type="flex">
                            <el-tooltip effect="dark" content="详情" placement="bottom">
                                <el-col>
                                    <el-button
                                        icon="el-icon-more"
                                        type="primary"
                                        size="mini" circle>
                                    </el-button>
                                </el-col>
                            </el-tooltip>
                            <el-tooltip effect="dark" content="编辑" placement="bottom">
                                <el-col>
                                    <el-button
                                        icon="el-icon-edit"
                                        size="mini"
                                        type="success" circle>
                                    </el-button>
                                </el-col>
                            </el-tooltip>
                        </el-row>
                        <el-row type="flex">
                            <el-tooltip effect="dark" content="冻结" placement="bottom">
                                <el-col>
                                    <el-button
                                        icon="el-icon-goods"
                                        size="mini"
                                        type="warning" circle>
                                    </el-button>
                                </el-col>
                            </el-tooltip>
                            <el-tooltip effect="dark" content="删除" placement="bottom">
                                <el-col>
                                    <el-button
                                        icon="el-icon-delete"
                                        size="mini"
                                        type="danger" circle>
                                    </el-button>
                                </el-col>
                            </el-tooltip>
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
    Tooltip,
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
      [Tooltip.name]: Tooltip,

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
