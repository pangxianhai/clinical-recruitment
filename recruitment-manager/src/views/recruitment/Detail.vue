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
                prop="categoryRes.categoryName"
                label="类目">
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
                prop="bidParty"
                label="申办方">
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
        <el-tabs style="margin-top: 30px">
            <el-tab-pane label="简介" v-html="recruitmentInfo.introduction">
            </el-tab-pane>
            <el-tab-pane label="治疗方案" v-html="recruitmentInfo.treatmentPlan"></el-tab-pane>
            <el-tab-pane label="入排标准" v-html="recruitmentInfo.entryCriteria"></el-tab-pane>
            <el-tab-pane label="患者权益" v-html="recruitmentInfo.patientRights"></el-tab-pane>
            <el-tab-pane label="研究科室">
                <el-row v-for="(department, index) in departmentList"
                        :key="index"
                        style="width: 400px"
                        type="flex" justify="space-between">
                    <el-col :span="10">
                        {{department.hospitalRes.address}}
                    </el-col>
                    <el-col :span="14">
                        {{department.hospitalRes.name}}
                    </el-col>
                    <el-col :span="12">
                        {{department.name}}
                    </el-col>
                </el-row>
            </el-tab-pane>
        </el-tabs>
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

    .recruitment-detail .el-table thead .cell {
        text-align: center;
    }

    .recruitment-detail .tips {
        margin: 10px 0 10px 0;
        color: #858585;
        font-weight: 500;
    }

    .recruitment-detail .el-tabs__content {
        color: #606266;
        font-size: 14px;
    }

    .recruitment-detail .button-panel {
        margin-top: 20px;
        text-align: center;
    }
</style>

<script>
  import RecruitmentApi from '@/api/RecruitmentApi';
  import {RecruitmentStatus} from '@/constants/Global';

  export default {
    data: function () {
      return {
        recruitmentInfo: {
          status: {}
        },
        departmentList: [],
        RecruitmentStatus: RecruitmentStatus
      }
    },
    created: function () {
      let recruitmentId = this.$route.params.recruitmentId;
      this.loadRecruitmentInfo(recruitmentId);
      this.loadRecruitmentDepartment(recruitmentId);
    },
    methods: {
      loadRecruitmentInfo: function (recruitmentId) {
        RecruitmentApi.getRecruitmentById(recruitmentId).then((recruitmentInfo) => {
          this.recruitmentInfo = recruitmentInfo;
        });
      },
      loadRecruitmentDepartment: function (recruitmentId) {
        RecruitmentApi.getRecruitmentDepartmentById(recruitmentId).then((departmentList) => {
          this.departmentList = departmentList;
        });
      },
      onBeginRecruitment: function () {
        if (this.recruitmentInfo.status.code === RecruitmentStatus.FINISHED) {
          this.$confirm('请注意结束时间，系统会根据结束时间自动结束项目！', '提示', {
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
            this.$message.success('操作成功!');
            this.loadRecruitmentInfo(recruitmentInfo.recruitmentId);
          }
        });
      },
      onEndRecruitment: function () {
        this.$confirm('您确定要执行该操作吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          RecruitmentApi.recruitmentEnd(this.recruitmentInfo.recruitmentId).then((success) => {
            if (success) {
              this.$message.success('操作成功!');
              this.loadRecruitmentInfo(this.recruitmentInfo.recruitmentId);
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
