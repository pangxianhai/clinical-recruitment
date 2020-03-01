<template>
    <div class="reference-register">
        <van-nav-bar title="推荐人注册" left-arrow @click-left="onGoBack"></van-nav-bar>
        <van-cell-group>
            <van-field
                v-model="researcherInfo.name"
                required
                clearable
                label="姓名"
                placeholder="请输入姓名"
                :error-message="errorMsg.name"
                @blur="validator('name')"
                :disabled="nameDisabled"
            ></van-field>
            <van-field
                v-model="researcherInfo.phone"
                required
                clearable
                label="手机号"
                placeholder="请输入手机号"
                :error-message="errorMsg.phone"
                @blur="validator('phone')"
                :disabled="phoneDisabled"
            ></van-field>
            <van-field
                v-model="researcherInfo.genderShow"
                required
                clearable
                label="性别"
                placeholder="请选择性别"
                :error-message="errorMsg.genderShow"
                @blur="validator('genderShow')"
                @focus="showGenderPopup = true"
                :disabled="genderDisabled"
            ></van-field>
            <van-popup v-model="showGenderPopup" position="bottom">
                <van-picker :columns="genderList" show-toolbar title="选择性别"
                            @change="onChangeGender" @confirm="onConfirmGender"
                            cancel-button-text=""
                            :visible-item-count=3>
                </van-picker>
            </van-popup>
            <van-field
                v-model="researcherInfo.departmentShow"
                required
                clearable
                label="科室"
                placeholder="请选择机构及科室"
                :error-message="errorMsg.departmentShow"
                @blur="validator('departmentShow')"
                @focus="onOpenDepartmentPopup"
            ></van-field>
            <van-popup v-model="showDepartmentPopup" position="bottom">
                <van-picker show-toolbar title="机构及科室列表"
                            :columns="organizationAndDepartmentColumns"
                            @confirm="onConfirmDepartment"
                            @cancel="onCancelDepartment"
                            @change="onChangeDepartment"
                            :loading="organizationAndDepartmentLoading"
                />
            </van-popup>
            <van-field
                v-model="researcherInfo.medicalInstitution"
                required
                label="执业机构"
                placeholder="请输入执业机构"
                :error-message="errorMsg.medicalInstitution"
                @blur="validator('medicalInstitution')">
            </van-field>
            <van-field
                v-model="researcherInfo.medicalCategory"
                required
                label="执业类别"
                placeholder="请输入执业类别"
                :error-message="errorMsg.medicalCategory"
                @blur="validator('medicalCategory')">
            </van-field>
        </van-cell-group>
        <van-row class="submit-panel">
            <van-col span="11">
                <van-button size="small" block plain hairline type="primary"
                            @click="onCancelAction">取消
                </van-button>
            </van-col>
            <van-col span="11" offset="2">
                <van-button size="small" block type="primary" @click="onRegisterAction">注册
                </van-button>
            </van-col>
        </van-row>
    </div>
</template>

<style>
    .reference-register .submit-panel {
        margin-top: 20px;
    }
</style>

<script>
  import AsyncValidator from 'async-validator';
  import UserApi from '@/api/UserApi';
  import OrganizationApi from '@/api/OrganizationApi';
  import ResearcherApi from '@/api/ResearcherApi'

  export default {
    data: function () {
      return {
        showAddress: false,
        nameDisabled: false,
        phoneDisabled: false,
        genderDisabled: false,
        researcherInfo: {
          name: '',
          phone: '',
          gender: '',
          genderShow: ''
        },
        errorMsg: {
          name: '',
          phone: ''
        },
        rules: {
          name: [{
            required: true,
            message: '请填写姓名'
          }, {
            max: 16,
            message: '姓名输入太长'
          }],
          phone: [{
            required: true,
            message: '请填写手机号'
          }, {
            pattern: /\d{11}/,
            message: '手机号码格式错误'
          }],
          genderShow: {
            required: true,
            message: '请选择性别'
          },
          departmentShow: {
            required: true,
            message: '请选择科室'
          },
          medicalInstitution: [{
            required: true,
            message: '请填写执业机构'
          }, {
            max: 16,
            message: '执业机构输入太长'
          }],
          medicalCategory: [{
            required: true,
            message: '请填写执业类别'
          }, {
            max: 16,
            message: '执业类别输入太长'
          }]
        },
        showGenderPopup: false,
        showDepartmentPopup: false,
        organizationAndDepartmentLoading: false,
        genderList: [{text: '男', value: 1}, {text: '女', value: 2}],
        organizationAndDepartmentColumns: [],
        departmentCache: {}
      };
    },
    created: function () {
      this.researcherInfo.openId = this.$route.query.openId;
      this.researcherInfo.nickname = this.$route.query.nickname;
      this.loadCurrentUserInfo();
    },
    methods: {
      loadCurrentUserInfo: function () {
        UserApi.getCurrentUserInfo().then(userInfo => {
          if (!userInfo.userId) {
            return;
          }
          this.researcherInfo.name = userInfo.realName;
          this.nameDisabled = true;
          this.researcherInfo.phone = userInfo.phone;
          this.phoneDisabled = true;
          this.researcherInfo.gender = userInfo.gender.code;
          this.researcherInfo.genderShow = userInfo.gender.desc;
          this.genderDisabled = true;
        });
      },
      onGoBack: function () {
        let redirectURL = this.$route.query.redirectURL;
        if (typeof redirectURL === 'undefined' || redirectURL.length <= 0) {
          redirectURL = '/recruitment/list';
        }
        this.$router.push({path: String(redirectURL)}, function () {

        });
      },
      validatorRegisterInfo: function (item) {
        const validator = new AsyncValidator(this.rules);
        return validator.validate(this.researcherInfo, (errors, fields) => {
          if (errors) {
            this.handlerError(item, errors, fields)
          } else {
            this.errorMsg = {};
          }
        });
      },
      validator: function (item) {
        this.validatorRegisterInfo(item);
      },
      handlerError: function (item, errors, fields) {
        if (typeof item !== 'undefined') {
          if (typeof fields[item] !== 'undefined') {
            this.errorMsg[item] = fields[item][0].message;
          } else {
            this.errorMsg[item] = '';
          }
        } else {
          let errorMsg = {};
          errors.forEach(e => {
            errorMsg[e.field] = e.message;
          });
          this.errorMsg = errorMsg;
        }
      },
      onChangeGender: function (picker, value) {
        this.researcherInfo.gender = value.value;
        this.researcherInfo.genderShow = value.text;
        this.showGenderPopup = false;
      },
      onConfirmGender: function (value) {
        this.researcherInfo.gender = value.value;
        this.researcherInfo.genderShow = value.text;
        this.showGenderPopup = false;
      },
      onOpenDepartmentPopup: function () {
        this.showDepartmentPopup = true;
        this.organizationAndDepartmentLoading = true;
        if (this.organizationAndDepartmentColumns.length > 1) {
          this.organizationAndDepartmentLoading = false;
          return;
        }
        OrganizationApi.getOrganizationList().then(organizationInfo => {
          let organizationList = organizationInfo.data;
          let organizationColumns = [];
          for (let i = 0; i < organizationList.length; ++i) {
            let organization = organizationList[i];
            organizationColumns.push({
              text: organization.name,
              value: organization.organizationId
            });
          }
          this.organizationAndDepartmentColumns.push({
            values: organizationColumns
          });
          this.onLoadDepartment(organizationList[0].organizationId);
          this.organizationAndDepartmentLoading = false;
        });
      },
      onLoadDepartment: function (organizationId) {
        OrganizationApi.getDepartmentByOrganization(organizationId).then(departmentList => {
          let departmentColumns = [];
          for (let i = 0; i < departmentList.length; ++i) {
            let departmentInfo = departmentList[i];
            departmentColumns.push({
              text: departmentInfo.name,
              value: departmentInfo.departmentId
            });
          }
          this.organizationAndDepartmentColumns.push({
            values: departmentColumns
          });
          this.departmentCache[organizationId] = departmentColumns;
        });
      },
      onChangeDepartment: function (picker, values) {
        let departmentColumns = this.departmentCache[values[0].value];
        if (typeof departmentColumns !== 'undefined') {
          picker.setColumnValues(1, departmentColumns);
          return;
        }
        OrganizationApi.getDepartmentByOrganization(values[0].value).then(departmentList => {
          let departmentColumns = [];
          for (let i = 0; i < departmentList.length; ++i) {
            let departmentInfo = departmentList[i];
            departmentColumns.push({
              text: departmentInfo.name,
              value: departmentInfo.departmentId
            });
          }
          this.departmentCache[values[0].value] = departmentColumns;
          picker.setColumnValues(1, departmentColumns);
        });
      },
      onConfirmDepartment: function (value) {
        this.researcherInfo.departmentShow = value[0].text + '/' + value[1].text;
        this.researcherInfo.organizationId = value[0].value;
        this.researcherInfo.departmentId = value[1].value;
        this.showDepartmentPopup = false;
        window.console.log(this.researcherInfo);
      },
      onCancelDepartment: function () {
        this.showDepartmentPopup = false;
      },
      onCancelAction: function () {
        this.onGoBack();
      },

      onRegisterAction: function () {
        this.validatorRegisterInfo().then(() => {
          ResearcherApi.registerResearcher(this.researcherInfo).then(loginInfo => {
            if (loginInfo) {
              if (!UserApi.isLogin()) {
                UserApi.saveLoginInfo(loginInfo);
              }
              this.$toast.success('注册成功！即将跳转');
              setTimeout(() => {
                this.onGoBack();
              }, 2000);
            }
          });
        });
      }
    }
  }
</script>