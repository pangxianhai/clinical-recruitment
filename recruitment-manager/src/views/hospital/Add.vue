<template>
    <div class="organization-add">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/organization/list' }">机构管理</el-breadcrumb-item>
            <el-breadcrumb-item>添加机构</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form status-icon style="margin-top: 25px;width: 30%" :rules="hospitalRules"
                 ref="hospitalInfo"
                 :model="hospitalInfo"
                 label-width="80px">
            <el-form-item label="名称" prop="name">
                <el-input v-model="hospitalInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="地址" prop="addressIds">
                <el-cascader
                    :options="areaData"
                    v-model="hospitalInfo.addressIds"
                    placeholder="请选择地址">
                </el-cascader>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-circle-plus-outline"
                           @click="onAddHospitalAction('hospitalInfo')">添加
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
  import {RouterUtil} from '@/util/Util';
  import AreaData from '@/util/AreaData';
  import HospitalApi from '@/api/HospitalApi';

  export default {
    data: function () {
      return {
        areaData: AreaData,
        hospitalInfo: {},
        hospitalRules: {
          name: [
            {required: true, message: '请输入机构名称', trigger: 'blur'},
            {min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur'}
          ],
          addressIds: [
            {required: true, message: '请选择地址', trigger: 'blur'},
          ]
        }
      }
    },
    methods: {
      onAddHospitalAction: function (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (typeof this.hospitalInfo.addressIds !== 'undefined'
                && this.hospitalInfo.addressIds.length >= 3) {
              this.hospitalInfo.provinceId = this.hospitalInfo.addressIds[0];
              this.hospitalInfo.cityId = this.hospitalInfo.addressIds[1];
              this.hospitalInfo.districtId = this.hospitalInfo.addressIds[2];
            }
            HospitalApi.addHospital(this.hospitalInfo).then(success => {
              if (success) {
                this.$message.success('添加成功即将跳转!');
                RouterUtil.goToBack(this.$route, this.$router, '/hospital/list');
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
