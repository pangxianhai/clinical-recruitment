<template>
    <van-tabbar v-model="active">
        <van-tabbar-item v-for="(menuInfo,index) in menuList" :key="index"
                         :to="menuInfo.path"
                         :icon="menuInfo.icon">{{menuInfo.text}}
        </van-tabbar-item>
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
        menuList: [],
        menuInfo: {
          patient: [
            {
              path: '/recruitment/list',
              icon: 'more-o',
              text: '任务列表'
            },
            {
              path: '/recruitment/applicationList',
              icon: 'records',
              text: '申请记录'
            },
            {
              path: '/patient/info',
              icon: 'user-o',
              text: '我'
            }
          ]
        },
        active: 0,
      }
    },
    created: function () {
      UserApi.getLogInfo().then(userInfo => {
        this.userInfo = userInfo;
        this.initMenu();
      });
    },
    methods: {
      initMenu: function () {
        let path = this.$route.path;
        if (this.userInfo.userType.code === 3) {
          this.menuList = this.menuInfo.patient;
          for (let i = 0; i < this.menuList.length; ++i) {
            let m = this.menuList[i];
            if (m.path === path) {
              this.active = i;
              break;
            }
          }
        }
      }
    }
  }
</script>
