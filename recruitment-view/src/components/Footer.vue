<template>
    <van-tabbar v-model="active">
        <van-tabbar-item v-for="(menuInfo,index) in menuList" :key="index"
                         :to="menuInfo.path"
                         :icon="menuInfo.icon">{{menuInfo.text}}
        </van-tabbar-item>
    </van-tabbar>
</template>

<script>
  import UserApi from '@/api/UserApi';
  import {UserConstants} from '@/constants/Global';

  export default {
    name: 'my-footer',
    data: function () {
      return {
        userInfo: {},
        menuList: [],
        menuInfo: {
          patient: [
            {
              path: '/recruitment/list',
              icon: 'orders-o',
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
          ],
          doctor: [
            {
              path: '/recruitment/list',
              icon: 'orders-o',
              text: '任务列表'
            },
            {
              path: '/recruitment/applicationList',
              icon: 'records',
              text: '申请记录'
            },
            {
              path: '/doctor/info',
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
        if (typeof this.userInfo.userId === 'undefined') {
          this.menuList = this.menuInfo.patient;
        } else if (this.userInfo.userType.code === UserConstants.PATIENT) {
          this.menuList = this.menuInfo.patient;
        } else if (this.userInfo.userType.code === UserConstants.DOCTOR) {
          this.menuList = this.menuInfo.doctor;
        }
        this.initActiveMenu();
      },
      initActiveMenu: function () {
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
