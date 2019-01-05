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
            <el-form>
                <el-form-item>
                    <el-input placeholder="标题"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input placeholder="登记编号"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input placeholder="实验分期"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input placeholder="适应症状"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input placeholder="药物名称"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input placeholder="药物类型"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input placeholder="招募人数"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input placeholder="简介"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input placeholder="初筛要点"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input placeholder="入排标准"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input placeholder="研究中心"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input placeholder="患者权益"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-date-picker style="width: 100%" :editable="false"
                                    v-model="value1"
                                    type="date"
                                    placeholder="启始时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-date-picker style="width: 100%" :editable="false"
                                    v-model="value1"
                                    type="date"
                                    placeholder="截至时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item class="submit-item">
                    <el-button type="primary">提交</el-button>
                    <el-button>取消</el-button>
                </el-form-item>
            </el-form>
        </el-main>
    </el-container>
</div>
</body>
<script src="/static/js/vue.js"></script>
<script src="/static/js/element.js"></script>
<script>
  new Vue({
    el: '#recruitment_add',
    data: function () {
      return {
        "value1": ''
      }
    }
  })
</script>
</html>
