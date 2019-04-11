<template>
    <div class="patient-register">
        <van-nav-bar title="患者注册" left-arrow @click-left="onGoBack"></van-nav-bar>
        <van-cell-group>
            <van-field
                v-model="patientInfo.name"
                required
                clearable
                label="姓名"
                placeholder="请输入姓名"
                :error-message="errorMsg.name"
                @blur="validator('name')"
            ></van-field>
            <van-field
                v-model="patientInfo.phone"
                required
                clearable
                label="手机号"
                placeholder="请输入手机号"
                :error-message="errorMsg.phone"
                @blur="validator('phone')"
            ></van-field>
            <van-field
                v-model="patientInfo.genderShow"
                required
                clearable
                label="性别"
                placeholder="请选择性别"
                :error-message="errorMsg.genderShow"
                @blur="validator('genderShow')"
                @focus="showGenderPopup = true"
            ></van-field>
            <van-popup v-model="showGenderPopup" position="bottom">
                <van-picker :columns="genderList" show-toolbar title="选择性别"
                            @change="onChangeGender" @confirm="onConfirmGender"
                            cancel-button-text=""
                            :visible-item-count=3>
                </van-picker>
            </van-popup>
            <van-field
                v-model="patientInfo.address"
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
                v-model="patientInfo.age"
                required
                clearable
                label="年龄"
                placeholder="请输入年龄"
                :error-message="errorMsg.age"
                @blur="validator('age')"
                type="number"
            ></van-field>
            <van-button class="register-button"
                        size="normal" block
                        type="primary"
                        @click="patientRegister">注册
            </van-button>
        </van-cell-group>
    </div>
</template>

<style>
    .register-button {
        margin: 20px auto auto auto;
        width: 95%;
    }
</style>

<script>
  import {NavBar, CellGroup, Button, Col, Field, Popup, Picker, Row} from 'vant';
  import AddressSelect from "@/components/AddressSelect";
  import AsyncValidator from 'async-validator';
  import PatientApi from '@/api/PatientApi';
  import UserApi from "@/api/UserApi";

  export default {
    components: {
      [NavBar.name]: NavBar,
      [CellGroup.name]: CellGroup,
      [Button.name]: Button,
      [Col.name]: Col,
      [Field.name]: Field,
      [Popup.name]: Popup,
      [Picker.name]: Picker,
      [Row.name]: Row,
      [AddressSelect.name]: AddressSelect,
    },
    data: function () {
      return {
        showAddress: false,
        patientInfo: {},
        showGenderPopup: false,
        genderList: [{text: '男', value: 1}, {text: '女', value: 2}],
        errorMsg: {
          name: '',
          phone: ''
        },
        rules: {
          name: [{
            required: true,
            message: '请填写用户名'
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
          age: [
            {
              required: true,
              message: '请填写年龄'
            }
          ]
        }
      }
    },
    created: function () {
      this.patientInfo.openId = this.$route.query.openId;
      this.patientInfo.nickname = this.$route.query.nickname;
    },
    methods: {
      patientRegister: function () {
        this.validatorAll().then(() => {
          PatientApi.registerPatient(this.patientInfo).then(userId => {
            if (userId) {
              UserApi.saveUserId(userId);
              this.$toast.success('注册成功！即将跳转');
              setTimeout(() => {
                this.onGoBack();
              }, 2000);
            }
          });
        }).catch(({errors, fields}) => {
          this.handlerError(undefined, errors, fields);
        });
      },
      validator: function (item) {
        const validator = new AsyncValidator(this.rules);
        validator.validate(this.patientInfo, (errors, fields) => {
          if (errors) {
            this.handlerError(item, errors, fields)
          } else {
            this.errorMsg = {};
          }
        });
      },
      validatorAll: function () {
        const validator = new AsyncValidator(this.rules);
        return validator.validate(this.patientInfo, (errors, fields) => {
          if (errors) {
            this.handlerError(undefined, errors, fields)
          } else {
            this.errorMsg = {};
          }
        });
      },
      handlerError: function (item, errors, fields) {
        if (typeof  item !== 'undefined') {
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
        this.patientInfo.gender = value.value;
        this.patientInfo.genderShow = value.text;
        this.showGenderPopup = false;
      },
      onConfirmGender: function (value) {
        this.patientInfo.gender = value.value;
        this.patientInfo.genderShow = value.text;
        this.showGenderPopup = false;
      },
      addressSelectConfirm: function (data) {
        this.patientInfo.address = data[0].name + " " + data[1].name + " " + data[2].name;
        this.showAddress = false;
        this.validator('address');
      },
      addressSelectCancel: function () {
        this.patientInfo.address = '';
        this.showAddress = false;
        this.validator('address');
      },
      onGoBack: function () {
        let redirectURL = this.$route.query.redirectURL;
        if (typeof redirectURL === 'undefined' || redirectURL.length <= 0) {
          redirectURL = '/recruitment/list';
        }
        this.$router.push({path: redirectURL});
      },
    }
  }
</script>
