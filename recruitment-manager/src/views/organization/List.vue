<template>
    <div class="organization-list">
        <el-table
            :data="organizationList"
            border
            style="width: 100%">
            <el-table-column
                fixed
                prop="name"
                label="姓名"></el-table-column>
        </el-table>
    </div>
</template>

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
        areaData: AreaData,
        organizationList: [],
        currentPage: 1,
        totalRecord: 0,
        pageSize: 10,
        queryInfo: {}
      }
    },
  }

</script>
