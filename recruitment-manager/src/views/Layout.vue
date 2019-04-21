<template>
    <div>
        <el-container>
            <el-aside width="150px">
                <div class="menu-title">
                    <img height="50" src="@/assets/logo.png"/>
                    <span>爱之募</span>
                </div>
                <el-menu>
                    <el-submenu index="1">
                        <template slot="title">
                            <i class="el-icon-location"></i>
                            <span>导航一</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="1-1">选项1</el-menu-item>
                            <el-menu-item index="1-2">选项2</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                </el-menu>
            </el-aside>
            <el-container>
                <el-header>
                    <div class="user-info">
                        <span>庞先海</span>
                        <el-button type="text">退出</el-button>
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
    Button
  } from 'element-ui'

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
    }
  }
</script>
