<template>
    <div class="">
        <van-nav-bar title="患者个人信息"></van-nav-bar>
        <van-cell-group>
            <van-cell icon="manager-o" title="姓名"
                      :value="patientInfo.userInfoVO.realName"></van-cell>
            <van-cell icon="more-o" title="性别"
                      :value="patientInfo.userInfoVO.gender.desc"></van-cell>
            <van-cell icon="flower-o" title="微信昵称"
                      :value="patientInfo.userInfoVO.nickname"></van-cell>
            <van-cell icon="phone-o" title="手机号" :value="patientInfo.userInfoVO.phone"></van-cell>
            <van-cell icon="gem-o" title="年龄" :value="patientInfo.age"></van-cell>
            <van-cell icon="location-o" title="地址" :value="patientInfo.address"></van-cell>
        </van-cell-group>
        <van-button v-if="!isLogin" style="margin-top: 10px"
                    size="small" block type="primary"
                    @click="onPatientLogin">注册
        </van-button>
        <my-footer></my-footer>
    </div>
</template>

<script>
  import {NavBar, Cell, CellGroup, Button} from 'vant';
  import PatientApi from '@/api/PatientApi';
  import Footer from '@/components/Footer';
  import UserApi from "@/api/UserApi";
  import {UserConstants} from '@/constants/Global'

  export default {
    components: {
      [Footer.name]: Footer,
      [NavBar.name]: NavBar,
      [Cell.name]: Cell,
      [CellGroup.name]: CellGroup,
      [Button.name]: Button,
    },
    data: () => {
      return {
        patientInfo: {
          userInfoVO: {
            gender: {}
          }
        },
        isLogin: false
      }
    },
    created: function () {
      if (!UserApi.isLogin()) {
        this.isLogin = false;
        return;
      }
      this.isLogin = true;
      PatientApi.getCurrentPatientInfo().then(patientInfo => {
        if (patientInfo.userId) {
          this.patientInfo = patientInfo;
        }
      });
    },
    methods: {
      onPatientLogin: function () {
        this.$router.push({
          path: '/user/login',
          query: {
            userType: UserConstants.PATIENT
          }
        });
      }
    }
  }
</script>
