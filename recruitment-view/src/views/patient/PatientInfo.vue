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

        <my-footer></my-footer>
    </div>
</template>

<script>
  import PatientApi from '@/api/PatientApi'

  export default {
    data: () => {
      return {
        patientInfo: {
          userInfoVO: {
            gender: {}
          }
        }
      }
    },
    created: function () {
      PatientApi.getCurrentPatientInfo().then(patientInfo => {
        this.patientInfo = patientInfo;
      });
    }
  }
</script>
