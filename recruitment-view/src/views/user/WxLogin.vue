<template>
    <div></div>
</template>

<script>
  import {Toast} from 'vant';
  import UserApi from '@/api/UserApi';

  export default {
    data: function () {
      return {}
    },
    created: function () {
      Toast.loading({
        duration: 0,
        mask: true,
        forbidClick: true,
        loadingType: 'spinner',
        message: '微信登录中...'
      });
      let code = this.$route.query.code;
      let userType = this.$route.query.userType;
      UserApi.wxLogin(code).then(userInfo => {
        Toast.clear();
        let query = this.$route.query;
        if (typeof userInfo.userId === 'undefined') {
          if (this.UserConstants.PATIENT === parseInt(userType)) {
            if ('application' === query.action && query.recruitmentId.length > 0) {
              this.$router.push({
                path: '/recruitment/application',
                query: {
                  recruitmentId: query.recruitmentId,
                  redirectURL: query.redirectURL,
                  openId: userInfo.openId,
                  nickname: userInfo.nickname
                }
              });
            } else {
              this.$router.push({path: '/patient/register'});
            }
          }
        } else {
          UserApi.saveUserId(userInfo.userId);
          this.$router.push({path: '/recruitment/list'});
        }
      })
    }
  }
</script>
