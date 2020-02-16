<template>
    <div class="researcher-update">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/researcher/list' }">研究员管理</el-breadcrumb-item>
            <el-breadcrumb-item>修改研究员信息</el-breadcrumb-item>
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
                    <el-radio :label="1">
                        <i class="el-icon-male"></i>男
                    </el-radio>
                    <el-radio :label="2">
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
                             disabled>
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
                <el-button type="primary" icon="el-icon-edit"
                           @click="onUpdateResearcherAction('researcherInfo')">更新
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
  import {RouterUtil} from '@/util/Util';
  import OrganizationApi from '@/api/OrganizationApi';
  import UserApi from '@/api/UserApi';
  import ResearcherApi from '@/api/ResearcherApi';

  export default {
    data: function () {
      return {
        researcherInfo: {
          name: '',
          phone: '',
          gender: 1,
          organizationDepartmentList: [],
          medicalInstitution: '',
          medicalCategory: '',
          remark: ''
        },
        organizationList: [],
        departmentProps: {
          lazy: true,
          lazyLoad:
          this.loadDepartment
        }
        ,
        sourceOrganizationId: '',
        researcherInfoRules:
            {
              name: [
                {required: true, message: '请输入姓名', trigger: 'blur'},
                {min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur'}
              ],
              phone:
                  [
                    {required: true, message: '请输入手机号码', trigger: 'blur'},
                    {pattern: /^\d{11}$/, message: '手机号码格式不正确'},
                    {validator: this.validatePhone, trigger: 'blur'}
                  ],
              gender:
                  [
                    {required: true, message: '请选择性别', trigger: 'blur'},
                  ],
              addressIds:
                  [
                    {required: true, message: '请选择地址', trigger: 'blur'},
                  ],
              medicalInstitution:
                  [
                    {required: true, message: '请输入执业机构', trigger: 'blur'},
                    {min: 1, max: 64, message: '长度不能超过64个字符', trigger: 'blur'}
                  ],
              medicalCategory:
                  [
                    {required: true, message: '请输入执业类别', trigger: 'blur'},
                    {min: 1, max: 64, message: '长度不能超过64个字符', trigger: 'blur'}
                  ],
              remark:
                  [
                    {min: 0, max: 512, message: '长度不能超过512个字符', trigger: 'blur'}
                  ]
            }
      }
    },
    created: async function () {
      let researcherId = this.$route.params.researcherId;
      await this.loadResearcherInfo(researcherId);
      await this.loadOrganization();
    },
    methods: {
      validatePhone: function (rule, value, callback) {
        if (value === '') {
          callback(new Error('请输入手机号码'));
        } else {
          UserApi.getUserByPhone(value).then(userInfo => {
            if (userInfo.userId && this.researcherInfo.userId !== userInfo.userId) {
              callback(new Error('手机号码已经被注册了'));
            } else {
              callback();
            }
          });
        }
      },
      loadOrganization: async function () {
        OrganizationApi.getOrganization({
          currentPage: 1,
          pageSize: 1000
        }).then(data => {
          let organizationListTmp = [];
          data.data.forEach(o => {
            organizationListTmp.push({
              label: o.name,
              value: o.organizationId,
              children: []
            });
          });
          for (let i = 0; i < organizationListTmp.length; ++i) {
            let node = organizationListTmp[i];
            if (this.sourceOrganizationId === node.value) {
              this.loadOrganizationDepartment(node);
            }
          }
          this.organizationList = organizationListTmp;
        });

      },
      async loadOrganizationDepartment(node) {
        await OrganizationApi.getOrganizationDepartment({
          currentPage: 1,
          pageSize: 10000,
          organizationId: node.value
        }).then((response) => {
          let children = [];
          response.data.forEach(d => {
            let di = {
              label: d.name,
              value: d.departmentId,
              leaf: true
            };
            children.push(di);
          });
          node.children = children;
        });
      },
      loadDepartment: function (node, resolve) {
        const {data} = node;
        if (typeof data === 'undefined') {
          return;
        }
        if (typeof data.children !== 'undefined' && data.children.length > 0) {
          resolve();
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
      loadResearcherInfo: async function (researcherId) {
        await ResearcherApi.gerResearcherById(researcherId).then(researcherInfo => {
          this.researcherInfo.researcherId = researcherInfo.researcherId;
          this.researcherInfo.userId = researcherInfo.userId;
          this.researcherInfo.name = researcherInfo.userInfoRes.realName;
          this.researcherInfo.phone = researcherInfo.userInfoRes.phone;
          this.researcherInfo.gender = researcherInfo.userInfoRes.gender.code;
          this.researcherInfo.organizationDepartmentList = [researcherInfo.organizationId,
            researcherInfo.departmentId];
          this.researcherInfo.medicalInstitution = researcherInfo.medicalInstitution;
          this.researcherInfo.medicalCategory = researcherInfo.medicalCategory;
          this.researcherInfo.remark = researcherInfo.remark;
          this.sourceOrganizationId = researcherInfo.organizationId;
        })
      },
      onUpdateResearcherAction: function (formName) {
        this.researcherInfo.organizationId = this.researcherInfo.organizationDepartmentList[0];
        this.researcherInfo.departmentId = this.researcherInfo.organizationDepartmentList[1];
        this.$refs[formName].validate((valid) => {
          if (!valid) {
            return false;
          }
          ResearcherApi.updateResearcher(this.researcherInfo.researcherId,
              this.researcherInfo).then(success => {
            if (success) {
              this.$message.success('更新成功即将跳转!');
              RouterUtil.goToBack(this.$route, this.$router, '/researcher/list');
            }
          });
        });

      }
    }
  }
</script>
