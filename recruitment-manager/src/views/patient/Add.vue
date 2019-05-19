<template>
    <div class="doctor-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/patient/list' }">患者管理</el-breadcrumb-item>
            <el-breadcrumb-item>添加患者</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form status-icon style="margin-top: 25px;width: 30%" :rules="patientRules"
                 ref="patientInfo"
                 :model="patientInfo"
                 label-width="80px">
            <el-form-item label="姓名" prop="name">
                <el-input v-model="patientInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop="phone">
                <el-input v-model.number="patientInfo.phone"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="patientInfo.gender">
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
                    v-model="patientInfo.addressIds"
                    placeholder="请选择地址">
                </el-cascader>
            </el-form-item>
            <el-form-item label="年龄" prop="age">
                <el-input v-model.number="patientInfo.age"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-circle-plus-outline"
                           @click="onAddPatientAction('patientInfo')">添加
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
  import UserApi from '@/api/UserApi';
  import PatientApi from '@/api/PatientApi';
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
        patientInfo: {},
        patientRules: {
          name: [
            {required: true, message: '请输入姓名', trigger: 'blur'},
            {min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur'}
          ],
          phone: [
            {required: true, message: '请输入手机号码', trigger: 'blur'},
            {pattern: /\d{11}/, message: '手机号码格式不正确'},
            {validator: this.validatePhone, trigger: 'blur'}
          ],
          gender: [
            {required: true, message: '请选择性别', trigger: 'blur'},
          ],
          addressIds: [
            {required: true, message: '请选择地址', trigger: 'blur'},
          ],
          age: [
            {required: true, message: '请输入年龄', trigger: 'blur'},
            {type: 'number', message: '年龄必须为数字值'}
          ],
        }
      }
    },
    methods: {
      onAddPatientAction: function (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (typeof this.patientInfo.addressIds !== 'undefined'
                && this.patientInfo.addressIds.length >= 3) {
              this.patientInfo.provinceId = this.patientInfo.addressIds[0];
              this.patientInfo.cityId = this.patientInfo.addressIds[1];
              this.patientInfo.districtId = this.patientInfo.addressIds[2];
            }
            PatientApi.addPatient(this.patientInfo).then(success => {
              if (success) {
                Message.success('添加成功即将跳转!');
                RouterUtil.goToBack(this.$route, this.$router, '/patient/list');
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
