<template>
    <div class="manager-list">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>管理员列表</el-breadcrumb-item>
        </el-breadcrumb>
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
                width="80"
                fixed="right"
                label="操作">
                <template slot-scope="scope">
                    <el-button
                        v-if="scope.row.status.code===UserStatus.NORMAL"
                        type="danger"
                        @click="freezeUser(scope.row)"
                        size="mini">
                        冻结
                    </el-button>
                    <el-button
                        v-if="scope.row.status.code===UserStatus.FREEZE"
                        type="primary"
                        @click="unfreezeUser(scope.row)"
                        size="mini">
                        解冻
                    </el-button>
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
    .manager-list .el-table {
        margin-top: 20px;
        text-align: center;
    }

    .manager-list .el-table th.is-leaf {
        color: #858585;

    }

    .manager-list .el-table .cell {
        text-align: center;
    }

    .manager-list .el-pagination {
        float: right;
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
    Message
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
    },
    data: function () {
      return {
        UserStatus: UserStatus,
        managerList: [],
        currentPage: 1,
        totalRecord: 0,
        pageSize: 10,
      }
    },
    created: function () {
      this.loadManagerInfo();
    },
    methods: {
      loadManagerInfo: function () {
        UserApi.getManager({
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }).then(data => {
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
      }
    }
  }
</script>
