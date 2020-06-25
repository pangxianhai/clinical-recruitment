<template>
    <div class="update-password">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/administrator/list'}">管理员管理</el-breadcrumb-item>
            <el-breadcrumb-item>修改密码</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form status-icon style="margin-top: 25px;width: 30%"
                 :rules="managerRules"
                 ref="managerInfo"
                 :model="managerInfo"
                 label-width="100px">
            <el-form-item label="原名密码" prop="password">
                <el-input v-model="managerInfo.password" type="password"
                          autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
                <el-input v-model="managerInfo.newPassword" type="password"
                          autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="重复新密码" prop="renewPassword">
                <el-input v-model="managerInfo.renewPassword" type="password"
                          autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-edit"
                           @click="onUpdatePasswordAction('managerInfo')">修改密码
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
  import UserApi from '@/api/UserApi';
  import {RouterUtil} from '@/util/Util';

  export default {
    data: function () {
      return {
        managerInfo: {
          password: '',
          newPassword: '',
          renewPassword: ''
        },
        managerRules: {
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
          ],
          newPassword: [
            {required: true, message: '请输入新密码', trigger: 'blur'},
            {min: 8, max: 20, message: '密码必须在8到20位之间', trigger: 'blur'}
          ],
          renewPassword: [
            {validator: this.validatePassAgain, trigger: 'blur'}
          ],
        }
      }
    },
    methods: {
      validatePassAgain: function (rule, value, callback) {
        if (value === '') {
          callback(new Error('请再次输入新密码'));
        } else if (value !== this.managerInfo.newPassword) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      },
      onUpdatePasswordAction: function (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            UserApi.updatePassword(this.managerInfo).then(success => {
              if (success) {
                this.$message.success('修改密码成功！即将重新登录');
                UserApi.logOut();
                RouterUtil.goToBack(this.$route, this.$router, '/login');
              }
            });
          } else {
            return false;
          }
        });
      },
    }
  }
</script>
