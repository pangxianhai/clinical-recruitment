<template>
    <div class="reference-add">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/reference/list' }">推荐人管理</el-breadcrumb-item>
            <el-breadcrumb-item>添加推荐人</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form status-icon style="margin-top: 25px;width: 35%" :rules="referenceRules"
                 ref="referenceInfo"
                 :model="referenceInfo"
                 label-width="100px">
            <el-form-item label="姓名" prop="name">
                <el-input v-model="referenceInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop="phone">
                <el-input v-model.number="referenceInfo.phone"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="referenceInfo.gender">
                    <el-radio label="1">
                        <i class="el-icon-male"></i>男
                    </el-radio>
                    <el-radio label="2">
                        <i class="el-icon-female"></i>女
                    </el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="研究科室" prop="departmentList"
                          style="text-align: left">
                <el-cascader style="width:100%"
                             v-model="referenceInfo.departmentList"
                             :options="hospitalList"
                             :props="departmentProps"
                             clearable filterable>
                </el-cascader>
            </el-form-item>
            <el-form-item label="角色" prop="role">
                <el-radio-group v-model="referenceInfo.role">
                    <el-radio label="1">
                        <i class="el-icon-s-custom"></i>研究员
                    </el-radio>
                    <el-radio label="2">
                        <i class="el-icon-user"></i>医生
                    </el-radio>
                </el-radio-group>
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
                <el-button type="primary" icon="el-icon-circle-plus-outline"
                           @click="onAddReferenceAction('referenceInfo')">添加
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
  import {RouterUtil, CollectionUtil} from '@/util/Util';
  import AreaData from '@/util/AreaData';
  import UserApi from '@/api/UserApi';
  import ReferenceApi from '@/api/ReferenceApi';
  import HospitalApi from '@/api/HospitalApi';
  import DepartmentApi from '@/api/DepartmentApi';

  export default {
    data: function () {
      return {
        areaData: AreaData,
        referenceInfo: {},
        hospitalList: [],
        departmentProps: {
          lazy: true,
          lazyLoad: this.loadDepartment
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
          role: [
            {required: true, message: '请选择角色', trigger: 'blur'},
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
          departmentList: [
            {required: true, message: '请选择机构科室', trigger: 'blur'}
          ],
        }
      }
    },
    created: function () {
      this.loadHospital();
    },
    methods: {
      onAddReferenceAction: function (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let referenceAddInfo = {};
            Object.assign(referenceAddInfo, this.referenceInfo);
            if (CollectionUtil.isNotEmpty(this.referenceInfo.departmentList)
                && this.referenceInfo.departmentList.length === 2) {
              referenceAddInfo.hospitalId = this.referenceInfo.departmentList[0];
              referenceAddInfo.departmentId = this.referenceInfo.departmentList[1];
            }
            delete referenceAddInfo.departmentList;
            ReferenceApi.addReference(referenceAddInfo).then(success => {
              if (success) {
                this.$message.success('添加成功即将跳转!');
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
            if (userInfo.userId) {
              callback(new Error('手机号码已经被注册了'));
            } else {
              callback();
            }
          });
        }
      },
      loadHospital: function () {
        HospitalApi.getHospital({
          currentPage: 1,
          pageSize: 1000
        }).then(data => {
          let hospitalListTmp = [];
          data.data.forEach(o => {
            hospitalListTmp.push({
              label: o.name,
              value: o.hospitalId
            });
          });
          this.hospitalList = hospitalListTmp;
        });
      },
      loadDepartment: (node, resolve) => {
        const {data} = node;
        if (typeof data === 'undefined') {
          return;
        }
        DepartmentApi.getDepartment({
          currentPage: 1,
          pageSize: 10000,
          hospitalId: data.value
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
    }
  }
</script>
