<template>
    <div class="doctor-add">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/doctor/list' }">医生管理</el-breadcrumb-item>
            <el-breadcrumb-item>添加医生</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form status-icon style="margin-top: 25px;width: 30%" :rules="doctorRules"
                 ref="doctorInfo"
                 :model="doctorInfo"
                 label-width="80px">
            <el-form-item label="姓名" prop="name">
                <el-input v-model="doctorInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop="phone">
                <el-input v-model.number="doctorInfo.phone"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="doctorInfo.gender">
                    <el-radio label="1">
                        <i class="el-icon-male"></i>男
                    </el-radio>
                    <el-radio label="2">
                        <i class="el-icon-female"></i>女
                    </el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="地址" prop="addressIds">
                <el-cascader
                    :options="areaData"
                    v-model="doctorInfo.addressIds"
                    placeholder="请选择地址">
                </el-cascader>
            </el-form-item>
            <el-form-item label="执业机构" prop="medicalInstitution">
                <el-input v-model="doctorInfo.medicalInstitution"></el-input>
            </el-form-item>
            <el-form-item label="执业类别" prop="medicalCategory">
                <el-input v-model="doctorInfo.medicalCategory"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-circle-plus-outline"
                           @click="onAddDoctorAction('doctorInfo')">添加
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
    Message,
    Cascader,
  } from 'element-ui';
  // import AdminApi from '@/api/AdminApi';
  import DoctorApi from '@/api/DoctorApi';
  import {RouterUtil} from '@/util/Util';
  import AreaData from '@/util/AreaData';

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
      [Cascader.name]: Cascader,
    },
    data: function () {
      return {
        areaData: AreaData,
        doctorInfo: {},
        doctorRules: {
          name: [
            {required: true, message: '请输入姓名', trigger: 'blur'},
            {min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur'}
          ],
          phone: [
            {required: true, message: '请输入手机号码', trigger: 'blur'},
            {pattern: /^\d{11}$/, message: '手机号码格式不正确'},
            {validator: this.validatePhone, trigger: 'blur'}
          ],
          gender: [
            {required: true, message: '请选择性别', trigger: 'blur'},
          ],
          addressIds: [
            {required: true, message: '请选择地址', trigger: 'blur'},
          ],
          medicalInstitution: [
            {required: true, message: '请输入执业机构', trigger: 'blur'},
            {min: 1, max: 64, message: '长度不能超过64个字符', trigger: 'blur'}
          ],
          medicalCategory: [
            {required: true, message: '请输入执业类别', trigger: 'blur'},
            {min: 1, max: 64, message: '长度不能超过64个字符', trigger: 'blur'}
          ],
        }
      }
    },
    methods: {
      onAddDoctorAction: function (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (typeof this.doctorInfo.addressIds !== 'undefined'
                && this.doctorInfo.addressIds.length >= 3) {
              this.doctorInfo.provinceId = this.doctorInfo.addressIds[0];
              this.doctorInfo.cityId = this.doctorInfo.addressIds[1];
              this.doctorInfo.districtId = this.doctorInfo.addressIds[2];
            }
            DoctorApi.addDoctor(this.doctorInfo).then(success => {
              if (success) {
                Message.success('添加成功即将跳转!');
                RouterUtil.goToBack(this.$route, this.$router, '/doctor/list');
              }
            });
          } else {
            return false;
          }
        });
      },
      validatePhone: function (rule, value, callback) {
        if (value === '') {
          callback(new Error('请输入手机号码'));
        } else {
          // UserApi.getUserByPhone(value).then(userInfo => {
          //   if (userInfo.userId) {
          //     callback(new Error('手机号码已经被注册了'));
          //   } else {
          //     callback();
          //   }
          // });
        }
      }
    }
  }
</script>
