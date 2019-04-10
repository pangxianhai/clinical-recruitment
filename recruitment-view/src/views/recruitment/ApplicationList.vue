<template>
    <div class="application-list">
        <van-nav-bar title="申请记录"></van-nav-bar>
        <van-list
            v-model="applicationLoading"
            :finished="applicationFinished"
            finished-text="没有更多了"
            @load="onLoadApplication">
            <van-panel class="recruitment-panel" v-for="(item, index) in applicationList"
                       :title="item.recruitmentVO.title"
                       :key="index">
                <van-row type="flex">
                    <van-col span="5">患者姓名:</van-col>
                    <van-col span="8">
                        <span v-if="item.patientVO">{{item.patientVO.userInfoVO.realName}}</span>
                    </van-col>
                    <van-col span="5">状态:</van-col>
                    <van-col span="3">{{item.status.desc}}</van-col>
                </van-row>
                <van-row type="flex">
                    <van-col span="5">报名时间:</van-col>
                    <van-col span="8">{{item.applicationTime}}</van-col>
                    <van-col span="5">推荐医生:</van-col>
                    <van-col span="3" v-if="item.doctorInfoVO">
                        {{item.doctorInfoVO.userInfoVO.realName}}
                    </van-col>
                </van-row>
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
  import RecruitmentApi from '@/api/RecruitmentApi';

  export default {
    data: function () {
      return {
        applicationList: [],
        applicationLoading: false,
        applicationFinished: false,
        currentPage: 1,
      }
    },
    methods: {
      onLoadApplication: function () {
        RecruitmentApi.getRecruitmentApplication({}).then((pageResult) => {
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
