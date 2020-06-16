<template>
    <div>
        <el-cascader style="width:100%"
                     v-model="currentValue"
                     :options="departmentInfoList"
                     :props="departmentProps"
                     :placeholder="placeholder"
                     @change="handleChange"
                     clearable filterable>
        </el-cascader>
    </div>
</template>

<script>
  import HospitalApi from '@/api/HospitalApi';
  import DepartmentApi from '@/api/DepartmentApi';
  import {CollectionUtil} from "../util/Util";

  export default {
    props: {
      value: Array,
      placeholder: String
    },
    data: function () {
      return {
        currentValue: this.value,
        currentPlaceholder: this.placeholder,
        departmentInfoList: [],
        departmentProps: {
          lazy: true,
          lazyLoad: this.loadDepartmentLazy,
          checkStrictly: true,
          multiple: true,
        }
      }
    },
    created() {
      this.initDepartmentInfoList();
    },
    methods: {
      async initDepartmentInfoList() {
        this.departmentInfoList = await this.getHospitalList();
      },
      async loadDepartmentLazy(node, resolve) {
        const {data} = node;
        if (typeof data === 'undefined') {
          return;
        }
        if (data.leaf) {
          resolve();
          return;
        }
        if (CollectionUtil.isNotEmpty(data.children)) {
          resolve();
          return;
        }
        const hospitalId = data.value;
        let departmentInfoList = await this.getDepartmentList(hospitalId);
        resolve(departmentInfoList);
      },
      handleChange(value) {
        this.$emit('input', value)
      },
      async getDepartmentList(hospitalId) {
        let departmentInfoList = [];
        await DepartmentApi.getDepartmentByHid(hospitalId).then(data => {
          data.forEach(departmentInfo => {
            departmentInfoList.push({
              label: departmentInfo.name,
              value: departmentInfo.departmentId,
              leaf: true
            })
          });
        });
        return departmentInfoList;
      },
      async getHospitalList() {
        let departmentInfoList = [];
        await HospitalApi.getHospital({}).then(data => {
          data.data.forEach(hospitalInfo => {
            departmentInfoList.push({
              label: hospitalInfo.name,
              value: hospitalInfo.hospitalId,
              disabled: true
            })
          });
        });
        return departmentInfoList;
      },
      getHospitalFromDepartmentInfoList(departmentInfoList, hospitalId) {
        for (let i = 0; i < departmentInfoList.length; ++i) {
          let item = departmentInfoList[i];
          if (item.value === hospitalId) {
            return item;
          }
        }
      }
    },
    watch: {
      async value(newValue) {
        let departmentInfoList = await this.getHospitalList();
        for (let i = 0; i < newValue.length; ++i) {
          let newItem = newValue[i];
          if (newItem.length < 2) {
            continue;
          }
          let hospitalItem = this.getHospitalFromDepartmentInfoList(departmentInfoList, newItem[0]);
          if (typeof hospitalItem === 'undefined') {
            continue;
          }
          if (CollectionUtil.isNotEmpty(hospitalItem.children)) {
            continue;
          }
          hospitalItem.children = await this.getDepartmentList(newItem[0]);
        }
        this.departmentInfoList = departmentInfoList;
        this.currentValue = newValue;
      }
    }
  }
</script>