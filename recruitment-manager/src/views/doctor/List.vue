<template>
    <div class="doctor-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>医生管理</el-breadcrumb-item>
            <el-breadcrumb-item>医生列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form ref="queryInfo" :model="queryInfo" :inline="true">
            <el-form-item label="姓名:" prop="realName">
                <el-input v-model="queryInfo.realName" clearable size="mini"></el-input>
            </el-form-item>
            <el-form-item label="手机号:" prop="phoneLike">
                <el-input v-model="queryInfo.phoneLike" clearable size="mini"></el-input>
            </el-form-item>
            <el-form-item label="地区:" prop="addressIds">
                <el-cascader
                    size="mini"
                    :options="areaData"
                    v-model="queryInfo.addressIds"
                    clearable
                    placeholder="地区">
                </el-cascader>
            </el-form-item>
            <el-form-item label="状态:" prop="status">
                <el-select v-model="queryInfo.status" size="mini" clearable placeholder="状态">
                    <el-option label="全部" value=""></el-option>
                    <el-option label="正常" value="1"></el-option>
                    <el-option label="冻结" value="2"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button size="mini" type="primary" @click="loadDoctorInfo()"
                           icon="el-icon-search">查询
                </el-button>
            </el-form-item>
        </el-form>
        <el-table
            :data="doctorList"
            border
            style="width: 100%">
            <el-table-column
                fixed
                prop="userInfoVO.realName"
                label="姓名">
            </el-table-column>
            <el-table-column
                prop="userInfoVO.phone"
                label="手机号">
            </el-table-column>
            <el-table-column
                prop="userInfoVO.nickname"
                label="微信昵称">
            </el-table-column>
            <el-table-column
                prop="userInfoVO.gender.desc"
                label="性别">
            </el-table-column>

            <el-table-column
                prop="address"
                label="地址">
            </el-table-column>
            <el-table-column
                prop="medicalInstitution"
                label="执业机构">
            </el-table-column>
            <el-table-column
                prop="medicalCategory"
                label="执业类别">
            </el-table-column>
            <el-table-column
                label="状态">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.userInfoVO.status.code===UserStatus.NORMAL">
                        {{scope.row.userInfoVO.status.desc}}
                    </el-tag>
                    <el-tag v-if="scope.row.userInfoVO.status.code===UserStatus.FREEZE" type="info">
                        {{scope.row.userInfoVO.status.desc}}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column
                width="100"
                fixed="right"
                label="操作">
                <template slot-scope="scope">
                    <el-row type="flex">
                        <el-col>
                            <el-button
                                v-if="scope.row.userInfoVO.status.code===UserStatus.NORMAL"
                                type="danger"
                                icon="el-icon-goods"
                                @click="freezeUser(scope.row.userInfoVO)"
                                size="mini">
                                冻结
                            </el-button>
                        </el-col>
                    </el-row>
                    <el-row type="flex">
                        <el-col>
                            <el-button
                                v-if="scope.row.userInfoVO.status.code===UserStatus.FREEZE"
                                type="primary"
                                icon="el-icon-sold-out"
                                @click="unfreezeUser(scope.row.userInfoVO)"
                                size="mini">
                                解冻
                            </el-button>
                        </el-col>
                    </el-row>
                    <el-row type="flex">
                        <el-col>
                            <el-button
                                icon="el-icon-edit"
                                type="success"
                                @click="onUpdateAction(scope.row)"
                                size="mini">
                                编辑
                            </el-button>
                        </el-col>
                    </el-row>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @current-change="loadDoctorInfo"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="totalRecord">
        </el-pagination>
    </div>
</template>
<style>
    .doctor-list .el-form {
        margin-top: 10px;
        padding: 10px 8px 10px 8px;
        background: #dddef5;
        border-radius: 4px;
    }

    .doctor-list .el-table {
        margin-top: 20px;
    }

    .doctor-list .el-table .cell {
        text-align: center;
    }

    .doctor-list .el-pagination {
        float: right;
    }

    .doctor-list .el-row {
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
    Button,
    Tag,
    Message,
    Row,
    Col,
    Form,
    FormItem,
    Input,
    InputNumber,
    Select,
    Option,
    Cascader,
  } from 'element-ui';
  import DoctorApi from '@/api/DoctorApi';
  import {UserStatus} from '@/constants/Global';
  import AdminApi from '@/api/AdminApi';
  import AreaData from '@/util/AreaData';

  export default {
    components: {
      [Breadcrumb.name]: Breadcrumb,
      [BreadcrumbItem.name]: BreadcrumbItem,
      [Table.name]: Table,
      [TableColumn.name]: TableColumn,
      [Pagination.name]: Pagination,
      [Button.name]: Button,
      [Tag.name]: Tag,
      [Row.name]: Row,
      [Col.name]: Col,
      [Form.name]: Form,
      [FormItem.name]: FormItem,
      [Input.name]: Input,
      [InputNumber.name]: InputNumber,
      [Select.name]: Select,
      [Option.name]: Option,
      [Cascader.name]: Cascader,

    },
    data: function () {
      return {
        UserStatus: UserStatus,
        doctorList: [],
        currentPage: 1,
        totalRecord: 0,
        pageSize: 10,
        areaData: AreaData,
        queryInfo: {
          addressIds: [],
        }
      }
    },
    created: function () {
      Object.assign(this.queryInfo, this.$route.query);
      let addressIds = this.queryInfo.addressIds;
      if (typeof addressIds !== 'undefined' && addressIds.length > 0) {
        let addIds = [];
        for (let i in addressIds) {
          addIds.push(parseInt(addressIds[i]));
        }
        this.queryInfo.addressIds = addIds;
      }
      this.loadDoctorInfo();
    },
    methods: {
      loadDoctorInfo: function () {
        this.$router.replace({
          query: Object.assign(this.queryInfo, {
            currentPage: this.currentPage,
            pageSize: this.pageSize
          })
        });
        DoctorApi.getDoctor(Object.assign({
          provinceId: this.queryInfo.addressIds[0],
          cityId: this.queryInfo.addressIds[1],
          districtId: this.queryInfo.addressIds[2],
        }, this.queryInfo)).then(data => {
          this.doctorList = data.data;
          this.totalRecord = data.paginator.totalRecord;
        });
      },
      freezeUser: function (userInfo) {
        UserApi.freezeUser(userInfo.userId).then(success => {
          if (success) {
            Message.success('操作成功!');
            this.loadDoctorInfo();
          }
        });
      },
      unfreezeUser: function (userInfo) {
        UserApi.unfreezeUser(userInfo.userId).then(success => {
          if (success) {
            Message.success('操作成功!');
            this.loadDoctorInfo();
          }
        });
      },
      onUpdateAction: function (doctorInfo) {
        this.$router.push({
          path: `/doctor/update/${doctorInfo.doctorId}`
        });
      }
    }
  }
</script>
