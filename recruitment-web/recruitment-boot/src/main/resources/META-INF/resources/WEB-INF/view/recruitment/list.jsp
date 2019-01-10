<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes"/>
    <title>任务列表</title>
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

        a {
            text-decoration: none;
            color: #333;
        }

        .el-container {
            background: rgba(223, 245, 226, 0.97);
        }

        .el-main {
            padding: 1em 1em 0 1em;
            background: #FFF;
        }

        .el-message {
            width: 98%;
            min-width: auto !important;
        }

        .search-input .el-input__inner{
            background: rgba(223, 245, 226, 0.97);
        }

        .search {
            padding-top: 1em;
            border-bottom: solid 1px #ccc;
        }

        .search .el-input {
            width: 7em;
        }

        .el-form-item {
            margin-bottom: 1em;
        }

        .recruitment-panel {
            padding: 0.5em 0 0.5em 0;
            border-bottom: solid 1px #ccc;
            line-height: 1.4em;
        }

        .recruitment-panel .title {
            font-weight: bold;
            margin-bottom: 1em;
            font-size: 14px;
        }

        .recruitment-panel .label {
            color: #888;
            font-size: 12px;
            display: inline-block;
            height: 1.2em;
            overflow: hidden;
        }

        .recruitment-panel .value {
            color: #333;
            padding-right: 1em;
            font-size: 13px;
            display: inline-block;
            height: 1.2em;
            overflow: hidden;
        }

        .recruitment-panel .label-right {
            float: right;
            padding-right: 0.5em;
        }

        .recruitment-panel .value-right {
            float: right;
            padding-right: 0 !important;
        }
    </style>
</head>
<body>
<div id="recruitment_list">
    <el-container>
        <el-main>
            <el-input class="search-input"
                      placeholder="输入招募项目、登记编号、适应症状进行搜索">
                <i slot="suffix" class="el-input__icon el-icon-search" @click="searchAction"></i>
            </el-input>
            <el-form class="search" :inline="true" :model="searchForm">
                <el-form-item>
                    <span>智能推荐</span>
                </el-form-item>
                <el-form-item>
                    <el-select v-model="searchForm.indication" @change="searchAction">
                        <el-option v-for="item in indicationOptions"
                                   :key="item.value"
                                   :label="item.label"
                                   :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-cascader
                            :options="regionOptions"
                            :show-all-levels="false"
                            v-model="searchForm.regionOptions"
                            @change="searchAction"
                    ></el-cascader>
                </el-form-item>
            </el-form>
            <div class="recruitment-panel" v-for="recruitment in recruitmentList">
                <a :href="'/recruitment/detail/'+recruitment.recruitmentId">
                    <div class="title">{{recruitment.title}}</div>
                    <div>
                        <span class="label">登记编号：</span>
                        <span class="value">{{recruitment.registerCode}}</span>
                        <span class="value value-right">{{recruitment.practiceStages}}</span>
                        <span class="label label-right">实验分期：</span>

                    </div>
                    <div>
                        <span class="label">药物名称：</span>
                        <span class="value">{{recruitment.drugName}}</span>
                    </div>
                    <div>
                        <span class="label">招募人数：</span>
                        <span class="value">{{recruitment.recruitmentNumber}}人</span>
                        <span class="value value-right">{{recruitment.status.desc}}</span>
                        <span class="label label-right">招募状态：</span>
                    </div>
                    <div>
                        <span class="label">适应症状：</span>
                        <span class="value">{{recruitment.indication}}</span>
                    </div>
                </a>
            </div>
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
    el: '#recruitment_list',
    data: function () {
      return {
        regionOptions: ${regionVOList},
        indicationOptions: ${indicationOptions},
        recruitmentList:${recruitmentInfoList},
        searchForm: {
          regionOptions: ['', '青羊区'],
          indication: ''
        }
      }
    },
    methods: {
      searchAction: function () {
        console.log(this.searchForm);
      }
    }
  })
</script>
</html>