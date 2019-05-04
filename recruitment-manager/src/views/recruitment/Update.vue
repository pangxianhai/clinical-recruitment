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
                     style="width: 60%;margin: auto" label-width="100px">
                <el-form-item label="标题：" prop="title">
                    <el-input v-model="recruitmentInfo.title" placeholder="标题"></el-input>
                </el-form-item>
                <el-form-item label="登记编号：" prop="registerCode">
                    <el-input v-model="recruitmentInfo.registerCode" placeholder="登记编号"></el-input>
                </el-form-item>
                <el-form-item label="实验分期：" prop="practiceStages">
                    <el-input v-model="recruitmentInfo.practiceStages"
                              placeholder="实验分期"></el-input>
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
                    <el-input v-model="recruitmentInfo.introduction" type="textarea"
                              placeholder="简介"></el-input>
                </el-form-item>
                <el-form-item label="治疗方案：" prop="treatmentPlan">
                    <el-input v-model="recruitmentInfo.treatmentPlan" type="textarea"
                              placeholder="治疗方案"></el-input>
                </el-form-item>
                <el-form-item label="入排标准：" prop="entryCriteria">
                    <el-input v-model="recruitmentInfo.entryCriteria" type="textarea"
                              placeholder="入排标准"></el-input>
                </el-form-item>
                <el-form-item label="患者权益：" prop="patientRights">
                    <el-input v-model="recruitmentInfo.patientRights" type="textarea"
                              placeholder="患者权益"></el-input>
                </el-form-item>
                <el-form-item
                    v-for="(center, index) in recruitmentInfo.researchCenterList"
                    :label="'研究中心' + (index + 1) +':'"
                    :prop="'researchCenterList.' + index + '.name'"
                    :rules="{
                     validator: validateCenter, trigger: 'blur'
                 }"
                    :key="index">
                    <el-row type="flex" justify="space-between">
                        <el-col :span="11">
                            <el-cascader
                                :options="areaData"
                                v-model="center.areaIds"
                                placeholder="请选择地址">
                            </el-cascader>
                        </el-col>
                        <el-col :span="8">
                            <el-input
                                v-model="center.name" placeholder="研究中心名称"></el-input>
                        </el-col>

                        <el-col :span="2">
                            <el-button type="primary" size="mini" icon="el-icon-plus"
                                       @click="addResearchCenter"></el-button>
                        </el-col>
                        <el-col :span="2">
                            <el-button type="danger" size="mini" icon="el-icon-delete"
                                       @click.prevent="removeResearchCenter(index)"></el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary"
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
    Message
  } from 'element-ui';

  import AreaData from '@/util/AreaData';
  import RecruitmentApi from '@/api/RecruitmentApi';
  import {RouterUtil} from '@/util/Util';

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
    },
    data: function () {
      return {
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
          researchCenterList: [{}]
        },
        areaData: AreaData,
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
            {required: true, message: '请输入实验分期', trigger: 'blur'},
            {min: 1, max: 32, message: '最大只能输入32个字符', trigger: 'blur'}
          ],
          indication: [
            {required: true, message: '请输入适应症', trigger: 'blur'},
            {min: 1, max: 32, message: '最大只能输入32个字符', trigger: 'blur'}
          ],
          drugName: [
            {required: true, message: '请输入药物名称', trigger: 'blur'},
            {min: 1, max: 32, message: '最大只能输入32个字符', trigger: 'blur'}
          ],
          drugType: [
            {required: true, message: '请输入药物类型', trigger: 'blur'},
            {min: 1, max: 32, message: '最大只能输入32个字符', trigger: 'blur'}
          ],
          recruitmentNumber: [
            {required: true, message: '请输入招募人数', trigger: 'blur'},
            {type: 'number', message: '招募人数只能是数字值'},
          ],
          introduction: [
            {required: true, message: '请输入简介', trigger: 'blur'},
            {min: 1, max: 256, message: '最大只能输入256个字符', trigger: 'blur'}
          ],
          treatmentPlan: [
            {required: true, message: '请输入治疗方案', trigger: 'blur'},
            {min: 1, max: 256, message: '最大只能输入256个字符', trigger: 'blur'}
          ],
          entryCriteria: [
            {required: true, message: '请输入入排标准', trigger: 'blur'},
            {min: 1, max: 256, message: '最大只能输入256个字符', trigger: 'blur'}
          ],
          patientRights: [
            {required: true, message: '请输入患者权益', trigger: 'blur'},
            {min: 1, max: 256, message: '最大只能输入256个字符', trigger: 'blur'}
          ],
          startEndTime: [
            {required: true, message: '请选择启止日期', trigger: 'blur'},
          ]
        },
        validateCenter: (rule, value, callback) => {
          let index = parseInt(rule.field.split('.')[1]);
          let centerInfo = this.recruitmentInfo.researchCenterList[index];
          if (typeof centerInfo.areaIds === 'undefined' || centerInfo.areaIds.length === 0) {
            callback(new Error('请选择研究中心地址'));
            return;
          }
          if (typeof centerInfo.name === 'undefined' || centerInfo.name.length === 0) {
            callback(new Error('请填入研究中心名称'));
            return;
          }
          callback();
        },
      }
    },
    created: function () {
      let recruitmentId = this.$route.params.recruitmentId;
      this.loadRecruitmentInfo(recruitmentId);
      this.loadRecruitmentCenter(recruitmentId);
    },
    methods: {
      loadRecruitmentInfo: function (recruitmentId) {
        RecruitmentApi.getRecruitmentById(recruitmentId).then((recruitmentInfo) => {
          Object.assign(this.recruitmentInfo, recruitmentInfo);
          this.recruitmentInfo.startEndTime = [recruitmentInfo.startTime, recruitmentInfo.stopTime];
        });
      },
      loadRecruitmentCenter: function (recruitmentId) {
        RecruitmentApi.getRecruitmentCenterById(recruitmentId).then((researchCenterList) => {
          this.recruitmentInfo.researchCenterList = researchCenterList;
          this.recruitmentInfo.researchCenterList.forEach(center => {
            center.areaIds = [center.provinceId, center.cityId, center.districtId]
          });
        })
      },
      onUpdateRecruitmentAction: function (recruitmentInfoForm) {
        if (typeof this.recruitmentInfo.startEndTime !== 'undefined'
            && this.recruitmentInfo.startEndTime.length >= 2) {
          this.recruitmentInfo.startTime = this.recruitmentInfo.startEndTime[0];
          this.recruitmentInfo.stopTime = this.recruitmentInfo.startEndTime[1];
        }
        this.recruitmentInfo.researchCenterList.forEach(center => {
          if (typeof center.areaIds !== 'undefined' && center.areaIds.length >= 3) {
            center.provinceId = center.areaIds[0];
            center.cityId = center.areaIds[1];
            center.districtId = center.areaIds[2];
          }
        });
        this.$refs[recruitmentInfoForm].validate((valid) => {
          if (valid) {
            RecruitmentApi.updateRecruitment(this.recruitmentInfo).then(success => {
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
      removeResearchCenter(index) {
        if (this.recruitmentInfo.researchCenterList.length === 1) {
          Message.error("请至少保留一个研究中心");
          return;
        }
        if (index !== -1) {
          this.recruitmentInfo.researchCenterList.splice(index, 1)
        }
      },
      addResearchCenter() {
        this.recruitmentInfo.researchCenterList.push({
          key: Date.now()
        });
      },
    }
  }
</script>
