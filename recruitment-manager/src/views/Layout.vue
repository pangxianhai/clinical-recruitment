<template>
    <div>
        <el-container>
            <el-aside class="aside-left">
                <div class="menu-title">
                    <img height="50" src="@/assets/logo.png"/>
                    <!--<span>爱之募</span>-->
                </div>
                <el-menu :router="true" :default-active="defaultActive">
                    <el-submenu v-for="(menu,index) in menuList" :key="index" :index="menu.index">
                        <template slot="title">
                            <i :class="menu.icon" style="margin-right: 8px"></i>
                            <span>{{menu.title}}</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item v-for="(item,index) in menu.itemList" :index="item.index"
                                          :key="index" :route="item.path">
                                {{item.title}}
                            </el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                </el-menu>
            </el-aside>
            <el-container>
                <el-header>
                    <div class="user-info">
                        <span>{{userName}}</span>
                        <el-tooltip v-if="userName" effect="dark" content="退出登陆"
                                    placement="bottom">
                            <el-button @click="onLogOutAction" type="primary"
                                       icon="iconfont icon-dengchu">
                            </el-button>
                        </el-tooltip>
                    </div>
                </el-header>
                <el-main>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
        <el-footer>
            <div>@time 2019.05.01</div>
            <div>蜀ICP备19005727号-1</div>
        </el-footer>
    </div>
</template>

<style>
    .el-aside {
        min-height: 620px;
    }

    .aside-left {
        width: 150px !important;
        overflow: hidden;
    }

    .menu-title {
        width: 100%;
        text-align: center;
        height: 60px;
        padding-left: 30px;
        background-color: #409EFF;
    }

    .menu-title img {
        margin-top: 5px;
        float: left;
    }

    .menu-title span {
        color: #FFF;
        float: left;
        line-height: 60px;
        margin-left: 10px;
    }

    .el-header {
        background-color: #409EFF;
        margin: 0;
        padding: 0;
    }

    .el-header .user-info {
        line-height: 60px;
        color: #FFF;
        float: right;
        margin-right: 20px;
    }

    .el-header .user-info span {
        margin-right: 20px;
    }

    .el-header .user-info .el-button--text {
        color: #FFF;
    }

    .el-footer {
        background-color: #FFF;
        height: 60px;
        text-align: center;
        color: #888;
        padding-top: 20px;
        font-size: 12px;
    }

</style>

<script>
  import {
    Container,
    Header,
    Aside,
    Main,
    Footer,
    Menu,
    Submenu,
    MenuItem,
    MenuItemGroup,
    Radio,
    RadioGroup,
    RadioButton,
    Button,
    Loading,
    Tooltip
  } from 'element-ui';
  import Router from '@/router/Index';
  import UserApi from '@/api/UserApi';

  export default {
    components: {
      [Container.name]: Container,
      [Header.name]: Header,
      [Aside.name]: Aside,
      [Main.name]: Main,
      [Footer.name]: Footer,
      [Menu.name]: Menu,
      [Submenu.name]: Submenu,
      [MenuItem.name]: MenuItem,
      [MenuItemGroup.name]: MenuItemGroup,
      [Radio.name]: Radio,
      [RadioGroup.name]: RadioGroup,
      [RadioButton.name]: RadioButton,
      [Button.name]: Button,
      [Tooltip.name]: Tooltip,
    },

    data: function () {
      return {
        defaultActive: '1-1',
        menuList: [],
        userName: ''
      }
    },
    created: function () {
      this.initMenuList();
      // this.initDefaultActive();
      this.initCurrentUserInfo();
    },
    methods: {
      initMenuList: function () {
        let routes = Router.options.routes;
        let menuList = [];
        for (let i = 0; i < routes.length; ++i) {
          let route = routes[i];
          if (!route.menu) {
            continue;
          }
          let menu = {};
          menuList.push(menu);
          menu.icon = route.icon;
          menu.index = route.index;
          menu.title = route.name;

          if (typeof route.children === 'undefined') {
            continue;
          }
          let itemList = [];
          menu.itemList = itemList;
          for (let j = 0; j < route.children.length; ++j) {
            let child = route.children[j];

            let item = {};
            item.title = child.name;
            item.path = route.path + "/" + child.path;
            item.index = child.index;
            if (child.menu) {
              itemList.push(item);
            }
            if (child.name === this.$route.name) {
              this.defaultActive = child.index;
            }
          }
        }
        this.menuList = menuList;
      },
      initDefaultActive: function () {
        let path = this.$route.path;
        this.menuList.forEach(menu => {
          menu.itemList.forEach(item => {
            if (path === item.path) {
              this.defaultActive = item.index;
            }
          });
        });
      },
      initCurrentUserInfo: function () {
        UserApi.getLogInfo().then(userInfo => {
          if (!userInfo) {
            return;
          }
          this.userName = userInfo.realName;
        });
      },
      onLogOutAction: function () {
        const loading = Loading.service({
          lock: true,
          text: '正在退出登陆',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        UserApi.logOut();
        loading.close();
        this.$router.push({
          path: '/login',
          query: {
            redirectURL: this.$route.fullPath
          }
        })
      }
    }
  }
</script>
