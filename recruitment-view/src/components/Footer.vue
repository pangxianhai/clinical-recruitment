<template>
    <van-tabbar v-model="active" safe-area-inset-bottom>
        <van-tabbar-item v-for="(menuInfo,index) in menuList" :key="index"
                         :to="menuInfo.path"
                         :icon="menuInfo.icon">{{menuInfo.text}}
        </van-tabbar-item>
    </van-tabbar>
</template>

<script>
  export default {
    name: 'my-footer',
    data: function () {
      return {
        userInfo: {},
        menuList: [
          {
            path: '/recruitment/list',
            icon: 'orders-o',
            text: '项目列表'
          },
          {
            path: '/recruitment/diseaseApplication',
            icon: 'notes-o',
            text: '罕见病'
          },
          {
            path: '/user/me',
            icon: 'user-o',
            text: '我'
          }
        ],
        active: 0,
      }
    },
    created: function () {
      this.initMenu();
    },
    methods: {
      initMenu: function () {
        let path = this.$route.path;
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
</script>
