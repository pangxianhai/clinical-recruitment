<template>
    <div class="recruitment-application-detail">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/recruitment/list' }">项目管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/recruitment/applicationList' }">申请记录
            </el-breadcrumb-item>
            <el-breadcrumb-item>申请详情</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="tips" style="margin-top: 30px">患者信息</div>
        <el-table
            :data="[recruitmentApplicationInfo]"
            border
            style="width: 100%">
            <el-table-column
                fixed
                prop="patientVO.userInfoVO.realName"
                label="姓名">
            </el-table-column>
            <el-table-column
                prop="patientVO.userInfoVO.phone"
                label="手机号">
            </el-table-column>
            <el-table-column
                prop="patientVO.userInfoVO.nickname"
                label="微信昵称">
            </el-table-column>
            <el-table-column
                prop="patientVO.userInfoVO.gender.desc"
                label="性别">
            </el-table-column>
            <el-table-column
                prop="patientVO.address"
                label="地址">
            </el-table-column>
            <el-table-column
                prop="patientVO.age"
                label="年龄">
            </el-table-column>
            <el-table-column
                prop="diseaseDesc"
                label="病症描述">
            </el-table-column>
            <el-table-column
                prop="geneticDiseaseDesc"
                label="遗传病描述">
            </el-table-column>
            <el-table-column
                label="状态">
                <template slot-scope="scope">
                    <el-tag>
                        {{scope.row.status.desc}}
                    </el-tag>
                </template>
            </el-table-column>
        </el-table>
        <div class="tips" style="margin-top: 30px" v-if="recruitmentApplicationInfo.doctorInfoVO">
            推荐医生
        </div>
        <el-table
            v-if="recruitmentApplicationInfo.doctorInfoVO"
            :data="[recruitmentApplicationInfo.doctorInfoVO]"
            border
            style="width: 100%">
            <el-table-column
                fixed
                prop="userInfoVO.realName"
                label="姓名">
            </el-table-column>
            <el-table-column
                prop="userInfoVO.phone"
                label="手机号">
            </el-table-column>
            <el-table-column
                prop="userInfoVO.nickname"
                label="微信昵称">
            </el-table-column>
            <el-table-column
                prop="userInfoVO.gender.desc"
                label="性别">
            </el-table-column>
            <el-table-column
                prop="address"
                label="地址">
            </el-table-column>
            <el-table-column
                prop="medicalInstitution"
                label="执业机构">
            </el-table-column>
            <el-table-column
                prop="medicalCategory"
                label="执业类别">
            </el-table-column>
        </el-table>
        <div class="tips" style="margin-top: 30px">项目信息</div>
        <el-table
            v-if="recruitmentApplicationInfo.recruitmentVO.recruitmentId"
            :data="[recruitmentApplicationInfo.recruitmentVO]"
            border
            style="width: 100%">
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
        <el-tabs v-if="recruitmentApplicationInfo.recruitmentVO.recruitmentId" type="border-card"
                 style="margin-top: 30px">
            <el-tab-pane label="简介">{{recruitmentApplicationInfo.recruitmentVO.introduction}}
            </el-tab-pane>
            <el-tab-pane label="治疗方案">{{recruitmentApplicationInfo.recruitmentVO.treatmentPlan}}
            </el-tab-pane>
            <el-tab-pane label="入排标准">{{recruitmentApplicationInfo.recruitmentVO.entryCriteria}}
            </el-tab-pane>
            <el-tab-pane label="患者权益">{{recruitmentApplicationInfo.recruitmentVO.patientRights}}
            </el-tab-pane>
            <el-tab-pane label="研究中心">
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
            </el-tab-pane>
        </el-tabs>
        <div v-if="!recruitmentApplicationInfo.recruitmentVO.recruitmentId" class="tips-value">
            {{recruitmentApplicationInfo.recruitmentVO.title}}
        </div>
    </div>
</template>
<style>
    .recruitment-application-detail .tips {
        margin: 10px 0 10px 0;
        color: #858585;
        font-weight: 500;
    }

    .recruitment-application-detail .tips-value {
        color: #606266;
        font-size: 14px;
        margin-left: 10px;
    }

    .recruitment-application-detail .el-tabs__content {
        color: #606266;
        font-size: 14px;
    }
</style>
<script>
  import {
    Breadcrumb,
    BreadcrumbItem,
    Button,
    Table,
    TableColumn,
    Tag,
    Tabs,
    TabPane,
    Row,
    Col,
  } from 'element-ui';
  import RecruitmentApplicationApi from '@/api/RecruitmentApplicationApi';
  import RecruitmentApi from '@/api/RecruitmentApi';
  import {RecruitmentStatus} from '@/constants/Global';

  export default {
    components: {
      [Breadcrumb.name]: Breadcrumb,
      [BreadcrumbItem.name]: BreadcrumbItem,
      [Button.name]: Button,
      [Table.name]: Table,
      [TableColumn.name]: TableColumn,
      [Tag.name]: Tag,
      [Tabs.name]: Tabs,
      [TabPane.name]: TabPane,
      [Row.name]: Row,
      [Col.name]: Col,
    },
    data: function () {
      return {
        RecruitmentStatus: RecruitmentStatus,
        recruitmentApplicationInfo: {
          recruitmentVO: {
            status: {}
          },
          patientVO: {},
          status: {},
          doctorInfoVO: {}
        },
        researchCenterList: []
      }
    },
    created: function () {
      let recruitmentApplicationId = this.$route.params.recruitmentApplicationId;
      this.loadRecruitmentApplication(recruitmentApplicationId);
    },
    methods: {
      loadRecruitmentApplication: function (recruitmentApplicationId) {
        RecruitmentApplicationApi.getRecruitmentApplicationDetail(recruitmentApplicationId).then(
            applicationInfo => {
              this.recruitmentApplicationInfo = applicationInfo;
              if (applicationInfo.recruitmentVO.recruitmentId) {
                this.loadRecruitmentCenter(applicationInfo.recruitmentVO.recruitmentId);
              }
            });
      },
      loadRecruitmentCenter: function (recruitmentId) {
        RecruitmentApi.getRecruitmentCenterById(recruitmentId).then((researchCenterList) => {
          this.researchCenterList = researchCenterList;
        })
      },
    }
  }
</script>

