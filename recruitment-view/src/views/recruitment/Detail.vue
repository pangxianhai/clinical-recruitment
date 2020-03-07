<template>
    <div class="recruitment-detail">
        <van-nav-bar title="项目详情" left-arrow @click-right="onRecruitmentRecommend"
                     @click-left="onGoBack">
            <van-icon name="share" slot="right"></van-icon>
        </van-nav-bar>
        <van-panel class="recruitment-panel" :title="recruitmentInfo.title">
            <van-row type="flex">
                <van-col span="5">登记编号:</van-col>
                <van-col span="8">{{recruitmentInfo.registerCode}}</van-col>
                <van-col span="5">试验分期:</van-col>
                <van-col span="3">{{recruitmentInfo.practiceStages}}</van-col>
            </van-row>
            <van-row type="flex">
                <van-col span="5">药物类型:</van-col>
                <van-col span="19">{{recruitmentInfo.drugType}}</van-col>
            </van-row>
            <van-row type="flex">
                <van-col span="5">药物名称:</van-col>
                <van-col span="19">{{recruitmentInfo.drugName}}</van-col>
            </van-row>
            <van-row type="flex">
                <van-col span="5">适应症:</van-col>
                <van-col span="19">{{recruitmentInfo.indication}}</van-col>
            </van-row>
            <van-row type="flex">
                <van-col span="5">申办方:</van-col>
                <van-col span="19">{{recruitmentInfo.bidParty}}</van-col>
            </van-row>
            <van-row type="flex">
                <van-col span="5">招募人数:</van-col>
                <van-col span="8">{{recruitmentInfo.recruitmentNumber}}人</van-col>
                <van-col span="5">招募状态:</van-col>
                <van-col span="3">
                    <van-tag style="min-width: 38px" round
                             v-if="RecruitmentStatus.FINISHED===recruitmentInfo.status.code">
                        {{recruitmentInfo.status.desc}}
                    </van-tag>
                    <van-tag style="min-width: 38px" round type="primary" plain
                             v-if="RecruitmentStatus.NOT_BEGIN===recruitmentInfo.status.code">
                        {{recruitmentInfo.status.desc}}
                    </van-tag>
                    <van-tag style="min-width: 38px" round type="primary"
                             v-if="RecruitmentStatus.IN_PROCESS===recruitmentInfo.status.code">
                        {{recruitmentInfo.status.desc}}
                    </van-tag>
                </van-col>
            </van-row>
            <van-row type="flex">
                <van-col span="5">起至时间:</van-col>
                <van-col span="19">{{recruitmentInfo.startTime}}~{{recruitmentInfo.stopTime}}
                </van-col>
            </van-row>
        </van-panel>
        <div class="introduction" v-html="recruitmentInfo.introduction">
        </div>
        <van-tabs :v-model="1" scrollspy sticky>
            <van-tab title="治疗方案">
                <van-divider>治疗方案</van-divider>
                <div class="tabs-value" v-html="recruitmentInfo.treatmentPlan"></div>
            </van-tab>
            <van-tab title="入排标准">
                <van-divider>入排标准:</van-divider>
                <div class="tabs-value" v-html="recruitmentInfo.entryCriteria"></div>
            </van-tab>
            <van-tab title="患者权益">
                <van-divider>患者权益:</van-divider>
                <div class="tabs-value" v-html="recruitmentInfo.patientRights"></div>
            </van-tab>
            <van-tab title="研究中心">
                <van-divider>研究中心:</van-divider>
                <van-list
                    :v-model="false"
                    :finished="true">
                    <van-cell
                        v-for="(department,index) in recruitmentInfo.departmentDetailResList"
                        :key="index"
                        :title="department.organizationRes.name + '-' + department.name"
                        :value="department.organizationRes.address">
                    </van-cell>
                </van-list>
            </van-tab>
        </van-tabs>
        <van-goods-action :safe-area-inset-bottom="true" style="position: sticky;">
            <van-goods-action-icon icon="service-o" text="联系我们" color="#1989fa"
                                   @click="onContactUs"/>
            <van-goods-action-icon icon="share" text="推荐" @click="onRecruitmentRecommend"/>
            <van-goods-action-button icon="qr" type="warning" text="推荐二维码"
                                     @click="onRecommendQrcode"/>
            <van-goods-action-button icon="chat-o" type="danger" text="我要参加"
                                     @click="onRecruitmentApplication"/>
        </van-goods-action>
        <van-popup v-model="recommendQrcode"
                   position="bottom"
                   :overlay="true"
                   :lazy-render="false"
                   class="recommend-qrcode">
            <van-panel :title="recruitmentInfo.title">
            </van-panel>
            <div class="qrcode">
                <vue-qr :text="recommendQrcodeValue" :size="320"
                        :logoSrc="require('@/assets/headimg.jpeg')"></vue-qr>
            </div>
            <van-row style="margin-bottom: 8px">
                <van-col span="9" offset="2">
                    <van-button size="normal" type="warning" block
                                @click="recommendQrcode=false">取消
                    </van-button>
                </van-col>
                <van-col span="9" offset="2">
                    <van-button size="normal" type="info" block @click="recommendQrcode=false">
                        确定
                    </van-button>
                </van-col>
            </van-row>
        </van-popup>
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

    .introduction {
        margin: 10px 10px 15px 10px;
        color: #555;
        font-size: 12px;
    }

    .tabs-value {
        margin: 10px 10px 15px 10px;
        color: #555;
        font-size: 12px;
    }

</style>
<script>
  import RecruitmentApi from "@/api/RecruitmentApi";
  import {UserConstants, RecruitmentStatus, ApplicationAction} from '@/constants/Global';
  import UserApi from '@/api/UserApi';

  export default {
    data: function () {
      return {
        recruitmentInfo: {
          recruitmentId: '',
          status: {}
        },
        UserConstants: UserConstants,
        RecruitmentStatus: RecruitmentStatus,
        recommendQrcode: false,
        recommendQrcodeValue: ''
      }
    },
    created: function () {
      let recruitmentId = this.$route.params.recruitmentId;
      this.onLoadRecruitmentInfo(recruitmentId);
    },
    methods: {
      onGoBack: function () {
        let redirectURL = this.$route.query.redirectURL;
        if (typeof redirectURL === 'undefined' || redirectURL.length <= 0) {
          redirectURL = '/recruitment/list';
        }
        this.$router.push({path: String(redirectURL)}, function () {

        });
      },
      onLoadRecruitmentInfo: function (recruitmentId) {
        if (typeof recruitmentId === 'undefined') {
          return;
        }
        RecruitmentApi.getRecruitmentById(recruitmentId).then(recruitmentInfo => {
          this.recruitmentInfo = recruitmentInfo;
        });
      },
      onContactUs: function () {
        this.$router.push({
          path: '/site/contactUs',
          query: {
            redirectURL: this.$route.path
          }
        });
      },
      onRecruitmentApplication: function () {
        let redirectURL = this.$route.path;
        this.$router.push({
          path: '/recruitment/' + this.recruitmentInfo.recruitmentId + '/application',
          query: {
            redirectURL: String(redirectURL),
            action: ApplicationAction.ATTEND
          },
        }, function () {

        });
      },
      onRecruitmentRecommend: function () {
        let redirectURL = this.$route.path;
        this.$router.push({
          path: '/recruitment/' + this.recruitmentInfo.recruitmentId + '/application',
          query: {
            redirectURL: String(redirectURL),
            action: ApplicationAction.REFERENCE
          },
        }, function () {

        });
      },
      onRecommendQrcode: function () {
        this.recommendQrcodeValue = window.location.origin + '/recruitment/'
            + this.recruitmentInfo.recruitmentId
            + '/application?action=' + ApplicationAction.ATTEND + '&referenceUserId='
            + UserApi.getUserId();
        this.recommendQrcode = true;
      }
    }
  }
</script>
