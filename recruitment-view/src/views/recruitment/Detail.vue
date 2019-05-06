<template>
    <div class="recruitment-detail">
        <van-nav-bar title="项目详情" left-arrow @click-right="onRecruitmentApplication"
                     @click-left="onGoBack">
            <van-icon v-if="userInfo.userType.code === UserConstants.DOCTOR" name="share"
                      slot="right"></van-icon>
            <van-icon v-if="userInfo.userType.code === UserConstants.PATIENT" name="edit"
                      slot="right"></van-icon>
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
                <van-col span="5">招募人数:</van-col>
                <van-col span="8">{{recruitmentInfo.recruitmentNumber}}人</van-col>
                <van-col span="5">招募状态:</van-col>
                <van-col span="3">{{recruitmentInfo.status.desc}}</van-col>
            </van-row>
            <van-row type="flex">
                <van-col span="5">起至时间:</van-col>
                <van-col span="19">{{recruitmentInfo.startTime}}~{{recruitmentInfo.stopTime}}</van-col>
            </van-row>
        </van-panel>
        <div class="introduction">
            {{recruitmentInfo.introduction}}
        </div>
        <van-tabs :v-model="1">
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
        <van-row style="margin-top:15px" type="flex" justify="center">
            <van-col>
                <van-button type="info" size="small"
                            @click="onContactUs">
                    <van-icon name="service-o"></van-icon>
                    联系我们
                </van-button>
            </van-col>
            <van-col v-if="userInfo.userType.code === UserConstants.PATIENT"
                     style="margin-left: 15px">
                <van-button type="warning" size="small"
                            @click="onRecruitmentApplication()">
                    <van-icon name="edit"></van-icon>
                    我要参加
                </van-button>
            </van-col>
            <van-col v-if="userInfo.userType.code === UserConstants.DOCTOR"
                     style="margin-left: 15px">
                <van-button type="warning" size="small"
                            @click="onRecruitmentApplication()">
                    <van-icon name="share"></van-icon>
                    我要推荐
                </van-button>
            </van-col>
            <van-col v-if="userInfo.userType.code === UserConstants.DOCTOR"
                     style="margin-left: 15px">
                <van-button type="danger" size="small"
                            @click="onRecommendQrcode()">
                    <van-icon name="qr"></van-icon>
                    推荐二维码
                </van-button>
            </van-col>
        </van-row>
        <van-popup v-model="recommendQrcode"
                   position="bottom"
                   :overlay="true"
                   :lazy-render="false"
                   class="recommend-qrcode">
            <van-panel :title="recruitmentInfo.title">
            </van-panel>
            <div class="qrcode">
                <canvas id="canvas" code=""></canvas>
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
  import {NavBar, Panel, Row, Col, Tab, Tabs, List, Cell, Button, Popup, Icon} from 'vant';
  import RecruitmentApi from "@/api/RecruitmentApi";
  import {UserConstants} from '@/constants/Global';
  import UserApi from '@/api/UserApi';
  import QRCode from 'qrcode';

  export default {
    components: {
      [NavBar.name]: NavBar,
      [Panel.name]: Panel,
      [Row.name]: Row,
      [Col.name]: Col,
      [Tab.name]: Tab,
      [Tabs.name]: Tabs,
      [List.name]: List,
      [Button.name]: Button,
      [Popup.name]: Popup,
      [Icon.name]: Icon,
      [Cell.name]: Cell,
    },
    data: function () {
      return {
        recruitmentInfo: {
          status: {}
        },
        recruitmentCenterLoading: false,
        recruitmentCenterFinished: false,
        researchCenterList: [],
        UserConstants: UserConstants,
        userInfo: {
          userType: {
            code: UserConstants.PATIENT,
          }
        },
        recommendQrcode: false
      }
    },
    created: function () {
      let recruitmentId = this.$route.params.recruitmentId;
      this.onLoadRecruitmentInfo(recruitmentId);
      this.onLoadRecruitmentCenter(recruitmentId);
      UserApi.getLogInfo().then(userInfo => {
        if (userInfo.userId) {
          this.userInfo = userInfo;
        }
      });
    },
    methods: {
      onGoBack: function () {
        let redirectURL = this.$route.query.redirectURL;
        if (typeof redirectURL === 'undefined' || redirectURL.length <= 0) {
          redirectURL = '/recruitment/list';
        }
        this.$router.push({path: redirectURL});
      },
      onLoadRecruitmentInfo: function (recruitmentId) {
        if (typeof recruitmentId === 'undefined') {
          return;
        }
        RecruitmentApi.getRecruitmentById(recruitmentId).then(recruitmentInfo => {
          this.recruitmentInfo = recruitmentInfo;
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
        if (UserApi.isLogin()) {
          this.$router.push({
            path: '/recruitment/application',
            query: {
              recruitmentId: this.recruitmentInfo.recruitmentId,
              redirectURL: redirectURL
            },
          });
        } else {
          this.$router.push({
            path: '/user/login',
            query: {
              userType: 3,
              recruitmentId: this.recruitmentInfo.recruitmentId,
              action: 'application',
              redirectURL: redirectURL
            },
          });
        }
      },
      onRecommendQrcode: function () {
        let canvas = document.getElementById('canvas');
        let recommendUrl = process.env.VUE_APP_HOST + '/recruitment/application?recruitmentId='
            + this.recruitmentInfo.recruitmentId + "&doctorUserId=" + this.userInfo.userId;
        QRCode.toCanvas(canvas, recommendUrl, {
          width: 320,
          height: 320
        }, (error) => {
          if (error) {
            window.console.log(error);
          } else {
            this.recommendQrcode = true;
          }
        });
      }
    }
  }
</script>
