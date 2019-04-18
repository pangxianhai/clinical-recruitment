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
        UserApi.getLogInfo().then((userInfo) => {
          let thisUserType = this.$route.query.userType;
          if (userInfo.userType.code !== thisUserType) {
            Toast.loading({
              duration: 0,
              mask: true,
              loadingType: 'spinner',
              forbidClick: true,
              message: '您没有权限访问该页面，即将为您跳转到项目列表页...'
            });
            setTimeout(() => {
              this.onGoBack();
              Toast.clear();
            }, 2000);
          } else {
            this.onGoBack();
          }
        });
      } else {
        this.onToLogin();
      }
      Toast.clear();
    },
    methods: {
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
  }
</script>
