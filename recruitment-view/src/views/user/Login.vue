<template>
    <div></div>
</template>

<script>
  import UserApi from '@/api/UserApi';

  export default {
    data: () => {
      return {}
    },
    created: function () {
      this.$toast.loading({
        mask: true,
        duration: 0,
        forbidClick: true,
        loadingType: 'spinner',
        message: '自动登录中...'
      });
      this.onToLogin();
    },
    methods: {
      onGoBack: function () {
        let redirectURL = this.$route.query.redirectURL;
        if (typeof redirectURL === 'undefined' || redirectURL.length <= 0) {
          redirectURL = '/recruitment/list';
        }
        this.$router.push({
          path: String(redirectURL)
        }, function () {

        });
      },
      onToLogin: function () {
        let query = [];
        for (let q in this.$route.query) {
          query.push(q + '=' + encodeURIComponent(String(this.$route.query[q])));
        }
        let redirectURL = window.location.origin + "/user/wxlogin?" + query.join('&');
        UserApi.getWxLoginUrl(redirectURL).then(data => {
          this.$toast.clear();
          window.location.href = String(data);
        });
      }
    }
  }
</script>
