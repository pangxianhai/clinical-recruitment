<template>
    <div class="reference-register">
        <van-nav-bar title="推荐人注册" left-arrow @click-left="onGoBack"></van-nav-bar>
        <van-cell-group>
            <van-field
                v-model="referenceInfo.name"
                required
                clearable
                label="姓名"
                placeholder="请输入姓名"
                :error-message="errorMsg.name"
                @blur="validator('name')"
                :disabled="nameDisabled"
            ></van-field>
            <van-field
                v-model="referenceInfo.phone"
                required
                clearable
                label="手机号"
                placeholder="请输入手机号"
                :error-message="errorMsg.phone"
                @blur="validator('phone')"
                :disabled="phoneDisabled"
            ></van-field>
            <van-field
                v-model="referenceInfo.genderShow"
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
                v-model="referenceInfo.address"
                required
                label="地址"
                placeholder="请输入地址"
                :error-message="errorMsg.address"
                @blur="validator('address')"
                @focus="showAddress = true">
            </van-field>
            <address-select :show="showAddress" @confirm="addressSelectConfirm"
                            @cancel="addressSelectCancel"></address-select>
            <van-field
                v-model="referenceInfo.medicalInstitution"
                required
                label="执业机构"
                placeholder="请输入执业机构"
                :error-message="errorMsg.medicalInstitution"
                @blur="validator('medicalInstitution')">
            </van-field>
            <van-field
                v-model="referenceInfo.medicalCategory"
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
  import ReferenceApi from '@/api/ReferenceApi'

  export default {
    data: function () {
      return {
        showAddress: false,
        nameDisabled: false,
        phoneDisabled: false,
        genderDisabled: false,
        referenceInfo: {
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
          address: {
            required: true,
            message: '请选择地址'
          },
          genderShow: {
            required: true,
            message: '请选择性别'
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
        genderList: [{text: '男', value: 1}, {text: '女', value: 2}]
      };
    },
    created: function () {
      this.referenceInfo.openId = this.$route.query.openId;
      this.referenceInfo.nickname = this.$route.query.nickname;
      this.loadCurrentUserInfo();
    },
    methods: {
      loadCurrentUserInfo: function () {
        UserApi.getCurrentUserInfo().then(userInfo => {
          if (!userInfo.userId) {
            return;
          }
          this.referenceInfo.name = userInfo.realName;
          this.nameDisabled = true;
          this.referenceInfo.phone = userInfo.phone;
          this.referenceInfo.gender = userInfo.gender.code;
          this.phoneDisabled = true;
          this.referenceInfo.genderShow = userInfo.gender.desc;
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
        return validator.validate(this.referenceInfo, (errors, fields) => {
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
      addressSelectConfirm: function (data) {
        this.referenceInfo.address = data[0].name + " " + data[1].name + " " + data[2].name;
        this.showAddress = false;
        this.validator('address');
      },
      addressSelectCancel: function () {
        this.referenceInfo.address = '';
        this.showAddress = false;
        this.validator('address');
      },
      onChangeGender: function (picker, value) {
        this.referenceInfo.gender = value.value;
        this.referenceInfo.genderShow = value.text;
        this.showGenderPopup = false;
      },
      onConfirmGender: function (value) {
        this.referenceInfo.gender = value.value;
        this.referenceInfo.genderShow = value.text;
        this.showGenderPopup = false;
      },
      onCancelAction: function () {
        this.onGoBack();
      },
      onRegisterAction: function () {
        this.validatorRegisterInfo().then(() => {
          ReferenceApi.registerReference(this.referenceInfo).then(loginInfo => {
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