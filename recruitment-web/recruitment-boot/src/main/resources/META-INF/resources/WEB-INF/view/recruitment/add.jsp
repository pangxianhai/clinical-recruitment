<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes"/>
    <title>添加招募信息</title>
    <link rel="stylesheet" href="/static/css/element.css">
    <style>
        body {
            margin: 0;
        }

        .el-header {
            padding: 0;
            background: #3FB83C;
            color: #FFF;
            height: 4em !important;
            line-height: 4em;
            font-size: 15px;
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1;
        }

        .el-header .title {
            width: 100%;
            text-align: center;
            float: left;
        }

        .el-main {
            padding: 5em 5px 0 5px;
        }

        .submit-item {
            text-align: center;
        }

        .header-left {
            float: left;
            line-height: 4em;
            position: fixed;
            padding-left: 8px;
        }

        .el-message {
            width: 98%;
            min-width: auto !important;
        }
    </style>
</head>
<body>
<div id="recruitment_add">
    <el-container>
        <el-header>
            <i class="el-icon-arrow-left header-left"></i>
            <span class="title">添加招募信息</span>
        </el-header>
        <el-main>
            <el-form :model="recruitmentAddForm"
                     :rules="recruitmentAddRules"
                     ref="recruitmentAddForm">
                <el-form-item prop="title">
                    <el-input v-model="recruitmentAddForm.title" placeholder="标题"></el-input>
                </el-form-item>
                <el-form-item prop="registerCode">
                    <el-input v-model="recruitmentAddForm.registerCode"
                              placeholder="登记编号"></el-input>
                </el-form-item>
                <el-form-item prop="practiceStages">
                    <el-input v-model="recruitmentAddForm.practiceStages"
                              placeholder="实验分期"></el-input>
                </el-form-item>
                <el-form-item prop="indication">
                    <el-input v-model="recruitmentAddForm.indication" placeholder="适应症状"></el-input>
                </el-form-item>
                <el-form-item prop="drugName">
                    <el-input v-model="recruitmentAddForm.drugName" placeholder="药物名称"></el-input>
                </el-form-item>
                <el-form-item prop="drugType">
                    <el-input v-model="recruitmentAddForm.drugType" placeholder="药物类型"></el-input>
                </el-form-item>
                <el-form-item prop="recruitmentNumber">
                    <el-input v-model.number="recruitmentAddForm.recruitmentNumber"
                              placeholder="招募人数"></el-input>
                </el-form-item>
                <el-form-item prop="introduction">
                    <el-input v-model="recruitmentAddForm.introduction" placeholder="简介"></el-input>
                </el-form-item>
                <el-form-item prop="treatmentPlan">
                    <el-input v-model="recruitmentAddForm.treatmentPlan"
                              placeholder="治疗方案"></el-input>
                </el-form-item>
                <el-form-item prop="screeningStandard">
                    <el-input v-model="recruitmentAddForm.screeningStandard"
                              placeholder="初筛要点"></el-input>
                </el-form-item>
                <el-form-item prop="entryCriteria">
                    <el-input v-model="recruitmentAddForm.entryCriteria"
                              placeholder="入排标准"></el-input>
                </el-form-item>
                <el-form-item prop="researchCenter">
                    <el-input v-model="recruitmentAddForm.researchCenter"
                              placeholder="研究中心"></el-input>
                </el-form-item>
                <el-form-item prop="patientRights">
                    <el-input v-model="recruitmentAddForm.patientRights"
                              placeholder="患者权益"></el-input>
                </el-form-item>
                <el-form-item prop="startTime">
                    <el-date-picker style="width: 100%" :editable="false"
                                    v-model="recruitmentAddForm.startTime"
                                    type="datetime"
                                    placeholder="启始时间"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    format="yyyy年MM月dd HH时mm分ss秒">
                    </el-date-picker>
                </el-form-item>
                <el-form-item prop="stopTime">
                    <el-date-picker style="width: 100%" :editable="false"
                                    v-model="recruitmentAddForm.stopTime"
                                    type="datetime"
                                    placeholder="截至时间"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    format="yyyy年MM月dd HH时mm分ss秒">
                    </el-date-picker>
                </el-form-item>
                <el-form-item class="submit-item">
                    <el-button
                            type="primary"
                            @click="recruitmentAddOnSubmit('recruitmentAddForm')">
                        提交
                    </el-button>
                    <el-button>取消</el-button>
                </el-form-item>
            </el-form>
        </el-main>
    </el-container>
</div>
</body>
<script src="/static/js/lib/vue.js"></script>
<script src="/static/js/lib/element.js"></script>
<script src="/static/js/lib/axios.min.js"></script>
<script src="/static/js/util/ajax.js"></script>
<script type="text/javascript">
  new Vue({
    el: '#recruitment_add',
    data: function () {
      return {
        recruitmentAddForm: {
          title: '',
          registerCode: '',
          practiceStages: '',
          indication: '',
          drugName: '',
          drugType: '',
          recruitmentNumber: '',
          introduction: '',
          treatmentPlan: '',
          screeningStandard: '',
          entryCriteria: '',
          researchCenter: '',
          patientRights: '',
          startTime: '',
          stopTime: ''
        },
        recruitmentAddRules: {
          title: [
            {required: true, message: '请输入标题', trigger: 'blur'}
          ],
          registerCode: [
            {required: true, message: '请输入登记编号', trigger: 'blur'}
          ],
          practiceStages: [
            {required: true, message: '请输入实验分期', trigger: 'blur'}
          ],
          indication: [
            {required: true, message: '请输入适应症状', trigger: 'blur'}
          ],
          drugName: [
            {required: true, message: '请输入药物名称', trigger: 'blur'}
          ],
          drugType: [
            {required: true, message: '请输入药物类型', trigger: 'blur'}
          ],
          recruitmentNumber: [
            {required: true, message: '请输入招募人数'},
            {type: 'number', message: '招募人数必须为数字值'}
          ],
          introduction: [
            {required: true, message: '请输入简介', trigger: 'blur'}
          ],
          treatmentPlan: [
            {required: true, message: '请输入治疗方案', trigger: 'blur'}
          ],
          screeningStandard: [
            {required: true, message: '请输入初筛要点', trigger: 'blur'}
          ],
          entryCriteria: [
            {required: true, message: '请输入入排标准', trigger: 'blur'}
          ],
          researchCenter: [
            {required: true, message: '请输入研究中心', trigger: 'blur'}
          ],
          patientRights: [
            {required: true, message: '请输入患者权益', trigger: 'blur'}
          ],
          startTime: [
            {required: true, message: '请输入启始时间', trigger: 'blur'}
          ],
          stopTime: [
            {required: true, message: '请输入截至时间', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      recruitmentAddOnSubmit: function (recruitmentAddFormName) {
        this.$refs[recruitmentAddFormName].validate((valid) => {
          if (valid) {
            this.ajax.post('/recruitment/add.json', this.recruitmentAddForm).then((success) => {
              console.log(success);
            });
          } else {
            console.log(this.recruitmentAddForm);
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  })
</script>
</html>
