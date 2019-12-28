<template>
    <div class="manager-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>管理员列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form ref="queryInfo" :model="queryInfo" :inline="true">
            <el-form-item label="姓名:" prop="realName">
                <el-input v-model="queryInfo.realName" size="mini" clearable></el-input>
            </el-form-item>
            <el-form-item label="手机号:" prop="phoneLike">
                <el-input v-model="queryInfo.phoneLike" size="mini" clearable></el-input>
            </el-form-item>
            <el-form-item label="状态:" prop="status">
                <el-select v-model="queryInfo.status" size="mini" clearable placeholder="状态">
                    <el-option label="正常" value="1"></el-option>
                    <el-option label="冻结" value="2"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button size="mini" type="primary" @click="loadManagerInfo()"
                           icon="el-icon-search">查询
                </el-button>
            </el-form-item>
        </el-form>
        <el-table
            :data="managerList"
            border
            style="width: 100%">
            <el-table-column
                prop="realName"
                label="姓名">
            </el-table-column>
            <el-table-column
                prop="phone"
                label="手机号">
            </el-table-column>
            <el-table-column
                prop="gender.desc"
                label="性别">
            </el-table-column>
            <el-table-column
                label="状态">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.status.code===UserStatus.NORMAL">
                        {{scope.row.status.desc}}
                    </el-tag>
                    <el-tag v-if="scope.row.status.code===UserStatus.FREEZE" type="info">
                        {{scope.row.status.desc}}
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
                                v-if="scope.row.status.code===UserStatus.NORMAL"
                                type="danger"
                                icon="el-icon-goods"
                                @click="freezeUser(scope.row)"
                                size="mini">
                                冻结
                            </el-button>
                        </el-col>
                    </el-row>
                    <el-row type="flex">
                        <el-col>
                            <el-button
                                v-if="scope.row.status.code===UserStatus.FREEZE"
                                type="primary"
                                icon="el-icon-sold-out"
                                @click="unfreezeUser(scope.row)"
                                size="mini">
                                解冻
                            </el-button>
                        </el-col>
                    </el-row>
                    <el-row type="flex">
                        <el-col>
                            <el-button
                                type="success"
                                icon="el-icon-edit"
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
            @current-change="loadManagerInfo"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="totalRecord">
        </el-pagination>
    </div>
</template>

<style>
    .manager-list .el-form {
        margin-top: 10px;
        padding: 10px 8px 10px 8px;
        background: #dddef5;
        border-radius: 4px;
    }

    .manager-list .el-table {
        margin-top: 20px;
        text-align: center;
    }

    .manager-list .el-table .cell {
        text-align: center;
    }

    .manager-list .el-pagination {
        float: right;
    }

    .manager-list .el-row {
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
  } from 'element-ui';
  import {UserStatus} from '@/constants/Global';
  import UserApi from '@/api/UserApi';

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
    },
    data: function () {
      return {
        UserStatus: UserStatus,
        managerList: [],
        currentPage: 1,
        totalRecord: 0,
        pageSize: 10,
        queryInfo: {}
      }
    },
    created: function () {
      Object.assign(this.queryInfo, this.$route.query);
      this.loadManagerInfo();
    },
    methods: {
      loadManagerInfo: function () {
        this.$router.replace({
          query: Object.assign(this.queryInfo, {
            currentPage: this.currentPage,
            pageSize: this.pageSize
          })
        });
        UserApi.getManager(Object.assign(this.queryInfo, {
          currentPage: this.currentPage,
          pageSize: this.pageSize
        })).then(data => {
          this.managerList = data.data;
          this.totalRecord = data.paginator.totalRecord;
        });
      },
      freezeUser: function (userInfo) {
        UserApi.freezeUser(userInfo.userId).then(success => {
          if (success) {
            Message.success('操作成功!');
            this.loadManagerInfo();
          }
        });
      },
      unfreezeUser: function (userInfo) {
        UserApi.unfreezeUser(userInfo.userId).then(success => {
          if (success) {
            Message.success('操作成功!');
            this.loadManagerInfo();
          }
        });
      },
      onUpdateAction: function (userInfo) {
        this.$router.push({
          path: `/manager/update/${userInfo.userId}`
        });
      }
    }
  }
</script>
