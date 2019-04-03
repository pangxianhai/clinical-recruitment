<template>
    <van-tabbar v-model="active">
        <van-tabbar-item to="/recruitment/list" icon="more-o">
            任务列表
        </van-tabbar-item>
        <van-tabbar-item to="/" icon="records">申请记录</van-tabbar-item>
        <van-tabbar-item to="/patient/info" icon="user-o">我</van-tabbar-item>
    </van-tabbar>
</template>

<script>
  import UserApi from '../api/UserApi';

  export default {
    name: 'my-footer',
    data: function () {
      return {
        userInfo: {
          userType: {
            code: 3
          }
        },
        active: 1,
      }
    },
    created: function () {
      UserApi.getLoginfo().then(userinfo => {
        this.userInfo = userinfo;
      });
    }
  }
</script>
