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
      let query = [];
      for (let q in this.$route.query) {
        query.push(q + '=' + this.$route.query[q]);
      }
      let redirectURL = process.env.VUE_APP_HOST + "/user/wxlogin?" + query.join('&');
      UserApi.getWxLoginUrl(redirectURL).then(data => {
        window.location.href = data;
        Toast.clear();
      });
    }
  }
</script>
