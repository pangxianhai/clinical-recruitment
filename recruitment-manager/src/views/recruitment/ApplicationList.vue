<template>
    <div class="recruitment-application-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/recruitment/list' }">项目管理</el-breadcrumb-item>
            <el-breadcrumb-item>申请记录</el-breadcrumb-item>
        </el-breadcrumb>
        <el-table
            :data="recruitmentApplicationList"
            border
            style="width: 100%">
            <el-table-column
                fixed
                prop="recruitmentVO.title"
                label="项目标题">
            </el-table-column>
            <el-table-column
                prop="patientVO.userInfoVO.realName"
                label="患者姓名">
            </el-table-column>
            <el-table-column
                prop="diseaseDesc"
                label="病症描述">
            </el-table-column>
            <el-table-column
                prop="doctorInfoVO.userInfoVO.realName"
                label="推荐医生">
            </el-table-column>
            <el-table-column
                prop="status.desc"
                label="状态">
            </el-table-column>
            <el-table-column
                prop="applicationTime"
                label="申请时间">
            </el-table-column>
            <el-table-column
                width="60"
                fixed="right"
                label="操作">
                <template slot-scope="scope">
                    <div class="operator-panel">
                        <el-row type="flex">
                            <el-tooltip effect="dark" content="详情" :hide-after="500"
                                        placement="bottom">
                                <el-col>
                                    <el-button
                                        icon="el-icon-more"
                                        type="primary"
                                        @click="onRecruitmentApplicationDetail(scope.row)"
                                        size="mini" circle>
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
    .recruitment-application-list .el-table {
        margin-top: 20px;
    }

    .recruitment-application-list .el-table th.is-leaf {
        color: #858585;
    }

    .recruitment-application-list .el-table .cell {
        text-align: center;
    }

    .recruitment-application-list .el-pagination {
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
    Row,
    Col,
    Tooltip,
    Tag,
    Button,
  } from 'element-ui';
  import RecruitmentApplicationApi from '@/api/RecruitmentApplicationApi';

  export default {
    components: {
      [Breadcrumb.name]: Breadcrumb,
      [BreadcrumbItem.name]: BreadcrumbItem,
      [Table.name]: Table,
      [TableColumn.name]: TableColumn,
      [Pagination.name]: Pagination,
      [Row.name]: Row,
      [Col.name]: Col,
      [Tooltip.name]: Tooltip,
      [Tag.name]: Tag,
      [Button.name]: Button,
    },
    data: function () {
      return {
        recruitmentApplicationList: [],
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
        RecruitmentApplicationApi.getRecruitmentApplication({
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }).then(data => {
          this.recruitmentApplicationList = data.data;
          this.totalRecord = data.paginator.totalRecord;
        });
      },
      onRecruitmentApplicationDetail: function (recruitmentApplication) {
        this.$router.push({
          path: `/recruitment/applicationDetail/${recruitmentApplication.applicationId}`
        });
      }
    }
  }
</script>
