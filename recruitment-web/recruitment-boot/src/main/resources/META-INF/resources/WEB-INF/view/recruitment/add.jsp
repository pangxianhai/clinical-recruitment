<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes"/>
    <title>添加招募信息-临床实验招募平台</title>
    <link rel="stylesheet" href="/static/css/element.css">
    <style>
        .recruitment-add-panel .add-item {
            height: 3em;
        }

        .recruitment-add-panel .add-item .title {
            text-align: right;
            width: 5em;
            display: block;
            float: left;
            margin-right: 1em;
        }

        .recruitment-add-panel .add-item input {
            height: 1.8em;
            width: 17em;
            border: 1px solid #ccc;
            padding-left: 0.2em;
        }
    </style>
</head>
<body>
<div id="recruitment_add" class="recruitment-add-panel">
    <div class="add-item">
        <span class="title">标题:</span><input type="text"/>
    </div>
    <div class="add-item">
        <span class="title">登记编号:</span><input type="text"/>
    </div>
    <div class="add-item">
        <span class="title">实验分期:</span><input type="text"/>
    </div>
    <div class="add-item">
        <span class="title">适应症状:</span><input type="text"/>
    </div>
    <div class="add-item">
        <span class="title">药物名称:</span><input type="text"/>
    </div>
    <div class="add-item">
        <span class="title">药物类型:</span><input type="text"/>
    </div>
    <div class="add-item">
        <span class="title">招募人数:</span><input type="text"/>
    </div>
    <div class="add-item">
        <span class="title">简介:</span><input type="text"/>
    </div>
    <div class="add-item">
        <span class="title">初筛要点:</span><input type="text"/>
    </div>
    <div class="add-item">
        <span class="title">入排标准:</span><input type="text"/>
    </div>
    <div class="add-item">
        <span class="title">研究中心:</span><input type="text"/>
    </div>
    <div class="add-item">
        <span class="title">患者权益:</span><input type="text"/>
    </div>
    <div class="add-item">
        <span class="title">截至时间:</span><input type="text"/>
    </div>
    <div class="add-item">
        <span class="title">截至时间:</span><input type="text"/>
    </div>
</div>
</body>
<script src="/static/js/vue.js"></script>
<script src="/static/js/element.js"></script>
<script>
  new Vue({
    el: '#recruitment_add'
  })
</script>
</html>
