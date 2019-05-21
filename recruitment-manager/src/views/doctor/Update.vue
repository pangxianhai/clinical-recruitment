<template>
    <div class="doctor-update">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/doctor/list' }">医生管理</el-breadcrumb-item>
            <el-breadcrumb-item>修改医生信息</el-breadcrumb-item>
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
                <el-button type="primary" icon="el-icon-edit"
                           @click="onUpdateDoctorAction('doctorInfo')">更新
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
    Cascader,
    RadioGroup,
    Radio,
    Button,
    Icon,
    Message,
  } from 'element-ui';
  import AreaData from '@/util/AreaData';
  import DoctorApi from '@/api/DoctorApi';
  import UserApi from '@/api/UserApi';
  import {RouterUtil} from '@/util/Util';

  export default {
    components: {
      [Breadcrumb.name]: Breadcrumb,
      [BreadcrumbItem.name]: BreadcrumbItem,
      [Form.name]: Form,
      [FormItem.name]: FormItem,
      [Input.name]: Input,
      [Cascader.name]: Cascader,
      [RadioGroup.name]: RadioGroup,
      [Radio.name]: Radio,
      [Button.name]: Button,
      [Icon.name]: Icon,
    },
    data: function () {
      return {
        areaData: AreaData,
        doctorInfo: {
          medicalInstitution: '',
          medicalCategory: '',
          name: '',
          phone: '',
          gender: '',
          addressIds: []
        },
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
    created: function () {
      let doctorId = this.$route.params.doctorId;
      this.loadDoctorInfo(doctorId);
    },
    methods: {
      loadDoctorInfo: function (doctorId) {
        DoctorApi.getDoctorById(doctorId).then(doctorInfo => {
          this.doctorInfo.medicalInstitution = doctorInfo.medicalInstitution;
          this.doctorInfo.medicalCategory = doctorInfo.medicalCategory;
          this.doctorInfo.name = doctorInfo.userInfoVO.realName;
          this.doctorInfo.phone = doctorInfo.userInfoVO.phone;
          this.doctorInfo.gender = doctorInfo.userInfoVO.gender.code + "";
          this.doctorInfo.userId = doctorInfo.userInfoVO.userId;
          this.doctorInfo.doctorId = doctorInfo.doctorId;
          this.doctorInfo.addressIds = [doctorInfo.provinceId, doctorInfo.cityId,
            doctorInfo.districtId];
        })
      },
      validatePhone: function (rule, value, callback) {
        if (value === '') {
          callback(new Error('请输入手机号码'));
        } else {
          UserApi.getUserByPhone(value).then(userInfo => {
            if (userInfo.userId && this.doctorInfo.userId !== userInfo.userId) {
              callback(new Error('手机号码已经被注册了'));
            } else {
              callback();
            }
          });
        }
      },
      onUpdateDoctorAction: function (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (typeof this.doctorInfo.addressIds !== 'undefined'
                && this.doctorInfo.addressIds.length >= 3) {
              this.doctorInfo.provinceId = this.doctorInfo.addressIds[0];
              this.doctorInfo.cityId = this.doctorInfo.addressIds[1];
              this.doctorInfo.districtId = this.doctorInfo.addressIds[2];
            }
            DoctorApi.updateDoctor(this.doctorInfo.doctorId, this.doctorInfo).then(success => {
              if (success) {
                Message.success('更新成功即将跳转!');
                RouterUtil.goToBack(this.$route, this.$router, '/doctor/list');
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
