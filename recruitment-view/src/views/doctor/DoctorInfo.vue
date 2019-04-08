<template>
    <div class="">
        <van-nav-bar title="医生个人信息"></van-nav-bar>
        <van-cell-group>
            <van-cell icon="manager-o" title="姓名"
                      :value="doctorInfo.userInfoVO.realName"></van-cell>
            <van-cell icon="more-o" title="性别"
                      :value="doctorInfo.userInfoVO.gender.desc"></van-cell>
            <van-cell icon="flower-o" title="微信昵称"
                      :value="doctorInfo.userInfoVO.nickname"></van-cell>
            <van-cell icon="phone-o" title="手机号" :value="doctorInfo.userInfoVO.phone"></van-cell>
            <van-cell icon="location-o" title="地址" :value="doctorInfo.address"></van-cell>
            <van-cell icon="points" title="执业机构"
                      :value="doctorInfo.medicalInstitution"></van-cell>
            <van-cell icon="label-o" title="执业类别" :value="doctorInfo.medicalCategory"></van-cell>
        </van-cell-group>
        <my-footer></my-footer>
    </div>
</template>

<script>
  import DoctorApi from '@/api/DoctorApi';

  export default {
    data: function () {
      return {
        doctorInfo: {
          userInfoVO: {
            gender: {}
          }
        }
      }
    },
    created: function () {
      DoctorApi.getCurrentDoctorInfo().then(doctorInfo => {
        this.doctorInfo = doctorInfo;
      });
    }
  }
</script>
