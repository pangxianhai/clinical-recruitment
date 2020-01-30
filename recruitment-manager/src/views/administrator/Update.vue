<template>
    <div class="manager-update">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/administrator/list'}">管理员管理</el-breadcrumb-item>
            <el-breadcrumb-item>更新管理员</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form status-icon style="margin-top: 25px;width: 30%" :rules="managerRules"
                 ref="managerInfo"
                 :model="managerInfo"
                 label-width="80px">
            <el-form-item label="手机号码" prop="phone">
                <el-input v-model.number="managerInfo.phone" :disabled="true"
                          autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="realName">
                <el-input v-model="managerInfo.realName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="managerInfo.gender">
                    <el-radio label="1">
                        <i class="el-icon-male"></i>男
                    </el-radio>
                    <el-radio label="2">
                        <i class="el-icon-female"></i>女
                    </el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-edit"
                           @click="onUpdateManagerAction('managerInfo')">更新
                </el-button>
            </el-form-item>
        </el-form>

    </div>
</template>

<script>
  import {
    Breadcrumb,
    BreadcrumbItem,
    Form,
    FormItem,
    Input,
    RadioGroup,
    Radio,
    Button,
    Icon,
    Message
  } from 'element-ui';
  import AdminApi from '@/api/AdminApi';
  import {RouterUtil} from '@/util/Util';

  export default {
    components: {
      [Breadcrumb.name]: Breadcrumb,
      [BreadcrumbItem.name]: BreadcrumbItem,
      [Form.name]: Form,
      [FormItem.name]: FormItem,
      [Input.name]: Input,
      [RadioGroup.name]: RadioGroup,
      [Radio.name]: Radio,
      [Button.name]: Button,
      [Icon.name]: Icon,
    },
    data: function () {
      return {
        managerInfo: {
          phone: '',
          realName: '',
          gender: ''
        },
        managerRules: {
          realName: [
            {required: true, message: '请输入姓名', trigger: 'blur'},
            {min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur'}
          ],
          gender: [
            {required: true, message: '请选择性别', trigger: 'blur'},
          ]
        }
      }
    },
    created: function () {
      let userId = this.$route.params.userId;
      this.loadManagerInfo(userId);
    },
    methods: {
      loadManagerInfo: function (userId) {
        UserApi.getManagerByUserId(userId).then(managerInfo => {
          this.managerInfo.phone = managerInfo.phone;
          this.managerInfo.realName = managerInfo.realName;
          this.managerInfo.gender = managerInfo.gender.code + "";
          this.managerInfo.userId = managerInfo.userId;
        });
      },
      onUpdateManagerAction: function (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            UserApi.updateManager(this.managerInfo.userId, this.managerInfo).then(success => {
              if (success) {
                Message.success('更新成功即将跳转!');
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
