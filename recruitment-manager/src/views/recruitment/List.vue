<template>
    <div class="recruitment-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>项目管理</el-breadcrumb-item>
            <el-breadcrumb-item>项目列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form ref="queryInfo" :model="queryInfo" :inline="true">
            <el-form-item label="搜索文本:" prop="queryText">
                <el-input v-model="queryInfo.queryText" size="mini" placeholder="搜索标题、登记编号、适应症"
                          clearable></el-input>
            </el-form-item>
            <el-form-item label="状态:" prop="status">
                <el-select v-model="queryInfo.status" size="mini" clearable placeholder="状态">
                    <el-option label="未开始" value="0"></el-option>
                    <el-option label="进行中" value="1"></el-option>
                    <el-option label="已结束" value="2"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="开始时间段:" prop="startTime">
                <el-date-picker
                    size="mini"
                    v-model="queryInfo.startTime"
                    type="daterange"
                    align="right"
                    unlink-panels
                    value-format="yyyy-MM-dd"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="结束时间段:" prop="stopTime">
                <el-date-picker
                    size="mini"
                    v-model="queryInfo.stopTime"
                    type="daterange"
                    align="right"
                    unlink-panels
                    value-format="yyyy-MM-dd"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item>
                <el-button size="mini" type="primary" @click="loadRecruitmentInfo()"
                           icon="el-icon-search">查询
                </el-button>
            </el-form-item>
        </el-form>
        <el-table
            :data="recruitmentList"
            border
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
                label="试验分期">
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
                            <el-tooltip effect="dark" content="详情" placement="bottom"
                                        :hide-after="500">
                                <el-col>
                                    <el-button
                                        icon="el-icon-more"
                                        type="primary"
                                        @click="onRecruitmentDetail(scope.row)"
                                        size="mini" circle>
                                    </el-button>
                                </el-col>
                            </el-tooltip>
                        </el-row>
                        <el-row type="flex">
                            <el-tooltip effect="dark" content="编辑" placement="bottom"
                                        :hide-after="500">
                                <el-col>
                                    <el-button
                                        icon="el-icon-edit"
                                        size="mini"
                                        @click="onRecruitmentUpdate(scope.row)"
                                        type="success" circle>
                                    </el-button>
                                </el-col>
                            </el-tooltip>
                        </el-row>
                        <el-row type="flex">
                            <el-tooltip v-if="scope.row.status.code===RecruitmentStatus.IN_PROCESS"
                                        effect="dark" content="结束" placement="bottom"
                                        :hide-after="500">
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
                                        effect="dark" content="开始" placement="bottom"
                                        :hide-after="500">
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
    .recruitment-list .el-form {
        margin-top: 10px;
        padding: 10px 8px 10px 8px;
        background: #dddef5;
        border-radius: 4px;
    }

    .recruitment-list .el-table {
        margin-top: 20px;
    }

    .recruitment-list .el-table th.is-leaf {
        color: #858585;
    }

    .recruitment-list .el-table thead .cell {
        text-align: center;
    }

    .recruitment-list .el-pagination {
        float: right;
    }

    .recruitment-list .operator-panel {
        text-align: center;
    }

    .recruitment-list .operator-panel .el-row {
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
    Message,
    Form,
    FormItem,
    Input,
    Select,
    Option,
    DatePicker
  } from 'element-ui';
  import RecruitmentApi from '@/api/RecruitmentApi';
  import {RecruitmentStatus} from '@/constants/Global';

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
      [Form.name]: Form,
      [FormItem.name]: FormItem,
      [Input.name]: Input,
      [Select.name]: Select,
      [Option.name]: Option,
      [DatePicker.name]: DatePicker,
    },
    data: function () {
      return {
        RecruitmentStatus: RecruitmentStatus,
        recruitmentList: [],
        currentPage: 1,
        totalRecord: 0,
        pageSize: 10,
        queryInfo: {
          startTime: [],
          stopTime: []
        }
      }
    },
    created: function () {
      Object.assign(this.queryInfo, this.$route.query);
      this.loadRecruitmentInfo();
    },
    methods: {
      loadRecruitmentInfo: function () {
        this.$router.replace({
          query: Object.assign(this.queryInfo, {
            currentPage: this.currentPage,
            pageSize: this.pageSize
          })
        });
        RecruitmentApi.getRecruitment(Object.assign({
          startTimeBegin: this.queryInfo.startTime[0],
          startTimeEnd: this.queryInfo.startTime[1],
          stopTimeBegin: this.queryInfo.stopTime[0],
          stopTimeEnd: this.queryInfo.stopTime[1],
        }, this.queryInfo)).then(data => {
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
            }
          });
        }).catch(() => {

        });
      },
      onRecruitmentDetail: function (recruitmentInfo) {
        this.$router.push({
          path: `/recruitment/detail/${recruitmentInfo.recruitmentId}`
        });
      },
      onRecruitmentUpdate: function (recruitmentInfo) {
        this.$router.push({
          path: `/recruitment/update/${recruitmentInfo.recruitmentId}`
        });
      }
    }
  }
</script>
