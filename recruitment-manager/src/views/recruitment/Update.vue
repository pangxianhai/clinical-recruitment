<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>项目管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/recruitment/list' }">项目列表</el-breadcrumb-item>
            <el-breadcrumb-item>编辑项目</el-breadcrumb-item>
        </el-breadcrumb>
        <div style="text-align: center;margin-top: 25px">
            <el-form :model="recruitmentInfo" :rules="recruitmentInfoRules"
                     ref="recruitmentInfoForm"
                     style="width: 90%;margin: auto" label-width="100px">
                <el-form-item label="标题：" prop="title">
                    <el-input v-model="recruitmentInfo.title" placeholder="标题"></el-input>
                </el-form-item>
                <el-form-item label="登记编号：" prop="registerCode">
                    <el-input v-model="recruitmentInfo.registerCode" placeholder="登记编号"></el-input>
                </el-form-item>
                <el-form-item label="试验分期：" prop="practiceStages">
                    <el-input v-model="recruitmentInfo.practiceStages"
                              placeholder="试验分期"></el-input>
                </el-form-item>
                <el-form-item label="适应症：" prop="indication">
                    <el-input v-model="recruitmentInfo.indication" placeholder="适应症"></el-input>
                </el-form-item>
                <el-form-item label="药物名称：" prop="drugName">
                    <el-input v-model="recruitmentInfo.drugName" placeholder="药物名称"></el-input>
                </el-form-item>
                <el-form-item label="药物类型：" prop="drugType">
                    <el-input v-model="recruitmentInfo.drugType" placeholder="药物类型"></el-input>
                </el-form-item>
                <el-form-item label="招募人数：" prop="recruitmentNumber">
                    <el-input v-model.number="recruitmentInfo.recruitmentNumber"
                              placeholder="招募人数"></el-input>
                </el-form-item>
                <el-form-item label="启止时间：" style="text-align: left" prop="startEndTime">
                    <el-date-picker
                        type="daterange"
                        v-model="recruitmentInfo.startEndTime"
                        format="yyyy年MM月dd日"
                        value-format="yyyy-MM-dd"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="简介：" prop="introduction">
                    <quill-editor
                        v-model="recruitmentInfo.introduction"
                        :options="editorOption">
                    </quill-editor>
                </el-form-item>
                <el-form-item label="治疗方案：" prop="treatmentPlan">
                    <quill-editor
                        v-model="recruitmentInfo.treatmentPlan"
                        :options="editorOption">
                    </quill-editor>
                </el-form-item>
                <el-form-item label="入排标准：" prop="entryCriteria">
                    <quill-editor
                        v-model="recruitmentInfo.entryCriteria"
                        :options="editorOption">
                    </quill-editor>
                </el-form-item>
                <el-form-item label="患者权益：" prop="patientRights">
                    <quill-editor
                        v-model="recruitmentInfo.patientRights"
                        :options="editorOption">
                    </quill-editor>
                </el-form-item>
                <el-form-item label="研究科室：" prop="organizationDepartmentList"
                              style="text-align: left">
                    <el-cascader style="width:50%"
                                 v-model="recruitmentInfo.organizationDepartmentList"
                                 :options="organizationList"
                                 :props="departmentProps"
                                 clearable filterable>
                    </el-cascader>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" icon="el-icon-edit"
                               @click="onUpdateRecruitmentAction('recruitmentInfoForm')">提交
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
  import {
    Breadcrumb,
    BreadcrumbItem,
    Form,
    FormItem,
    Input,
    InputNumber,
    DatePicker,
    Button,
    Col,
    Row,
    Cascader,
    Message, Select, Option
  } from 'element-ui';

  import AreaData from '@/util/AreaData';
  import RecruitmentApi from '@/api/RecruitmentApi';
  import {RouterUtil} from '@/util/Util';
  import OrganizationApi from '@/api/OrganizationApi';

  export default {
    components: {
      [Breadcrumb.name]: Breadcrumb,
      [BreadcrumbItem.name]: BreadcrumbItem,
      [Form.name]: Form,
      [FormItem.name]: FormItem,
      [Input.name]: Input,
      [InputNumber.name]: InputNumber,
      [DatePicker.name]: DatePicker,
      [Button.name]: Button,
      [Col.name]: Col,
      [Row.name]: Row,
      [Cascader.name]: Cascader,
      [Select.name]: Select,
      [Option.name]: Option
    },
    data: function () {
      return {
        editorOption: {
          modules: {
            toolbar: [
              ['bold', 'italic', 'underline', 'strike', 'blockquote', 'code-block'],

              [{'list': 'ordered'}, {'list': 'bullet'}],
              [{'indent': '-1'}, {'indent': '+1'}],

              [{'size': ['small', false, 'large', 'huge']}],
              [{'header': [1, 2, 3, 4, 5, 6, false]}],

              [{'color': []}, {'background': []}],
              [{'font': []}],
              [{'align': []}],
            ]
          },
        },
        recruitmentInfo: {
          title: '',
          registerCode: '',
          createdTime: "",
          drugName: "",
          drugType: "",
          entryCriteria: "",
          indication: "",
          introduction: "",
          patientRights: "",
          practiceStages: "",
          recruitmentNumber: 0,
          startTime: "",
          status: {},
          stopTime: "",
          treatmentPlan: "",
          startEndTime: [],
          organizationDepartmentList: []
        },
        organizationList: [],
        areaData: AreaData,
        departmentProps: {
          multiple: true,
          checkStrictly: true,
          lazy: true,
          lazyLoad: this.loadDepartment
        },
        recruitmentInfoRules: {
          title: [
            {required: true, message: '请输入项目标题', trigger: 'blur'},
            {min: 1, max: 32, message: '最大只能输入32个字符', trigger: 'blur'}
          ],
          registerCode: [
            {required: true, message: '请输入登记编号', trigger: 'blur'},
            {min: 1, max: 32, message: '最大只能输入32个字符', trigger: 'blur'}
          ],
          practiceStages: [
            {required: true, message: '请输入试验分期', trigger: 'blur'},
            {min: 1, max: 32, message: '最大只能输入32个字符', trigger: 'blur'}
          ],
          indication: [
            {required: true, message: '请输入适应症', trigger: 'blur'},
            {min: 1, max: 32, message: '最大只能输入32个字符', trigger: 'blur'}
          ],
          drugName: [
            {required: true, message: '请输入药物名称', trigger: 'blur'},
            {min: 1, max: 1024, message: '最大只能输入1024个字符', trigger: 'blur'}
          ],
          drugType: [
            {required: true, message: '请输入药物类型', trigger: 'blur'},
            {min: 1, max: 512, message: '最大只能输入512个字符', trigger: 'blur'}
          ],
          recruitmentNumber: [
            {required: true, message: '请输入招募人数', trigger: 'blur'},
            {type: 'number', message: '招募人数只能是数字值'},
          ],
          introduction: [
            {required: true, message: '请输入简介', trigger: 'blur'},
            {min: 1, max: 5000, message: '最大只能输入5000个字符', trigger: 'blur'}
          ],
          treatmentPlan: [
            {required: true, message: '请输入治疗方案', trigger: 'blur'},
            {min: 1, max: 5000, message: '最大只能输入5000个字符', trigger: 'blur'}
          ],
          entryCriteria: [
            {required: true, message: '请输入入排标准', trigger: 'blur'},
            {min: 1, max: 5000, message: '最大只能输入5000个字符', trigger: 'blur'}
          ],
          patientRights: [
            {required: true, message: '请输入患者权益', trigger: 'blur'},
            {min: 1, max: 5000, message: '最大只能输入5000个字符', trigger: 'blur'}
          ],
          startEndTime: [
            {required: true, message: '请选择启止日期', trigger: 'blur'},
          ],
          organizationList: [
            {required: true, message: '请选择研究机构', trigger: 'blur'},
          ]
        }
      }
    },
    created: function () {
      let recruitmentId = this.$route.params.recruitmentId;
      this.loadRecruitmentInfo(recruitmentId);
      this.loadOrganization();
    },
    methods: {
      loadRecruitmentInfo: function (recruitmentId) {
        RecruitmentApi.getRecruitmentById(recruitmentId).then((recruitmentInfo) => {
          Object.assign(this.recruitmentInfo, recruitmentInfo);
          this.recruitmentInfo.startEndTime = [recruitmentInfo.startTime, recruitmentInfo.stopTime];
          let organizationDepartmentList = [];
          recruitmentInfo.departmentInfoBoList.forEach(department => {
           let organizationDepartment=[department.organizationId,department.departmentId];
            organizationDepartmentList.push(organizationDepartment);
          });
          window.console.log(organizationDepartmentList);
          this.recruitmentInfo.organizationDepartmentList = organizationDepartmentList;
          delete this.recruitmentInfo.departmentInfoBoList;
        });
      },
      loadOrganization: function () {
        OrganizationApi.getOrganization({
          currentPage: 1,
          pageSize: 1000
        }).then(data => {
          this.organizationList = [];
          data.data.forEach(o => {
            this.organizationList.push({
              label: o.name,
              value: o.organizationId,
              disabled: true
            });
          });
        });
      },
      onUpdateRecruitmentAction: function (recruitmentInfoForm) {
        if (typeof this.recruitmentInfo.startEndTime !== 'undefined'
            && this.recruitmentInfo.startEndTime.length >= 2) {
          this.recruitmentInfo.startTime = this.recruitmentInfo.startEndTime[0];
          this.recruitmentInfo.stopTime = this.recruitmentInfo.startEndTime[1];
        }
        this.$refs[recruitmentInfoForm].validate((valid) => {
          if (valid) {
            RecruitmentApi.updateRecruitment(this.recruitmentInfo.recruitmentId,
                this.recruitmentInfo).then(
                success => {
                  if (success) {
                    Message.success('修改成功，即将跳转');
                    RouterUtil.goToBack(this.$route, this.$router);
                  }
                });
          } else {
            return false;
          }
        });
      },

      loadDepartment: function (node, resolve) {
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
    }
  }
</script>
