<template>
    <div></div>
</template>

<script>
  import {Toast} from 'vant';
  import {UserConstants} from '@/constants/Global'
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
          if (UserConstants.PATIENT === parseInt(userType)) {
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
              this.$router.push({
                path: '/patient/register',
                query: {
                  recruitmentId: query.recruitmentId,
                  redirectURL: query.redirectURL,
                  openId: userInfo.openId,
                  nickname: userInfo.nickname
                }
              });
            }
          } else if (UserConstants.DOCTOR === parseInt(userType)) {
            this.$router.push({
              path: '/doctor/register',
              query: {
                openId: userInfo.openId,
                nickname: userInfo.nickname,
                redirectURL: query.redirectURL,
              }
            });
          }
        } else {
          //已登陆的用户
          UserApi.saveUserId(userInfo.userId);
          let thisUserType = this.$route.query.userType;
          if (userInfo.userType.code !== parseInt(thisUserType)) {
            Toast.loading({
              duration: 0,
              mask: true,
              loadingType: 'spinner',
              forbidClick: true,
              message: '您没有权限访问该页面，即将为您跳转到项目列表页...'
            });
            setTimeout(() => {
              this.$router.push({path: '/recruitment/list'});
              Toast.clear();
            }, 2000);
          } else {
            this.$router.push({path: '/recruitment/list'});
          }
        }
      })
    }
  }
</script>
