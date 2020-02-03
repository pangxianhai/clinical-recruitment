<template>
    <div class="researcher-add">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/researcher/list' }">研究员管理</el-breadcrumb-item>
            <el-breadcrumb-item>添加研究员</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form status-icon style="margin-top: 25px;width: 30%" :rules="researcherInfoRules"
                 ref="researcherInfo"
                 :model="researcherInfo"
                 label-width="100px">
            <el-form-item label="姓名" prop="name">
                <el-input v-model="researcherInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop="phone">
                <el-input v-model.number="researcherInfo.phone"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="researcherInfo.gender">
                    <el-radio label="1">
                        <i class="el-icon-male"></i>男
                    </el-radio>
                    <el-radio label="2">
                        <i class="el-icon-female"></i>女
                    </el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="研究科室：" prop="organizationDepartmentList"
                          style="text-align: left">
                <el-cascader style="width:100%"
                             v-model="researcherInfo.organizationDepartmentList"
                             :options="organizationList"
                             :props="departmentProps"
                             clearable filterable>
                </el-cascader>
            </el-form-item>
            <el-form-item label="执业机构" prop="medicalInstitution">
                <el-input v-model="researcherInfo.medicalInstitution"></el-input>
            </el-form-item>
            <el-form-item label="执业类别" prop="medicalCategory">
                <el-input v-model="researcherInfo.medicalCategory"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="remark">
                <el-input v-model="researcherInfo.remark"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-circle-plus-outline"
                           @click="onAddResearcherAction('researcherInfo')">添加
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<style>

</style>

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
    Cascader, Message,
  } from 'element-ui';
  import OrganizationApi from '@/api/OrganizationApi';
  import UserApi from '@/api/UserApi';
  import ResearcherApi from '@/api/ResearcherApi';
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
      [Cascader.name]: Cascader,
    },
    data: function () {
      return {
        researcherInfo: {},
        organizationList: [],
        departmentProps: {
          lazy: true,
          lazyLoad: this.loadDepartment
        },
        researcherInfoRules: {
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
          organizationDepartmentList: [
            {required: true, message: '请选择机构科室', trigger: 'blur'}
          ],
          remark: [
            {min: 0, max: 512, message: '长度不能超过512个字符', trigger: 'blur'}
          ]
        }
      }
    },
    created: function () {
      window.console.log(typeof this.loadOrganization);
      this.loadOrganization();
    },
    methods: {
      onAddResearcherAction: function () {
        let addParam = {};
        if (typeof this.researcherInfo.organizationDepartmentList !== 'undefined'
            && this.researcherInfo.organizationDepartmentList != null) {
          addParam.organizationId = this.researcherInfo.organizationDepartmentList[0];
          addParam.departmentId = this.researcherInfo.organizationDepartmentList[1];
        }
        Object.assign(addParam, this.researcherInfo);
        delete addParam.organizationDepartmentList;
        ResearcherApi.addResearcher(addParam).then(success => {
          if (success) {
            Message.success('添加成功即将跳转!');
            RouterUtil.goToBack(this.$route, this.$router, '/researcher/list');
          }
        });
      },
      loadOrganization: function () {
        OrganizationApi.getOrganization({
          currentPage: 1,
          pageSize: 1000
        }).then(data => {
          let organizationListTmp = [];
          data.data.forEach(o => {
            organizationListTmp.push({
              label: o.name,
              value: o.organizationId
            });
          });
          this.organizationList = organizationListTmp;
        });
      },
      loadDepartment: (node, resolve) => {
        const {data} = node;
        if (typeof data === 'undefined') {
          return;
        }
        OrganizationApi.getOrganizationDepartment({
          currentPage: 1,
          pageSize: 10000,
          organizationId: data.value
        }).then(data => {
          let nodes = [];
          data.data.forEach(d => {
            let di = {
              label: d.name,
              value: d.departmentId,
              leaf: true
            };
            nodes.push(di);
          });
          resolve(nodes);
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