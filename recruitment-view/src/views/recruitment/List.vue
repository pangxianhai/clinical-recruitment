<template>
    <div class="recruitment-list">
        <van-nav-bar title="招募大厅"></van-nav-bar>
        <van-swipe :autoplay="3000">
            <van-swipe-item>
                <img width="100%" src="../../assets/banner1.png"/>
            </van-swipe-item>
            <van-swipe-item>
                <img width="100%" src="../../assets/banner2.png"/>
            </van-swipe-item>
        </van-swipe>
        <van-search placeholder="输入标题，登记编号，适应症等搜索" v-model="searchParam.queryText"
                    @search="recruitmentOnSearch"></van-search>
        <van-row style="padding-bottom: 10px;background-color: #FFF">
            <van-col class="title" span="6">智能推荐</van-col>
            <van-col class="choice" span="8" offset="1">{{showRecommendText}}
                <van-icon name="arrow-down" @click="showRecommend = !showRecommend"></van-icon>
            </van-col>
            <van-col class="choice" span="7" offset="1">{{showAddressText}}
                <van-icon name="arrow-down" @click="showAddress= !showAddress"></van-icon>
            </van-col>
        </van-row>
        <address-select :show="showAddress" @confirm="addressSelectConfirm"
                        @cancel="addressSelectCancel"></address-select>
        <van-popup class="show-recommend-popup" v-model="showRecommend" position="top">
            <van-col v-for="item in recommendList" :key="item">
                <van-button @click="choiceRecommend(item)" type="default">{{item}}</van-button>
            </van-col>
        </van-popup>
        <van-list
            v-model="recruitmentLoading"
            :finished="recruitmentFinished"
            finished-text="没有更多了"
            @load="onLoadRecruitment">
            <van-panel class="recruitment-panel" v-for="(item, index) in recruitmentInfList"
                       :title="item.title"
                       :key="index">
                <van-row type="flex">
                    <van-col span="5">登记编号:</van-col>
                    <van-col span="8">{{item.registerCode}}</van-col>
                    <van-col span="5">实验分期:</van-col>
                    <van-col span="3">{{item.practiceStages}}</van-col>
                </van-row>
                <van-row type="flex">
                    <van-col span="5">药物名称:</van-col>
                    <van-col span="19">{{item.drugName}}</van-col>
                </van-row>
                <van-row type="flex">
                    <van-col span="5">招募人数:</van-col>
                    <van-col span="8">{{item.recruitmentNumber}}人</van-col>
                    <van-col span="5">招募状态:</van-col>
                    <van-col span="3">{{item.status.desc}}</van-col>
                </van-row>
                <van-row type="flex">
                    <van-col span="5">适应症:</van-col>
                    <van-col span="19">{{item.indication}}</van-col>
                </van-row>
                <van-row type="flex" justify="end">
                    <van-col>
                        <van-button type="info" size="small"
                                    @click="onContactUs">联系我们
                        </van-button>
                    </van-col>
                    <van-col v-if="userInfo.userType.code === UserConstants.PATIENT"
                             style="margin-left: 15px">
                        <van-button type="warning" size="small"
                                    @click="onRecruitmentApplication(item)">
                            我要参加
                        </van-button>
                    </van-col>
                    <van-col v-if="userInfo.userType.code === UserConstants.DOCTOR"
                             style="margin-left: 15px">
                        <van-button type="warning" size="small"
                                    @click="onRecruitmentApplication(item)">
                            我要推荐
                        </van-button>
                    </van-col>
                    <van-col v-if="userInfo.userType.code === UserConstants.DOCTOR"
                             style="margin-left: 15px">
                        <van-button type="danger" size="small"
                                    @click="onRecommendQrcode(item)">
                            推荐二维码
                        </van-button>
                    </van-col>
                </van-row>
            </van-panel>
        </van-list>
        <my-footer></my-footer>
        <van-popup v-model="recommendQrcode"
                   position="bottom"
                   :overlay="true"
                   :lazy-render="false"
                   class="recommend-qrcode">
            <van-panel :title="recommendQrcodeTitle">
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

<style lang="less">
    .recruitment-list .van-field__right-icon .van-icon {
        color: #1989fa;
    }

    .recruitment-list .van-row .title {
        padding-left: 12px;
        font-size: 13px;
    }

    .recruitment-list .van-row .choice {
        font-size: 12px;
        text-align: right;
    }

    .recruitment-list .van-row .choice .van-icon {
        color: #1989fa;
    }

    .recruitment-list .show-recommend-popup {
        width: 100%;
    }

    .recruitment-list .show-recommend-popup .van-col {
        margin: 10px;
    }

    .recruitment-list .show-recommend-popup .van-col .van-button--normal {
        width: 130px;
    }

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

    .recommend-qrcode .qrcode {
        width: 100%;
        text-align: center;
    }
</style>

<script>
  import {NavBar, Button, List, Icon, Search, Row, Col, Popup, Swipe, SwipeItem, Panel} from 'vant';
  import QRCode from 'qrcode';
  import AddressSelect from "@/components/AddressSelect";
  import RecruitmentApi from '@/api/RecruitmentApi';
  import UserApi from '@/api/UserApi';
  import {UserConstants} from '@/constants/Global';
  import Footer from '@/components/Footer';

  export default {
    components: {
      [AddressSelect.name]: AddressSelect,
      [Footer.name]: Footer,
      [NavBar.name]: NavBar,
      [Button.name]: Button,
      [List.name]: List,
      [Icon.name]: Icon,
      [Search.name]: Search,
      [Row.name]: Row,
      [Col.name]: Col,
      [Popup.name]: Popup,
      [Swipe.name]: Swipe,
      [SwipeItem.name]: SwipeItem,
      [Panel.name]: Panel,
    },
    data: function () {
      return {
        userInfo: {
          userType: {
            //默认为患者
            code: UserConstants.PATIENT,
          }
        },
        UserConstants: UserConstants,
        showAddress: false,
        showRecommend: false,
        recommendQrcode: false,
        recommendQrcodeTitle: '',
        recruitmentInfList: [],
        recruitmentLoading: false,
        recruitmentFinished: false,
        searchParam: {
          queryText: '',
          indication: '',
          addressText: '',
          currentPage: 1,
          pageSize: 10
        },
        recommendList: ['所有疾病类型', '糖尿病', '肺癌', '胃癌、结肠直癌', '食道癌', '肝癌、胆道癌', '乳腺癌', '脑癌、甲状腺癌', '泌尿生殖',
          '淋巴癌、白血病', '神经系统', '风湿免疫', '胰腺癌', '实体瘤', '黑色素瘤', '软组织肉瘤', '鼻咽癌'],
        showRecommendText: '所有疾病类型',
        showAddressText: '所有城市'
      }
    },
    created: function () {
      UserApi.getLogInfo().then(userInfo => {
        if (userInfo.userId) {
          this.userInfo = userInfo;
        }
      });
    },
    methods: {
      onLoadRecruitment: function () {
        RecruitmentApi.getRecruitment(this.searchParam).then(pageResult => {
          if (this.searchParam.currentPage === 1) {
            this.recruitmentInfList = pageResult.data;
          } else {
            this.recruitmentInfList = this.recruitmentInfList.concat(pageResult.data);
          }
          this.recruitmentLoading = false;
          this.searchParam.currentPage = this.searchParam.currentPage + 1;
          if (pageResult.paginator.currentPage >= pageResult.paginator.totalPage) {
            this.recruitmentFinished = true;
          }
        });
      },
      recruitmentOnSearch: function () {
        this.searchParam.currentPage = 1;
        this.recruitmentLoading = false;
        this.recruitmentFinished = false;
      },
      addressSelectCancel: function () {
        this.showAddressText = '所有城市';
        this.showAddress = false;
        this.searchParam.addressText = '';
        this.searchParam.currentPage = 1;
        this.recruitmentLoading = false;
        this.recruitmentFinished = false;
      },
      addressSelectConfirm: function (data) {
        const addressText = data[0].name + " " + data[1].name + " " + data[2].name;
        this.showAddressText = data[1].name + data[2].name;
        this.showAddress = false;
        this.searchParam.addressText = addressText;
        this.searchParam.currentPage = 1;
        this.recruitmentLoading = false;
        this.recruitmentFinished = false;
      },
      choiceRecommend: function (item) {
        this.showRecommendText = item;
        this.showRecommend = false;
        if ('所有疾病类型' === item) {
          this.searchParam.indication = '';
        } else {
          this.searchParam.indication = item;
        }
        this.searchParam.currentPage = 1;
        this.recruitmentLoading = false;
        this.recruitmentFinished = false;
      },
      onRecruitmentApplication: function (recruitment) {
        let redirectURL = this.$route.path;
        if (UserApi.isLogin()) {
          this.$router.push({
            path: '/recruitment/application',
            query: {
              recruitmentId: recruitment.recruitmentId,
              redirectURL: redirectURL
            },
          });
        } else {
          this.$router.push({
            path: '/user/login',
            query: {
              userType: 3,
              recruitmentId: recruitment.recruitmentId,
              action: 'application',
              redirectURL: redirectURL
            },
          });
        }
      },
      onContactUs: function () {
        this.$router.push({
          path: '/site/contactUs',
          query: {
            redirectURL: this.$route.path
          }
        });
      },
      onRecommendQrcode: function (recruitmentInfo) {
        let canvas = document.getElementById('canvas');
        let recommendUrl = process.env.VUE_APP_HOST + '/recruitment/application?recruitmentId='
            + recruitmentInfo.recruitmentId + "&doctorUserId=" + this.userInfo.userId;
        QRCode.toCanvas(canvas, recommendUrl, {
          width: 320,
          height: 320
        }, (error) => {
          if (error) {
            window.console.log(error);
          } else {
            this.recommendQrcode = true;
            this.recommendQrcodeTitle = recruitmentInfo.title;
          }
        });
      }
    }
  }
</script>
