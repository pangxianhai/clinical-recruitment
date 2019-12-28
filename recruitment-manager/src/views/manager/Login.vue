<template>
    <div class="login-page">
        <div class="login-panel">
            <el-form class="login-form" :model="loginForm" ref="loginForm" :rules="loginRules"
                     label-width="80px"
                     size="mini">
                <div class="title-panel">
                    <div class="title-box">
                        <img width="50" src="@/assets/logo.png"/>
                        <span class="title">爱之募后台管理系统</span>
                    </div>
                </div>
                <el-form-item label="手机号:" prop="phone">
                    <el-input size="small" type="text" v-model="loginForm.phone"></el-input>
                </el-form-item>
                <el-form-item label="密码:" prop="password">
                    <el-input size="small" type="password" v-model="loginForm.password"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onLoginAction('loginForm')">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<style>
    .login-page {
        background-image: url("http://image.aiteruiyiyao.cn/web/login-background.png");
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%
    }

    .login-panel .title-panel {
        width: 100%;
        text-align: center;
        overflow: hidden;
        padding: 10px 0 20px 0;
    }

    .login-panel .title-panel .title-box {
        width: 220px;
        margin: auto;
    }

    .login-panel .title-panel .title-box img {
        float: left;
    }

    .login-panel .title-panel .title-box .title {
        float: left;
        color: #555;
        font-size: 18px;
        line-height: 50px;
        width: 170px;
    }

    .login-panel .login-form {
        width: 30%;
        margin: 140px auto auto auto;
        box-shadow: 0 2px 4px rgba(0, 0, 0, .5), 0 0 6px rgba(0, 0, 0, .4);
        padding: 15px 20px 10px 0;
        min-width: 400px;
        background-color: #FFF;
        border-radius: 4px
    }
</style>

<script>
  import {Button, Form, FormItem, Input, Message} from 'element-ui';
  import UserApi from '@/api/UserApi';

  export default {
    components: {
      [Button.name]: Button,
      [Form.name]: Form,
      [FormItem.name]: FormItem,
      [Input.name]: Input,
    },
    data: function () {
      return {
        loginForm: {
          phone: '',
          password: ''
        },
        loginRules: {
          phone: [
            {required: true, message: '手机号不能为空', trigger: 'blur'},
            {pattern: /^\d{11}$/, message: '手机号码格式不正确'},
          ],
          password: [
            {required: true, message: '密码不能不能为空', trigger: 'blur'},
            {max: 64, message: '您的输入太长', trigger: 'blur'}
          ],
        }
      }
    },
    methods: {
      onLoginAction: function (loginForm) {
        this.$refs[loginForm].validate((valid) => {
          if (valid) {
            UserApi.manageLogin(this.loginForm).then(userInfo => {
              UserApi.saveUserId(userInfo.userId);
              Message({
                message: '登录成功，即将跳转',
                type: 'success'
              });
              setTimeout(() => {
                let redirectURL = this.$route.query.redirectURL;
                if (typeof redirectURL === 'undefined' || redirectURL.length <= 0) {
                  redirectURL = '/recruitment/list';
                }
                this.$router.push({path: redirectURL});
              }, 2000);
            });
          } else {
            window.console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }
</script>
