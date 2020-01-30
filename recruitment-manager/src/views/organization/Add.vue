<template>
    <div class="organization-add">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/organization/list' }">机构管理</el-breadcrumb-item>
            <el-breadcrumb-item>添加机构</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form status-icon style="margin-top: 25px;width: 30%" :rules="organizationRules"
                 ref="organizationInfo"
                 :model="organizationInfo"
                 label-width="80px">
            <el-form-item label="名称" prop="name">
                <el-input v-model="organizationInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="地址" prop="addressIds">
                <el-cascader
                    :options="areaData"
                    v-model="organizationInfo.addressIds"
                    placeholder="请选择地址">
                </el-cascader>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-circle-plus-outline"
                           @click="onAddOrganizationAction('organizationInfo')">添加
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

  import {RouterUtil} from '@/util/Util';
  import AreaData from '@/util/AreaData';
  import OrganizationApi from '@/api/OrganizationApi';

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
        organizationInfo: {},
        organizationRules: {
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
      onAddOrganizationAction: function (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (typeof this.organizationInfo.addressIds !== 'undefined'
                && this.organizationInfo.addressIds.length >= 3) {
              this.organizationInfo.provinceId = this.organizationInfo.addressIds[0];
              this.organizationInfo.cityId = this.organizationInfo.addressIds[1];
              this.organizationInfo.districtId = this.organizationInfo.addressIds[2];
            }
            OrganizationApi.addOrganization(this.organizationInfo).then(success => {
              if (success) {
                Message.success('添加成功即将跳转!');
                RouterUtil.goToBack(this.$route, this.$router, '/organization/list');
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
