<template>
    <div class="application-list">
        <van-nav-bar title="申请记录"></van-nav-bar>
        <van-swipe :autoplay="5000">
            <van-swipe-item>
                <img width="100%" src="../../assets/banner3.png"/>
            </van-swipe-item>
        </van-swipe>
        <van-list
            v-model="applicationLoading"
            :finished="applicationFinished"
            finished-text="没有更多了"
            @load="onLoadApplication">
            <van-panel class="recruitment-panel" v-for="(item, index) in applicationList"
                       :title="item.recruitmentVO.title"
                       :key="index">
                <router-link :to="'/recruitment/application/detail/' + item.applicationId">
                    <van-row type="flex" v-if="item.recruitmentVO.recruitmentId">
                        <van-col span="5">适应症:</van-col>
                        <van-col span="19">{{item.recruitmentVO.indication}}</van-col>
                    </van-row>
                    <van-row type="flex" v-if="item.recruitmentVO.recruitmentId">
                        <van-col span="5">药物类型:</van-col>
                        <van-col span="19">{{item.recruitmentVO.drugType}}</van-col>
                    </van-row>
                    <van-row type="flex" v-if="item.recruitmentVO.recruitmentId">
                        <van-col span="5">药物名称:</van-col>
                        <van-col span="19">{{item.recruitmentVO.drugName}}</van-col>
                    </van-row>
                    <van-row type="flex">
                        <van-col span="5">患者姓名:</van-col>
                        <van-col span="8">
                            <span
                                v-if="item.patientVO">{{item.patientVO.userInfoVO.realName}}</span>
                        </van-col>
                        <van-col span="5">状态:</van-col>
                        <van-col span="3">{{item.status.desc}}</van-col>
                    </van-row>
                    <van-row type="flex">
                        <van-col span="5">报名时间:</van-col>
                        <van-col span="8">{{item.applicationTime}}</van-col>
                        <van-col span="5" v-if="showRecommendDoctor">推荐医生:</van-col>
                        <van-col span="3" v-if="showRecommendDoctor && item.doctorInfoVO">
                            {{item.doctorInfoVO.userInfoVO.realName}}
                        </van-col>
                    </van-row>
                </router-link>
            </van-panel>
        </van-list>
        <my-footer></my-footer>
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
</style>

<script>
  import {NavBar, List, Icon, Panel, Row, Col, Swipe, SwipeItem,} from 'vant';
  import Footer from '@/components/Footer';
  import RecruitmentApi from '@/api/RecruitmentApi';
  import UserApi from '@/api/UserApi';
  import {UserConstants} from '@/constants/Global';

  export default {
    components: {
      [Footer.name]: Footer,
      [NavBar.name]: NavBar,
      [List.name]: List,
      [Icon.name]: Icon,
      [Panel.name]: Panel,
      [Row.name]: Row,
      [Col.name]: Col,
      [Swipe.name]: Swipe,
      [SwipeItem.name]: SwipeItem,
    },
    data: function () {
      return {
        applicationList: [],
        applicationLoading: false,
        applicationFinished: false,
        currentPage: 1,
        currentUser: {},
        showRecommendDoctor: false
      }
    },
    created: function () {
      UserApi.getLogInfo().then((userInfo) => {
        this.currentUser = userInfo;
        this.showRecommendDoctor = userInfo.userType.code === UserConstants.DOCTOR;
      });
    },
    methods: {
      onLoadApplication: function () {
        RecruitmentApi.getRecruitmentApplication({}).then((pageResult) => {
          if (!pageResult) {
            this.applicationLoading = false;
            this.applicationFinished = true;
            return;
          }
          if (this.currentPage === 1) {
            this.applicationList = pageResult.data;
          } else {
            this.applicationList = this.applicationList.concat(pageResult.data);
          }
          this.applicationLoading = false;
          this.currentPage = this.currentPage + 1;
          if (pageResult.paginator.currentPage >= pageResult.paginator.totalPage) {
            this.applicationFinished = true;
          }
        });
      }
    }
  }
</script>
