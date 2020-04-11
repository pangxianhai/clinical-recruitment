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
                 :rules="departmentRules"
                 ref="departmentInfo"
                 :model="departmentInfo"
                 label-width="80px">
            <el-form-item label="科室名称" prop="name">
                <el-input v-model="departmentInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="机构" prop="organizationId">
                <el-select v-model="departmentInfo.hospitalId" filterable
                           style="width: 50%"
                           placeholder="请选择研究机构">
                    <el-option
                        v-for="hospitalInfo in hospitalList"
                        :key="hospitalInfo.hospitalId"
                        :label="hospitalInfo.name"
                        :value="hospitalInfo.hospitalId">
                        <span style="float: left;margin-right:10px">{{ hospitalInfo.name }}</span>
                        <span
                            style="float: right; color: #8492a6; font-size: 13px">{{ hospitalInfo.address }}</span>
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-circle-plus-outline"
                           @click="onAddDepartmentAction('departmentInfo')">
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
  import HospitalApi from '@/api/HospitalApi';
  import DepartmentApi from '@/api/DepartmentApi';

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
        departmentInfo: {},
        hospitalList: [],
        departmentRules: {
          name: [
            {required: true, message: '请输入科室名称', trigger: 'blur'},
            {min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur'}
          ],
          hospitalId: [
            {required: true, message: '请选择机构', trigger: 'blur'},
          ]
        }
      }
    },
    created: function () {
      this.loadHospital();
    },
    methods: {
      loadHospital: function () {
        HospitalApi.getHospital({
          currentPage: 1,
          pageSize: 1000
        }).then(data => {
          this.hospitalList = data.data;
        });
      },
      onAddDepartmentAction: function (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            DepartmentApi.addDepartment(this.departmentInfo).then(
                success => {
                  if (success) {
                    Message.success('添加成功即将跳转!');
                    RouterUtil.goToBack(this.$route, this.$router, '/hospital/department/list');
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
