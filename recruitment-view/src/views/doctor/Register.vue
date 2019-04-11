<template>
    <div class="doctor-register">
        <van-nav-bar title="医生注册" left-arrow @click-left="onGoBack"></van-nav-bar>
        <van-cell-group>
            <van-field
                v-model="doctorInfo.name"
                required
                clearable
                label="姓名"
                placeholder="请输入姓名"
                :error-message="errorMsg.name"
                @blur="validator('name')"
            ></van-field>
            <van-field
                v-model="doctorInfo.phone"
                required
                clearable
                label="手机号"
                placeholder="请输入手机号"
                :error-message="errorMsg.phone"
                @blur="validator('phone')"
            ></van-field>
            <van-field
                v-model="doctorInfo.genderShow"
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
                v-model="doctorInfo.address"
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
                v-model="doctorInfo.medicalInstitution"
                required
                label="执业机构"
                placeholder="请输入执业机构"
                :error-message="errorMsg.medicalInstitution"
                @blur="validator('medicalInstitution')">
            </van-field>
            <van-field
                v-model="doctorInfo.medicalCategory"
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

<script>
  import {NavBar, CellGroup, Button, Col, Field, Popup, Picker, Row} from 'vant';
  import AsyncValidator from 'async-validator';
  import DoctorApi from '@/api/DoctorApi';
  import UserApi from "@/api/UserApi";
  import AddressSelect from "@/components/AddressSelect";

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
        doctorInfo: {},
        errorMsg: {
          name: '',
          phone: ''
        },
        rules: {},
        showGenderPopup: false,
        genderList: [{text: '男', value: 1}, {text: '女', value: 2}]
      };
    },
    created: function () {
      this.doctorInfo.openId = this.$route.query.openId;
      this.doctorInfo.nickname = this.$route.query.nickname;
    },
    methods: {
      onGoBack: function () {
        let redirectURL = this.$route.query.redirectURL;
        if (typeof redirectURL === 'undefined' || redirectURL.length <= 0) {
          redirectURL = '/recruitment/list';
        }
        this.$router.push({path: redirectURL});
      },
      validatorRegisterInfo: function (item) {
        const validator = new AsyncValidator(this.rules);
        return validator.validate(this.doctorInfo, (errors, fields) => {
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
      addressSelectConfirm: function (data) {
        this.doctorInfo.address = data[0].name + " " + data[1].name + " " + data[2].name;
        this.showAddress = false;
        this.validator('address');
      },
      addressSelectCancel: function () {
        this.doctorInfo.address = '';
        this.showAddress = false;
        this.validator('address');
      },
      onChangeGender: function (picker, value) {
        this.doctorInfo.gender = value.value;
        this.doctorInfo.genderShow = value.text;
        this.showGenderPopup = false;
      },
      onConfirmGender: function (value) {
        this.doctorInfo.gender = value.value;
        this.doctorInfo.genderShow = value.text;
        this.showGenderPopup = false;
      },
      onCancelAction: function () {
        this.onGoBack();
      },
      onRegisterAction: function () {
        this.validatorRegisterInfo().then(() => {
          DoctorApi.registerDoctor(this.doctorInfo).then(userId => {
            if (userId) {
              this.$toast('注册成功！即将跳转');
              UserApi.saveUserId(userId);
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
