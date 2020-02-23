<template>
    <div class="user-me">
        <div class="head">
            <van-nav-bar title="我的信息"></van-nav-bar>
            <van-row type="flex">
                <van-col style="width: 2rem"></van-col>
                <van-col>
                    <van-image
                        round
                        width="50"
                        height="50"
                        :src="require('@/assets/headimg.jpeg')"
                    />
                </van-col>
                <van-col @click="loginAction()"
                         style="line-height: 50px;color: #FFF;font-size: 14px;padding-left: 8px;">
                    {{nickname}}
                </van-col>
                <van-col v-if="isShowArrow" style="line-height: 50px;">
                    <van-icon name="arrow" size="12"/>
                </van-col>
            </van-row>
        </div>
        <div class="spacing"></div>
        <van-grid clickable :column-num="2" :border="false" :gutter="40" :icon-size="16">
            <van-grid-item icon="chat-o" text="我的参加" style="color:#5b4cff"/>
            <van-grid-item icon="coupon-o" text="我的推荐" style="color:#ff7813"/>
        </van-grid>
        <div class="spacing"></div>
        <van-cell-group>
            <van-cell icon="manager-o" title="姓名" :value="userInfo.realName"></van-cell>
            <van-cell icon="more-o" title="性别" :value="userInfo.gender"></van-cell>
            <van-cell icon="phone-o" title="手机号码" :value="userInfo.phone"></van-cell>
            <van-cell icon="clock-o" title="年龄" v-if="userInfo.showAge"
                      :value="userInfo.age"></van-cell>
            <van-cell icon="location-o" title="地区" v-if="userInfo.showAddress"
                      :value="userInfo.address"></van-cell>
        </van-cell-group>
        <div class="spacing"></div>
        <van-cell-group style="margin-bottom: 50px;display: block;">
            <van-cell icon="wap-home-o" title="所属机构" v-if="userInfo.showOrganizationName"
                      :value="userInfo.organizationName"></van-cell>
            <van-cell icon="shop-o" title="所属科室" v-if="userInfo.showDepartmentName"
                      :value="userInfo.departmentName"></van-cell>
            <van-cell icon="points" title="执业机构" v-if="userInfo.showMedicalInstitution"
                      :value="userInfo.medicalInstitution"></van-cell>
            <van-cell icon="label-o" title="执业类别" v-if="userInfo.showMedicalCategory"
                      :value="userInfo.medicalCategory"></van-cell>
            <van-cell icon="award-o" title="本科室申报的项目"
                      v-if="userInfo.showDepartmentApplication"></van-cell>
            <van-cell icon="user-circle-o" title="成为受试者" v-if="userInfo.showRegisterPatient"
                      is-link>
            </van-cell>
            <van-cell icon="contact" title="成为推荐人" v-if="userInfo.showRegisterReference"
                      is-link></van-cell>
            <van-cell icon="friends-o" title="成为研究员" v-if="userInfo.showRegisterResearcher"
                      is-link></van-cell>
        </van-cell-group>
        <my-footer></my-footer>
    </div>
</template>
<style>
    .user-me .head {
        background: url('../../assets/me-bj.png') no-repeat center #469ce3;
        color: #FFF;
    }

    .user-me .head .van-nav-bar {
        background-color: rgba(220, 38, 38, 0);
    }

    .user-me .head .van-nav-bar__title {
        color: #FFF;
    }

    .user-me .head .van-nav-bar::after {
        border: none !important;
    }

    .user-me .head .van-row {
        padding: 0px 0 8px 0;
    }

    .user-me .spacing {
        background-color: #ececec;
        width: 100%;
        height: 6px;
        display: block;
    }

    .user-me .van-icon-manager-o {
        color: #195dfc;
    }

    .user-me .van-icon-more-o {
        color: #fb294b;
    }

    .user-me .van-icon-phone-o {
        color: #4b97a8;
    }

    .user-me .van-icon-clock-o {
        color: #69dc7e;
    }

    .user-me .van-icon-location-o {
        color: #0aebe8;
    }

    .user-me .van-icon-wap-home-o {
        color: #0000ff;
    }

    .user-me .van-icon-shop-o {
        color: #eca46d;
    }

    .user-me .van-icon-points {
        color: #56986f;
    }

    .user-me .van-icon-label-o {
        color: #53ef4f;
    }

    .user-me .van-icon-award-o {
        color: #f877d4;
    }

    .user-me .van-icon-user-circle-o {
        color: rgba(204, 6, 223, 0.63);
    }

    .user-me .van-icon-contact {
        color: rgba(119, 9, 222, 0.72);
    }

    .user-me .van-icon-friends-o {
        color: rgba(238, 93, 13, 0.81);
    }
</style>
<script>
  import UserApi from '@/api/UserApi';

  export default {
    data: function () {
      return {
        nickname: '登录/注册',
        isShowArrow: true,
        userInfo: {
          showAge: false,
          showAddress: false,
          showMedicalInstitution: false,
          showMedicalCategory: false,
          showOrganizationName: false,
          showDepartmentName: false,
          showDepartmentApplication: false,
          showRegisterPatient: false,
          showRegisterReference: false,
          showRegisterResearcher: false,
        }
      }
    },
    created: function () {
      this.loadLogInfo();
    },
    methods: {
      loadLogInfo: function () {
        UserApi.getCurrentUserInfo().then(userInfo => {
          if (userInfo.userId) {
            this.nickname = userInfo.nickname;
            this.isShowArrow = false;
            this.buildUserInfo(userInfo);
          }
        });
      },
      loginAction: function () {
        if (UserApi.isLogin()) {
          return;
        }
        this.$router.push({
          path: '/user/login',
          query: {
            redirectURL: this.$route.path
          }
        }, function () {

        });
      },
      buildUserInfo(userInfo) {
        this.userInfo.realName = userInfo.realName;
        this.userInfo.gender = userInfo.gender.desc;
        this.userInfo.phone = userInfo.phone;
        if (userInfo.patientInfoRes) {
          this.userInfo.showAge = true;
          this.userInfo.age = userInfo.patientInfoRes.age;
          this.userInfo.showAddress = true;
          this.userInfo.address = userInfo.patientInfoRes.address;
          this.userInfo.openId = userInfo.openId;
          this.userInfo.showRegisterPatient = false;
        } else {
          this.userInfo.showRegisterPatient = true;
        }
        if (userInfo.referenceInfoRes) {
          this.userInfo.showAddress = true;
          this.userInfo.address = userInfo.patientInfoRes.address;
          this.userInfo.showMedicalInstitution = true;
          this.userInfo.medicalInstitution = userInfo.referenceInfoRes.medicalInstitution;
          this.userInfo.showMedicalCategory = true;
          this.userInfo.medicalCategory = userInfo.referenceInfoRes.medicalCategory;
          this.userInfo.showRegisterReference = false;
        } else {
          this.userInfo.showRegisterReference = true;
        }
        if (userInfo.researcherInfoRes) {
          this.userInfo.showDepartmentApplication = true;
          this.userInfo.showRegisterResearcher = false;
        } else {
          this.userInfo.showRegisterResearcher = true;
        }
        if (userInfo.organizationRes) {
          this.userInfo.showOrganizationName = true;
          this.userInfo.organizationName = userInfo.organizationRes.name;
        }
        if (userInfo.organizationDepartmentRes) {
          this.userInfo.showDepartmentName = true;
          this.userInfo.departmentName = userInfo.organizationDepartmentRes.name;
        }
      }
    }
  }
</script>