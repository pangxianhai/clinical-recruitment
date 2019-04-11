<template>
    <div></div>
</template>

<script>
  import {Toast} from 'vant';

  import UserApi from '@/api/UserApi';

  export default {
    data: () => {
      return {}
    },
    created: function () {
      Toast.loading({
        mask: true,
        duration: 0,
        forbidClick: true,
        loadingType: 'spinner',
        message: '自动登录中...'
      });
      if (UserApi.isLogin()) {
        this.onGoBack();
      } else {
        this.onToLogin();
      }
      Toast.clear();
    },
    onGoBack: function () {
      let redirectURL = this.$route.query.redirectURL;
      if (typeof redirectURL === 'undefined' || redirectURL.length <= 0) {
        redirectURL = '/recruitment/list';
      }
      this.$router.push({path: redirectURL});
    },
    onToLogin: function () {
      let query = [];
      for (let q in this.$route.query) {
        query.push(q + '=' + this.$route.query[q]);
      }
      let redirectURL = process.env.VUE_APP_HOST + "/user/wxlogin?" + query.join('&');
      UserApi.getWxLoginUrl(redirectURL).then(data => {
        window.location.href = data;
      });
    }
  }
</script>
