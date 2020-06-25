<template>
    <div class="manager-update">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/administrator/list'}">管理员管理</el-breadcrumb-item>
            <el-breadcrumb-item>更新管理员</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form status-icon style="margin-top: 25px;width: 40%" :rules="managerRules"
                 ref="adminInfo"
                 :model="adminInfo"
                 label-width="100px">
            <el-form-item label="手机号码" prop="phone">
                <el-input v-model.number="adminInfo.phone" :disabled="true"
                          autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="realName">
                <el-input v-model="adminInfo.realName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
                <el-radio-group v-model.number="adminInfo.gender">
                    <el-radio :label="1">
                        <i class="el-icon-male"></i>男
                    </el-radio>
                    <el-radio :label="2">
                        <i class="el-icon-female"></i>女
                    </el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="管理员类型" prop="type">
                <el-radio-group v-model.number="adminInfo.type">
                    <el-radio :label="1">
                        <i class="el-icon-user-solid"></i>系统管理员
                    </el-radio>
                    <el-radio :label="2">
                        <i class="el-icon-user"></i>业务管理员
                    </el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-edit"
                           @click="onUpdateManagerAction('adminInfo')">更新
                </el-button>
            </el-form-item>
        </el-form>

    </div>
</template>

<script>

  import {RouterUtil} from '@/util/Util';
  import AdminApi from "../../api/AdminApi";

  export default {
    data: function () {
      return {
        adminInfo: {
          phone: '',
          realName: '',
          gender: 0,
          type: 0,
        },
        managerRules: {
          realName: [
            {required: true, message: '请输入姓名', trigger: 'blur'},
            {min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur'}
          ],
          gender: [
            {required: true, message: '请选择性别', trigger: 'blur'},
          ],
          type: [
            {required: true, message: '请选择管理员类型', trigger: 'blur'},
          ],
        }
      }
    },
    created: function () {
      let adminId = this.$route.params.adminId;
      this.loadAdminInfo(adminId);
    },
    methods: {
      loadAdminInfo: function (adminId) {
        AdminApi.getAdminById(adminId).then(adminInfo => {
          this.adminInfo.phone = adminInfo.phone;
          this.adminInfo.realName = adminInfo.realName;
          this.adminInfo.gender = adminInfo.gender.code;
          this.adminInfo.type = adminInfo.type.code;
          this.adminInfo.id = adminInfo.id;
        });
      },
      onUpdateManagerAction: function (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            AdminApi.updateAdmin(this.adminInfo.id, this.adminInfo).then(success => {
              if (success) {
                this.$message.success('更新成功即将跳转!');
                RouterUtil.goToBack(this.$route, this.$router, '/administrator/list');
              }
            });
          } else {
            return false;
          }
        });
      }
    }
  }
</script>
