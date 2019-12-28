<template>
    <div class="recruitment-application-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/recruitment/list' }">项目管理</el-breadcrumb-item>
            <el-breadcrumb-item>申请记录</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form ref="queryInfo" :model="queryInfo" :inline="true">
            <el-form-item label="项目:" prop="recruitmentId">
                <el-select
                    v-model="queryInfo.recruitmentId"
                    filterable
                    remote
                    reserve-keyword
                    placeholder="请输入项目名称"
                    :remote-method="loadRecruitmentInfo"
                    size="mini"
                    :loading="recruitmentLoading">
                    <el-option
                        v-for="item in recruitmentList"
                        :key="item.recruitmentId"
                        :label="item.title"
                        :value="item.recruitmentId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="患者:" prop="patientId">
                <el-select
                    v-model="queryInfo.patientId"
                    filterable
                    remote
                    reserve-keyword
                    placeholder="请输入患者姓名"
                    :remote-method="loadPatientInfo"
                    size="mini"
                    :loading="patientLoading">
                    <el-option
                        v-for="item in patientList"
                        :key="item.patientId"
                        :label="item.userInfoVO.realName"
                        :value="item.patientId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="推荐医生:" prop="doctorId">
                <el-select
                    v-model="queryInfo.doctorId"
                    filterable
                    remote
                    reserve-keyword
                    placeholder="请输入推荐医生姓名"
                    :remote-method="loadDoctorInfo"
                    size="mini"
                    :loading="doctorLoading">
                    <el-option
                        v-for="item in doctorList"
                        :key="item.doctorId"
                        :label="item.userInfoVO.realName"
                        :value="item.doctorId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="状态:" prop="status">
                <el-select v-model="queryInfo.status" size="mini" clearable placeholder="状态">
                    <el-option label="全部" value=""></el-option>
                    <el-option label="未入组" value="1"></el-option>
                    <el-option label="已入组" value="2"></el-option>
                    <el-option label="已拒接" value="3"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button size="mini" type="primary" @click="loadApplicationInfo()"
                           icon="el-icon-search">查询
                </el-button>
            </el-form-item>
        </el-form>
        <el-table
            :data="recruitmentApplicationList"
            border
            style="width: 100%">
            <el-table-column
                fixed
                prop="recruitmentVO.title"
                label="项目标题">
            </el-table-column>
            <el-table-column
                prop="patientVO.userInfoVO.realName"
                label="患者姓名">
            </el-table-column>
            <el-table-column
                prop="diseaseDesc"
                label="病症描述">
            </el-table-column>
            <el-table-column
                prop="doctorInfoVO.userInfoVO.realName"
                label="推荐医生">
            </el-table-column>
            <el-table-column
                prop="status.desc"
                label="状态">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.status.code===ApplicationStatus.NOT_ACCEDE">
                        {{scope.row.status.desc}}
                    </el-tag>
                    <el-tag v-if="scope.row.status.code===ApplicationStatus.ACCEDED" type="success">
                        {{scope.row.status.desc}}
                    </el-tag>
                    <el-tag v-if="scope.row.status.code===ApplicationStatus.REFUSED" type="info">
                        {{scope.row.status.desc}}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column
                prop="applicationTime"
                label="申请时间">
            </el-table-column>
            <el-table-column
                width="60"
                fixed="right"
                label="操作">
                <template slot-scope="scope">
                    <div class="operator-panel">
                        <el-row type="flex">
                            <el-tooltip effect="dark" content="详情" :hide-after="500"
                                        placement="bottom">
                                <el-col>
                                    <el-button
                                        icon="el-icon-more"
                                        type="primary"
                                        @click="onRecruitmentApplicationDetail(scope.row)"
                                        size="mini" circle>
                                    </el-button>
                                </el-col>
                            </el-tooltip>
                        </el-row>
                        <el-row type="flex">
                            <el-tooltip effect="dark"
                                        content="入组"
                                        :hide-after="500"
                                        placement="bottom"
                                        v-if="scope.row.status.code===ApplicationStatus.NOT_ACCEDE">
                                <el-col>
                                    <el-button
                                        icon="el-icon-zoom-in"
                                        type="success"
                                        @click="updateApplicationStatus(scope.row.applicationId, ApplicationStatus.ACCEDED)"
                                        size="mini" circle>
                                    </el-button>
                                </el-col>
                            </el-tooltip>
                        </el-row>
                        <el-row type="flex">
                            <el-tooltip effect="dark"
                                        content="取消入组"
                                        :hide-after="500"
                                        placement="bottom"
                                        v-if="scope.row.status.code===ApplicationStatus.ACCEDED">
                                <el-col>
                                    <el-button
                                        icon="el-icon-zoom-out"
                                        type="danger"
                                        @click="updateApplicationStatus(scope.row.applicationId, ApplicationStatus.REFUSED)"
                                        size="mini" circle>
                                    </el-button>
                                </el-col>
                            </el-tooltip>
                        </el-row>
                        <el-row type="flex">
                            <el-tooltip effect="dark"
                                        content="重新入组"
                                        :hide-after="500"
                                        placement="bottom"
                                        v-if="scope.row.status.code===ApplicationStatus.REFUSED">
                                <el-col>
                                    <el-button
                                        icon="el-icon-circle-plus"
                                        type="info"
                                        @click="updateApplicationStatus(scope.row.applicationId, ApplicationStatus.ACCEDED)"
                                        size="mini" circle>
                                    </el-button>
                                </el-col>
                            </el-tooltip>
                        </el-row>
                    </div>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @current-change="loadApplicationInfo"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="totalRecord">
        </el-pagination>
    </div>
</template>
<style>
    .recruitment-application-list .el-form {
        margin-top: 10px;
        padding: 10px 8px 1px 8px;
        background: #dddef5;
        border-radius: 4px;
    }

    .recruitment-application-list .el-table {
        margin-top: 20px;
    }

    .recruitment-application-list .el-table .cell {
        text-align: center;
    }

    .recruitment-application-list .el-pagination {
        float: right;
    }

    .recruitment-application-list .operator-panel {
        text-align: center;
    }

    .recruitment-application-list .operator-panel .el-row {
        margin-top: 5px;
    }
</style>
<script>
  import {
    Breadcrumb,
    BreadcrumbItem,
    Table,
    TableColumn,
    Pagination,
    Row,
    Col,
    Tooltip,
    Tag,
    Button,
    Message,
    Form,
    FormItem,
    Select,
    Option,
  } from 'element-ui';
  import RecruitmentApplicationApi from '@/api/RecruitmentApplicationApi';
  import {ApplicationStatus} from '@/constants/Global';
  import RecruitmentApi from '@/api/RecruitmentApi';
  import PatientApi from '@/api/PatientApi';
  import DoctorApi from '@/api/DoctorApi';

  export default {
    components: {
      [Breadcrumb.name]: Breadcrumb,
      [BreadcrumbItem.name]: BreadcrumbItem,
      [Table.name]: Table,
      [TableColumn.name]: TableColumn,
      [Pagination.name]: Pagination,
      [Row.name]: Row,
      [Col.name]: Col,
      [Tooltip.name]: Tooltip,
      [Tag.name]: Tag,
      [Button.name]: Button,
      [Form.name]: Form,
      [FormItem.name]: FormItem,
      [Select.name]: Select,
      [Option.name]: Option,
    },
    data: function () {
      return {
        ApplicationStatus: ApplicationStatus,
        recruitmentApplicationList: [],
        currentPage: 1,
        totalRecord: 0,
        pageSize: 10,
        queryInfo: {},
        recruitmentList: [],
        recruitmentLoading: false,
        patientList: [],
        patientLoading: false,
        doctorList: [],
        doctorLoading: false,
      }
    },
    created: function () {
      Object.assign(this.queryInfo, this.$route.query);
      this.loadApplicationInfo();
    },
    methods: {
      loadApplicationInfo: function () {
        this.$router.replace({
          query: Object.assign(this.queryInfo, {
            currentPage: this.currentPage,
            pageSize: this.pageSize
          })
        });
        RecruitmentApplicationApi.getRecruitmentApplication(this.queryInfo).then(data => {
          this.recruitmentApplicationList = data.data;
          this.totalRecord = data.paginator.totalRecord;
        });
      },
      onRecruitmentApplicationDetail: function (recruitmentApplication) {
        this.$router.push({
          path: `/recruitment/applicationDetail/${recruitmentApplication.applicationId}`
        });
      },
      updateApplicationStatus: function (applicationId, status) {
        RecruitmentApplicationApi.updateApplicationStatus(applicationId, status).then(success => {
          if (success) {
            Message.success('操作成功!');
            this.loadApplicationInfo();
          }
        });
      },
      loadRecruitmentInfo: function (query) {
        this.recruitmentLoading = true;
        RecruitmentApi.getRecruitment({
          queryText: query,
          currentPage: 1,
          pageSize: 15
        }).then(data => {
          this.recruitmentList = data.data;
          this.recruitmentLoading = false;
        });
      },
      loadPatientInfo: function (query) {
        this.patientLoading = true;
        PatientApi.getPatient({
          realName: query,
          currentPage: 1,
          pageSize: 15
        }).then(data => {
          this.patientList = data.data;
          this.patientLoading = false;
        });
      },
      loadDoctorInfo: function (query) {
        this.doctorLoading = true;
        DoctorApi.getDoctor({
          realName: query,
          currentPage: 1,
          pageSize: 15
        }).then(data => {
          this.doctorList = data.data;
          this.doctorLoading = false;
        });
      }
    }
  }
</script>
