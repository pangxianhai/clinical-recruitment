<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes"/>
    <title>任务详情</title>
    <link rel="stylesheet" href="/static/css/element.css">
    <style>
        body {
            margin: 0;
            background-color: #FFF;
        }

        ul, li {
            padding: 0;
            margin: 0;
            list-style: none
        }

        .el-container {
            background: rgba(223, 245, 226, 0.97);
        }

        .el-header {
            padding: 0;
            background: #FFF;
            color: #000;
            height: 4em !important;
            line-height: 4em;
            font-size: 15px;
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1;
            font-weight: bold;
        }

        .el-header .title {
            width: 100%;
            text-align: center;
            float: left;
        }

        .el-main {
            padding: 5em 0.2em 0 1em;
            background: #FFF;
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

        .recruitment_title {
            font-weight: bold;
            font-size: 20px;
        }

        .recruitment_ul {
            color: #888;
            font-size: 14px;
            margin-top: 0.3em;
            line-height: 2em;
            background-color: #FFF;
        }

        .recruitment_introduction {
            border-top: solid 1px #bbb;
            padding: 0.5em 0 0.5em 0;
            margin-top: 0.3em;
        }

        .recruitment_tabs_main {
            margin-top: 0.3em;
            padding: 0;
        }

        .el-tabs__item {
            font-weight: normal;
            font-size: 12px;
            padding: 0 0.5em;
        }

        .el-tabs--border-card {
            border: 0;
        }
    </style>
</head>
<body>
<div id="recruitment_detail">
    <el-container>
        <el-header>
            <i class="el-icon-arrow-left header-left"></i>
            <span class="title">任务详情</span>
        </el-header>
        <el-main>
            <div class="recruitment_title">${recruitmentInfo.title}</div>
            <ul class="recruitment_ul">
                <li>登记编号：${recruitmentInfo.registerCode}</li>
                <li>实验分期：${recruitmentInfo.practiceStages}</li>
                <li>适应症状：${recruitmentInfo.indication}</li>
                <li>药物名称：${recruitmentInfo.drugName}</li>
                <li>药物类型：${recruitmentInfo.drugType}</li>
                <li>招募人数：${recruitmentInfo.recruitmentNumber}人</li>
            </ul>
            <div class="recruitment_introduction">
                ${recruitmentInfo.introduction}
            </div>
        </el-main>
        <el-main class="recruitment_tabs_main">
            <el-tabs class="recruitment_tabs" type="border-card" v-model="recruitmentInfoTabs">
                <el-tab-pane label="治疗方案" name="treatmentPlan">
                    ${recruitmentInfo.treatmentPlan}
                </el-tab-pane>
                <el-tab-pane label="初筛要点" name="screeningStandard">
                    ${recruitmentInfo.screeningStandard}
                </el-tab-pane>
                <el-tab-pane label="入排标准" name="entryCriteria">
                    ${recruitmentInfo.entryCriteria}
                </el-tab-pane>
                <el-tab-pane label="研究中心" name="researchCenter">
                    ${recruitmentInfo.researchCenter}
                </el-tab-pane>
                <el-tab-pane label="患者权益" name="patientRights">
                    ${recruitmentInfo.patientRights}
                </el-tab-pane>
            </el-tabs>
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
    el: '#recruitment_detail',
    data: function () {
      return {
        recruitmentInfoTabs: 'treatmentPlan'
      }
    },
    methods: {}
  })
</script>
</html>
