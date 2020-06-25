<template>
    <div class="manager-add">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/administrator/list'}">管理员管理</el-breadcrumb-item>
            <el-breadcrumb-item>添加管理员</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form status-icon style="margin-top: 25px;width: 40%" :rules="managerRules"
                 ref="managerInfo"
                 :model="managerInfo"
                 label-width="100px">
            <el-form-item label="姓名" prop="realName">
                <el-input v-model="managerInfo.realName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop="phone">
                <el-input v-model="managerInfo.phone" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input v-model="managerInfo.password" show-password
                          autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPass">
                <el-input v-model="managerInfo.checkPass" show-password
                          autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
                <el-radio-group v-model.number="managerInfo.gender">
                    <el-radio :label="1">
                        <i class="el-icon-male"></i>男
                    </el-radio>
                    <el-radio :label="2">
                        <i class="el-icon-female"></i>女
                    </el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="管理员类型" prop="type">
                <el-radio-group v-model.number="managerInfo.type">
                    <el-radio :label="1">
                        <i class="el-icon-user-solid"></i>系统管理员
                    </el-radio>
                    <el-radio :label="2">
                        <i class="el-icon-user"></i>业务管理员
                    </el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" icon="el-icon-circle-plus-outline"
                           @click="onAddManagerAction('managerInfo')">添加
                </el-button>
            </el-form-item>
        </el-form>

    </div>
</template>

<script>
  import AdminApi from '@/api/AdminApi';
  import UserApi from '@/api/UserApi';
  import {RouterUtil} from '@/util/Util';

  export default {
    data: function () {
      return {
        managerInfo: {},
        managerRules: {
          realName: [
            {required: true, message: '请输入姓名', trigger: 'blur'},
            {min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur'}
          ],
          phone: [
            {required: true, message: '请输入手机号码', trigger: 'blur'},
            {pattern: /^\d{11}$/, message: '手机号码格式不正确'},
            {validator: this.validatePhone, trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {min: 8, max: 20, message: '密码必须在8到20位之间', trigger: 'blur'}
          ],
          gender: [
            {required: true, message: '请选择性别', trigger: 'blur'},
          ],
          checkPass: [
            {validator: this.validatePassAgain, trigger: 'blur'}
          ],
          type: [
            {required: true, message: '请选择管理员类型', trigger: 'blur'},
          ],
        }
      }
    },
    created: function () {

    },
    methods: {
      onAddManagerAction: function (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            AdminApi.addAdmin(this.managerInfo).then(success => {
              if (success) {
                this.$message.success('注册成功即将跳转!');
                RouterUtil.goToBack(this.$route, this.$router, '/administrator/list');
              }
            });
          } else {
            return false;
          }
        });
      },
      validatePassAgain: function (rule, value, callback) {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.managerInfo.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      },
      validatePhone: function (rule, value, callback) {
        if (value === '') {
          callback(new Error('请输入手机号码'));
        } else {
          UserApi.getUserByPhone(value).then(userInfo => {
            if (userInfo.userId) {
              callback(new Error('手机号码已经被注册了'));
            } else {
              callback();
            }
          });
        }
      }
    }
  }
</script>
