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
                <router-link :to="'/recruitment/detail/' + item.recruitmentId">
                    <van-row type="flex">
                        <van-col span="5">登记编号:</van-col>
                        <van-col span="8">{{item.registerCode}}</van-col>
                        <van-col span="5">试验分期:</van-col>
                        <van-col span="3">{{item.practiceStages}}</van-col>
                    </van-row>
                    <van-row type="flex">
                        <van-col span="5">药物类型:</van-col>
                        <van-col span="19">{{item.drugType}}</van-col>
                    </van-row>
                    <van-row type="flex">
                        <van-col span="5">药物名称:</van-col>
                        <van-col span="19">{{item.drugName}}</van-col>
                    </van-row>
                    <van-row type="flex">
                        <van-col span="5">适应症:</van-col>
                        <van-col span="19">{{item.indication}}</van-col>
                    </van-row>
                    <van-row type="flex">
                        <van-col span="5">申办方:</van-col>
                        <van-col span="19">{{item.bidParty}}</van-col>
                    </van-row>
                    <van-row type="flex">
                        <van-col span="5">招募人数:</van-col>
                        <van-col span="8">{{item.recruitmentNumber}}人</van-col>
                        <van-col span="5">招募状态:</van-col>
                        <van-col span="3">
                            <van-tag style="min-width: 38px" round
                                     v-if="RecruitmentStatus.FINISHED===item.status.code">
                                {{item.status.desc}}
                            </van-tag>
                            <van-tag style="min-width: 38px" round type="primary" plain
                                     v-if="RecruitmentStatus.NOT_BEGIN===item.status.code">
                                {{item.status.desc}}
                            </van-tag>
                            <van-tag style="min-width: 38px" round type="primary"
                                     v-if="RecruitmentStatus.IN_PROCESS===item.status.code">
                                {{item.status.desc}}
                            </van-tag>
                        </van-col>
                    </van-row>
                    <van-row type="flex">
                        <van-col span="5">起至时间:</van-col>
                        <van-col span="19">{{item.startTime}}~{{item.stopTime}}</van-col>
                    </van-row>
                </router-link>
                <van-row type="flex" justify="end">
                    <van-col>
                        <van-button type="info" size="small"
                                    @click="onContactUs">
                            <van-icon name="service-o"></van-icon>
                            联系我们
                        </van-button>
                    </van-col>
                    <van-col style="margin-left: 15px">
                        <van-button type="warning" size="small"
                                    @click="onRecruitmentApplication(item)">
                            <van-icon name="edit"></van-icon>
                            我要参加
                        </van-button>
                    </van-col>
                    <van-col
                        style="margin-left: 15px">
                        <van-button type="danger" size="small"
                                    @click="onRecommendQrcode(item)">
                            <van-icon name="qr"></van-icon>
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
  import RecruitmentApi from '@/api/RecruitmentApi';
  import UserApi from '@/api/UserApi';
  import {RecruitmentStatus, ApplicationAction, UserConstants} from '@/constants/Global';

  export default {
    data: function () {
      return {
        UserConstants: UserConstants,
        RecruitmentStatus: RecruitmentStatus,
        showAddress: false,
        showRecommend: false,
        recommendQrcode: false,
        recommendQrcodeTitle: '',
        recommendQrcodeValue: '',
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
        this.$router.push({
          path: '/recruitment/' + recruitment.recruitmentId + '/application',
          query: {
            redirectURL: String(redirectURL),
            action: ApplicationAction.ATTEND
          },
        }, function () {

        });
      },
      onContactUs: function () {
        this.$router.push({
          path: '/site/contactUs',
          query: {
            redirectURL: this.$route.path
          }
        }, function () {

        });
      },
      onRecommendQrcode: function (recruitmentInfo) {
        this.recommendQrcodeValue = window.location.origin + '/recruitment/'
            + recruitmentInfo.recruitmentId
            + '/application?action=' + ApplicationAction.ATTEND + '&referenceUserId='
            + UserApi.getUserId();
        this.recommendQrcode = true;
        this.recommendQrcodeTitle = recruitmentInfo.title;
      }
    }
  }
</script>
