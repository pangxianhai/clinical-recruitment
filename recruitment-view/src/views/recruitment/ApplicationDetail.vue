<template>
    <div class="application-detail">
        <van-nav-bar title="申请记录详情" left-arrow @click-left="onGoBack">
        </van-nav-bar>
        <van-panel class="recruitment-panel" :title="recruitmentInfo.title">
            <van-row type="flex" v-if="recruitmentInfo.recruitmentId">
                <van-col span="5">登记编号:</van-col>
                <van-col span="8">{{recruitmentInfo.registerCode}}</van-col>
                <van-col span="5">实验分期:</van-col>
                <van-col span="3">{{recruitmentInfo.practiceStages}}</van-col>
            </van-row>
            <van-row type="flex" v-if="recruitmentInfo.recruitmentId">
                <van-col span="5">药物类型:</van-col>
                <van-col span="19">{{recruitmentInfo.drugType}}</van-col>
            </van-row>
            <van-row type="flex" v-if="recruitmentInfo.recruitmentId">
                <van-col span="5">药物名称:</van-col>
                <van-col span="19">{{recruitmentInfo.drugName}}</van-col>
            </van-row>
            <van-row type="flex" v-if="recruitmentInfo.recruitmentId">
                <van-col span="5">适应症:</van-col>
                <van-col span="19">{{recruitmentInfo.indication}}</van-col>
            </van-row>
            <van-row type="flex">
                <van-col span="5">患者姓名:</van-col>
                <van-col span="8">
                    <span v-if="patientInfo.userInfoVO">{{patientInfo.userInfoVO.realName}}</span>
                </van-col>
                <van-col span="5">患者性别:</van-col>
                <van-col span="3">
                    <span v-if="patientInfo.userInfoVO">
                        {{patientInfo.userInfoVO.gender.desc}}
                    </span>
                </van-col>
            </van-row>
            <van-row type="flex">
                <van-col span="5">手机号:</van-col>
                <van-col span="8">
                    <span v-if="patientInfo.userInfoVO">{{patientInfo.userInfoVO.phone}}</span>
                </van-col>
                <van-col span="5">患者年龄:</van-col>
                <van-col span="3">{{patientInfo.age}}</van-col>
            </van-row>
            <van-row type="flex">
                <van-col span="5">患者地址:</van-col>
                <van-col span="19">{{patientInfo.address}}</van-col>
            </van-row>
            <van-row type="flex">
                <van-col span="5">申请状态:</van-col>
                <van-col span="3">{{applicationInfo.status.desc}}</van-col>
            </van-row>
        </van-panel>
        <van-panel :title="recruitmentInfo.recruitmentId ? '病例描述' : '病史表述'">
            <p class="disease-desc">{{applicationInfo.diseaseDesc}}</p>
        </van-panel>
        <van-panel v-if="! recruitmentInfo.recruitmentId" title="遗传病表述">
            <p class="disease-desc">{{applicationInfo.geneticDiseaseDesc}}</p>
        </van-panel>
        <van-panel v-if="applicationInfo.diseaseImageList" title="病例图片" style="overflow: hidden">
            <div style="width: 33.3%;text-align: center; float: left"
                 v-for="(image,index) in applicationInfo.diseaseImageList"
                 :key="index">
                <img width="80" height="80"
                     :src="image.thumbnailUrl"
                     @click="previewImage(index)"/>
            </div>
        </van-panel>
        <van-panel class="recruitment-panel" title="推荐医生信息"
                   v-if="showRecommendDoctor && doctorInfo">
            <van-row type="flex">
                <van-col span="5">医生姓名:</van-col>
                <van-col span="8">{{doctorInfo.userInfoVO.realName}}</van-col>
                <van-col span="5">医生性别:</van-col>
                <van-col span="3">{{doctorInfo.userInfoVO.gender.desc}}</van-col>
            </van-row>
            <van-row type="flex">
                <van-col span="5">执业机构:</van-col>
                <van-col span="19">{{doctorInfo.medicalInstitution}}</van-col>
            </van-row>
            <van-row type="flex">
                <van-col span="5">执业类别:</van-col>
                <van-col span="19">{{doctorInfo.medicalCategory}}</van-col>
            </van-row>
            <van-row type="flex">
                <van-col span="5">地址:</van-col>
                <van-col span="19">{{doctorInfo.address}}</van-col>
            </van-row>
        </van-panel>
        <van-panel title="项目简介" v-if="recruitmentInfo.recruitmentId">
            <p class="disease-desc">{{recruitmentInfo.introduction}}</p>
        </van-panel>
        <van-tabs :v-model="1" v-if="recruitmentInfo.recruitmentId">
            <van-tab title="治疗方案">
                <div class="tabs-value">{{recruitmentInfo.treatmentPlan}}</div>
            </van-tab>
            <van-tab title="入排标准">
                <div class="tabs-value">{{recruitmentInfo.entryCriteria}}</div>
            </van-tab>
            <van-tab title="患者权益">
                <div class="tabs-value">{{recruitmentInfo.patientRights}}</div>
            </van-tab>
            <van-tab title="研究中心">
                <van-list
                    :v-model="false"
                    :finished="true">
                    <van-cell
                        v-for="(center,index) in researchCenterList"
                        :key="index"
                        :title="center.name" :value="center.address">
                    </van-cell>
                </van-list>
            </van-tab>
        </van-tabs>
    </div>
</template>
<style>
    .recruitment-panel {
        padding: 5px 10px 8px 10px;
    }

    .recruitment-panel .van-panel__header {
        padding: 0;
    }

    .recruitment-panel .van-col {
        font-size: 12px;
    }

    .recruitment-panel .van-panel__content .van-row {
        line-height: 22px;
        color: #888;
    }

    .disease-desc {
        font-size: 14px;
        color: #555;
        margin: 10px;
        padding-left: 5px;
    }

    .tabs-value {
        margin: 10px 10px 15px 10px;
        color: #555;
        font-size: 12px;
    }
</style>
<script>
  import {NavBar, Panel, Row, Col, ImagePreview, Tab, Tabs, Cell, List} from 'vant';
  import {UserConstants} from '@/constants/Global';
  import RecruitmentApi from '@/api/RecruitmentApi';
  import UserApi from '@/api/UserApi';

  export default {
    components: {
      [NavBar.name]: NavBar,
      [Panel.name]: Panel,
      [Row.name]: Row,
      [Col.name]: Col,
      [Tab.name]: Tab,
      [Tabs.name]: Tabs,
      [Cell.name]: Cell,
      [List.name]: List,
    },
    data: function () {
      return {
        applicationInfo: {
          status: {},
        },
        diseaseImageList: [],
        recruitmentInfo: {},
        researchCenterList: [],
        patientInfo: {
          userInfoVO: {
            gender: {}
          }
        },
        doctorInfo: undefined,
        UserConstants: UserConstants,
        currentUser: {},
        showRecommendDoctor: true
      }
    },
    created: function () {
      let applicationId = this.$route.params.applicationId;
      this.onLoadApplicationInfo(applicationId);
      this.onLoadCurrentUserInfo();
    },
    methods: {
      onGoBack: function () {
        let redirectURL = this.$route.query.redirectURL;
        if (typeof redirectURL === 'undefined' || redirectURL.length <= 0) {
          redirectURL = '/recruitment/applicationList';
        }
        this.$router.push({path: redirectURL});
      },
      onLoadApplicationInfo: function (applicationId) {
        RecruitmentApi.getRecruitmentApplicationById(applicationId).then(applicationInfo => {
          this.applicationInfo = applicationInfo;
          this.recruitmentInfo = applicationInfo.recruitmentVO;
          this.patientInfo = applicationInfo.patientVO;
          this.doctorInfo = applicationInfo.doctorInfoVO;
          this.onLoadRecruitmentCenter(applicationInfo.recruitmentId);
        });
      },
      onLoadCurrentUserInfo: function () {
        UserApi.getLogInfo().then((userInfo) => {
          this.currentUser = userInfo;
          this.showRecommendDoctor = userInfo.userType.code === UserConstants.DOCTOR;
        });
      },
      onLoadRecruitmentCenter: function (recruitmentId) {
        if (typeof recruitmentId === 'undefined') {
          return;
        }
        RecruitmentApi.getRecruitmentCenterById(recruitmentId).then(centerList => {
          this.researchCenterList = centerList;
        });
      },
      previewImage: function (index) {
        let imageList = [];
        this.applicationInfo.diseaseImageList.forEach((imageInfo) => {
          imageList.push(imageInfo.imageUrl);
        });
        ImagePreview({
          images: imageList,
          startPosition: index
        });
      }
    }
  }
</script>
