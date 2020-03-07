<template>
    <div class="application-list">
        <van-nav-bar title="申请记录" left-arrow @click-left="onGoBack"></van-nav-bar>
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
            <van-panel class="recruitment-panel" v-for="(applicationInfo, index) in applicationList"
                       :title="applicationInfo.recruitmentInfoRes.title"
                       :key="index">
                <router-link
                    :to="'/recruitment/application/detail/' + applicationInfo.applicationId">
                    <van-row type="flex" v-if="applicationInfo.recruitmentInfoRes.recruitmentId">
                        <van-col span="5">适应症:</van-col>
                        <van-col span="19">{{applicationInfo.recruitmentInfoRes.indication}}
                        </van-col>
                    </van-row>
                    <van-row type="flex" v-if="applicationInfo.recruitmentInfoRes.recruitmentId">
                        <van-col span="5">药物类型:</van-col>
                        <van-col span="19">{{applicationInfo.recruitmentInfoRes.drugType}}</van-col>
                    </van-row>
                    <van-row type="flex" v-if="applicationInfo.recruitmentInfoRes.recruitmentId">
                        <van-col span="5">药物名称:</van-col>
                        <van-col span="19">{{applicationInfo.recruitmentInfoRes.drugName}}</van-col>
                    </van-row>
                    <van-row type="flex">
                        <van-col span="5">患者姓名:</van-col>
                        <van-col span="8">
                            <span
                                v-if="applicationInfo.patientInfoDetailRes">{{applicationInfo.patientInfoDetailRes.userInfoRes.realName}}</span>
                        </van-col>
                        <van-col span="5">状态:</van-col>
                        <van-col span="3">{{applicationInfo.status.desc}}</van-col>
                    </van-row>
                    <van-row type="flex">
                        <van-col span="5">报名时间:</van-col>
                        <van-col span="8">{{applicationInfo.applicationTime}}</van-col>
                        <van-col span="5">推荐人:</van-col>
                        <van-col span="3"
                                 v-if="applicationInfo.referenceDetailInfoRes">
                            {{applicationInfo.referenceDetailInfoRes.userInfoRes.realName}}
                        </van-col>
                    </van-row>
                    <van-row type="flex">
                        <van-col span="5">研究机构:</van-col>
                        <van-col span="19">
                            {{applicationInfo.departmentDetailRes.organizationRes.name}}/
                            {{applicationInfo.departmentDetailRes.name}}
                        </van-col>
                    </van-row>
                </router-link>
            </van-panel>
        </van-list>
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
  import RecruitmentApi from '@/api/RecruitmentApi';
  import UserApi from '@/api/UserApi';

  export default {

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
      },
      onGoBack: function () {
        let redirectURL = this.$route.query.redirectURL;
        if (typeof redirectURL === 'undefined' || redirectURL.length <= 0) {
          redirectURL = '/recruitment/list';
        }
        this.$router.push({path: String(redirectURL)}, function () {

        });
      },
    }
  }
</script>
