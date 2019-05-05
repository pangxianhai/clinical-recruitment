<template>
    <div class="recruitment-detail">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>项目管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/recruitment/list' }">项目列表</el-breadcrumb-item>
            <el-breadcrumb-item>项目详情</el-breadcrumb-item>
        </el-breadcrumb>
        <el-table
            :data="[recruitmentInfo]"
            border
            style="width: 100%;margin-top: 15px">
            <el-table-column
                prop="title"
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
                prop="drugName"
                label="药物名称">
            </el-table-column>
            <el-table-column
                prop="drugType"
                label="药物类型">
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
        </el-table>
        <div class="tips">简介</div>
        <div class="tips-value">{{recruitmentInfo.introduction}}</div>
        <div class="tips">治疗方案</div>
        <div class="tips-value">{{recruitmentInfo.treatmentPlan}}</div>
        <div class="tips">入排标准</div>
        <div class="tips-value">{{recruitmentInfo.entryCriteria}}</div>
        <div class="tips">患者权益</div>
        <div class="tips-value">{{recruitmentInfo.patientRights}}</div>
        <div class="tips">研究中心</div>
        <div class="tips-value">
            <el-row v-for="(center, index) in researchCenterList" :key="index"
                    style="width: 400px"
                    type="flex" justify="space-between">
                <el-col :span="10">
                    {{center.address}}
                </el-col>
                <el-col :span="14">
                    {{center.name}}
                </el-col>
            </el-row>
        </div>
        <div class="button-panel">
            <el-row type="flex" style="width: 200px;margin: auto">
                <el-col>
                    <el-button
                        icon="el-icon-edit"
                        size="mini"
                        @click="onRecruitmentUpdate()"
                        type="success">
                        编辑
                    </el-button>
                </el-col>
                <el-col v-if="recruitmentInfo.status.code===RecruitmentStatus.IN_PROCESS">
                    <el-button
                        icon="el-icon-goods"
                        size="mini"
                        type="danger"
                        @click="onEndRecruitment()">
                        结束
                    </el-button>
                </el-col>
                <el-col v-if="recruitmentInfo.status.code!==RecruitmentStatus.IN_PROCESS">
                    <el-button
                        icon="el-icon-sold-out"
                        size="mini"
                        type="warning"
                        @click="onBeginRecruitment()">
                        开始
                    </el-button>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<style>
    .recruitment-detail .el-table {
        margin-top: 20px;
    }

    .recruitment-detail .el-table th.is-leaf {
        color: #858585;
    }

    .recruitment-detail .el-table thead .cell {
        text-align: center;
    }

    .recruitment-detail .tips {
        margin: 10px 0 10px 0;
        color: #858585;
        font-weight: 500;
    }

    .recruitment-detail .tips-value {
        color: #606266;
        font-size: 14px;
        margin-left: 10px;
    }

    .recruitment-detail .button-panel {
        margin-top: 20px;
        text-align: center;
    }
</style>

<script>
  import {
    Breadcrumb,
    BreadcrumbItem,
    Table,
    TableColumn,
    Tooltip,
    Tag,
    Row,
    Col,
    Button,
    MessageBox,
    Message
  } from 'element-ui';
  import RecruitmentApi from '@/api/RecruitmentApi';
  import {RecruitmentStatus} from '@/constants/Global';

  export default {
    components: {
      [Breadcrumb.name]: Breadcrumb,
      [BreadcrumbItem.name]: BreadcrumbItem,
      [Table.name]: Table,
      [TableColumn.name]: TableColumn,
      [Tooltip.name]: Tooltip,
      [Tag.name]: Tag,
      [Row.name]: Row,
      [Col.name]: Col,
      [Button.name]: Button,

    },
    data: function () {
      return {
        recruitmentInfo: {
          status: {}
        },
        researchCenterList: [],
        RecruitmentStatus: RecruitmentStatus
      }
    },
    created: function () {
      let recruitmentId = this.$route.params.recruitmentId;
      this.loadRecruitmentInfo(recruitmentId);
      this.loadRecruitmentCenter(recruitmentId);
    },
    methods: {
      loadRecruitmentInfo: function (recruitmentId) {
        RecruitmentApi.getRecruitmentById(recruitmentId).then((recruitmentInfo) => {
          this.recruitmentInfo = recruitmentInfo;
        });
      },
      loadRecruitmentCenter: function (recruitmentId) {
        RecruitmentApi.getRecruitmentCenterById(recruitmentId).then((researchCenterList) => {
          this.researchCenterList = researchCenterList;
        })
      },
      onBeginRecruitment: function () {
        if (this.recruitmentInfo.status.code === RecruitmentStatus.FINISHED) {
          MessageBox.confirm('请注意结束时间，系统会根据结束时间自动结束项目！', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.beginRecruitment(this.recruitmentInfo);
          }).catch(() => {

          });
        } else {
          this.beginRecruitment(this.recruitmentInfo);
        }
      },
      beginRecruitment: function (recruitmentInfo) {
        RecruitmentApi.recruitmentBegin(recruitmentInfo.recruitmentId).then((success) => {
          if (success) {
            Message.success('操作成功!');
            this.loadRecruitmentInfo(recruitmentInfo.recruitmentId);
          } else {
            Message.error('操作失败');
          }
        });
      },
      onEndRecruitment: function () {
        MessageBox.confirm('您确定要执行该操作吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          RecruitmentApi.recruitmentEnd(this.recruitmentInfo.recruitmentId).then((success) => {
            if (success) {
              Message.success('操作成功!');
              this.loadRecruitmentInfo(this.recruitmentInfo.recruitmentId);
            } else {
              Message.error('操作失败');
            }
          });
        }).catch(() => {

        });
      },
      onRecruitmentUpdate: function () {
        this.$router.push({
          path: `/recruitment/update/${this.recruitmentInfo.recruitmentId}`
        });
      }
    }
  }
</script>
