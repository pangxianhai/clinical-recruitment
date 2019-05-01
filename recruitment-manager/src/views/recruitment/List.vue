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
                label="招募状态">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.status.code===RecruitmentStatus.FINISHED" type="info">
                        {{scope.row.status.desc}}
                    </el-tag>
                    <el-tag v-if="scope.row.status.code===RecruitmentStatus.NOT_BEGIN"
                            type="warning">
                        {{scope.row.status.desc}}
                    </el-tag>
                    <el-tag v-if="scope.row.status.code===RecruitmentStatus.IN_PROCESS">
                        {{scope.row.status.desc}}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column
                width="200"
                label="启截时间">
                <template slot-scope="scope">
                    <span>{{ scope.row.startTime }}~{{ scope.row.stopTime }}</span>
                </template>
            </el-table-column>
            <el-table-column
                width="163"
                prop="createdTime"
                label="添加时间">
            </el-table-column>
            <el-table-column
                width="60"
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
                        </el-row>
                        <el-row type="flex">
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
                            <el-tooltip v-if="scope.row.status.code===RecruitmentStatus.IN_PROCESS"
                                        effect="dark" content="结束" placement="bottom">
                                <el-col>
                                    <el-button
                                        icon="el-icon-goods"
                                        size="mini"
                                        type="danger" circle
                                        @click="onEndRecruitment(scope.row)">
                                    </el-button>
                                </el-col>
                            </el-tooltip>
                            <el-tooltip v-if="scope.row.status.code!==RecruitmentStatus.IN_PROCESS"
                                        effect="dark" content="开始" placement="bottom">
                                <el-col>
                                    <el-button
                                        icon="el-icon-sold-out"
                                        size="mini"
                                        type="warning" circle
                                        @click="onBeginRecruitment(scope.row)">
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

    .operator-panel {
        text-align: center;
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
    Tag,
    MessageBox,
    Message
  } from 'element-ui';
  import RecruitmentApi from '@/api/RecruitmentApi';
  import {RecruitmentStatus} from '@/constants/Global';
  import {RouterUtil} from '@/util/Util';

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
      [Tag.name]: Tag,

    },
    data: function () {
      return {
        RecruitmentStatus: RecruitmentStatus,
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
      },
      onBeginRecruitment: function (recruitmentInfo) {
        if (recruitmentInfo.status.code === RecruitmentStatus.FINISHED) {
          MessageBox.confirm('请注意结束时间，系统会根据结束时间自动结束项目！', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.beginRecruitment(recruitmentInfo);
          }).catch(() => {

          });
        } else {
          this.beginRecruitment(recruitmentInfo);
        }
      },
      beginRecruitment: function (recruitmentInfo) {
        RecruitmentApi.recruitmentBegin(recruitmentInfo.recruitmentId).then((success) => {
          if (success) {
            Message.success('操作成功!');
            this.loadRecruitmentInfo();
          } else {
            Message.error('操作失败');
          }
        });
      },
      onEndRecruitment: function (recruitmentInfo) {
        MessageBox.confirm('您确定要执行该操作吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          RecruitmentApi.recruitmentEnd(recruitmentInfo.recruitmentId).then((success) => {
            if (success) {
              Message.success('操作成功!');
              this.loadRecruitmentInfo();
            } else {
              Message.error('操作失败');
            }
          });
        }).catch(() => {

        });
      }
    }
  }
</script>
