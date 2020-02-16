<template>
    <div class="reference-update">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/reference/list' }">推荐人管理</el-breadcrumb-item>
            <el-breadcrumb-item>更新推荐人</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form status-icon style="margin-top: 25px;width: 30%" :rules="referenceRules"
                 ref="referenceInfo"
                 :model="referenceInfo"
                 label-width="80px">
            <el-form-item label="姓名" prop="name">
                <el-input v-model="referenceInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop="phone">
                <el-input v-model.number="referenceInfo.phone"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="referenceInfo.gender">
                    <el-radio :label="1">
                        <i class="el-icon-male"></i>男
                    </el-radio>
                    <el-radio :label="2">
                        <i class="el-icon-female"></i>女
                    </el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="地址" prop="addressIds">
                <el-cascader
                    :options="areaData"
                    v-model="referenceInfo.addressIds"
                    placeholder="请选择地址">
                </el-cascader>
            </el-form-item>
            <el-form-item label="执业机构" prop="medicalInstitution">
                <el-input v-model="referenceInfo.medicalInstitution"></el-input>
            </el-form-item>
            <el-form-item label="执业类别" prop="medicalCategory">
                <el-input v-model="referenceInfo.medicalCategory"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="remark">
                <el-input v-model="referenceInfo.remark"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-edit"
                           @click="onUpdateReferenceAction('referenceInfo')">更新
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
  import UserApi from '@/api/UserApi';
  import AreaData from '@/util/AreaData';
  import ReferenceApi from '@/api/ReferenceApi';
  import {RouterUtil} from '@/util/Util';

  export default {
    data: function () {
      return {
        areaData: AreaData,
        referenceInfo: {
          name: '',
          phone: '',
          gender: 0,
          addressIds: [],
          medicalInstitution: '',
          medicalCategory: '',
          remark: '',
          userId: ''
        },
        referenceRules: {
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
          remark: [
            {min: 0, max: 64, message: '长度不能超过64个字符', trigger: 'blur'}
          ],
        }
      }
    },
    created: function () {
      let referenceId = this.$route.params.referenceId;
      this.loadReferenceInfo(referenceId);
    },
    methods: {
      loadReferenceInfo: function (referenceId) {
        ReferenceApi.getReferenceDetail(referenceId).then(referenceInfo => {
          Object.assign(this.referenceInfo, referenceInfo);
          this.referenceInfo.name = referenceInfo.userInfoRes.realName;
          this.referenceInfo.phone = referenceInfo.userInfoRes.phone;
          this.referenceInfo.gender = referenceInfo.userInfoRes.gender.code;
          this.referenceInfo.addressIds = [referenceInfo.provinceId, referenceInfo.cityId,
            referenceInfo.districtId];
        });
      },
      onUpdateReferenceAction: function (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (typeof this.referenceInfo.addressIds !== 'undefined'
                && this.referenceInfo.addressIds.length >= 3) {
              this.referenceInfo.provinceId = this.referenceInfo.addressIds[0];
              this.referenceInfo.cityId = this.referenceInfo.addressIds[1];
              this.referenceInfo.districtId = this.referenceInfo.addressIds[2];
            }
            ReferenceApi.updateReference(this.referenceInfo.referenceId, this.referenceInfo).then(
                success => {
                  if (success) {
                    this.$message.success('更新成功即将跳转!');
                    RouterUtil.goToBack(this.$route, this.$router, '/reference/list');
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
            if (userInfo.userId && this.referenceInfo.userId !== userInfo.userId) {
              callback(new Error('手机号码已经被注册了'));
            } else {
              callback();
            }
          });
        }
      },
    }
  }
</script>