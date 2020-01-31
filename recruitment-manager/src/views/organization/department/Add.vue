<template>
    <div class="organization-add">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/organization/list' }">机构管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/organization/department/list' }">科室列表
            </el-breadcrumb-item>
            <el-breadcrumb-item>添加科室</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form status-icon style="margin-top: 25px;width: 30%"
                 :rules="organizationDepartmentRules"
                 ref="organizationDepartmentInfo"
                 :model="organizationDepartmentInfo"
                 label-width="80px">
            <el-form-item label="科室名称" prop="name">
                <el-input v-model="organizationDepartmentInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="机构" prop="organizationId">
                <el-select v-model="organizationDepartmentInfo.organizationId" filterable
                           style="width: 50%"
                           placeholder="请选择研究机构">
                    <el-option
                        v-for="organization in organizationList"
                        :key="organization.organizationId"
                        :label="organization.name"
                        :value="organization.organizationId">
                            <span
                                style="float: left;margin-right:10px">{{ organization.name }}</span>
                        <span
                            style="float: right; color: #8492a6; font-size: 13px">{{ organization.address }}</span>
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-circle-plus-outline"
                           @click="onAddOrganizationDepartmentAction('organizationDepartmentInfo')">
                    添加
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
    Cascader, Select, Option,
  } from 'element-ui';

  import {RouterUtil} from '@/util/Util';
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
      [Select.name]: Select,
      [Option.name]: Option
    },
    data: function () {
      return {
        organizationDepartmentInfo: {},
        organizationList: [],
        organizationDepartmentRules: {
          name: [
            {required: true, message: '请输入科室名称', trigger: 'blur'},
            {min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur'}
          ],
          organizationId: [
            {required: true, message: '请选择机构', trigger: 'blur'},
          ]
        }
      }
    },
    created: function () {
      this.loadOrganization();
    },
    methods: {
      loadOrganization: function () {
        OrganizationApi.getOrganization({
          currentPage: 1,
          pageSize: 1000
        }).then(data => {
          this.organizationList = data.data;
        });
      },
      onAddOrganizationDepartmentAction: function (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            OrganizationApi.addOrganizationDepartment(this.organizationDepartmentInfo).then(
                success => {
                  if (success) {
                    Message.success('添加成功即将跳转!');
                    RouterUtil.goToBack(this.$route, this.$router, '/organization/department/list');
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
