<template>
    <div></div>
</template>

<script>
  import {Toast} from 'vant';
  import UserApi from '@/api/UserApi';
  import {CookieUtil} from '@/util/Util';

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
        if (typeof userInfo.userId === 'undefined') {
          if (this.UserConstants.PATIENT === parseInt(userType)) {
            this.$router.push({path: '/patient/register'});
          }
        } else {
          CookieUtil.setCookie('userId', userInfo.userId);
          this.$router.push({path: '/recruitment/list'});
        }
      })
    }
  }
</script>
