<template>
    <div>
        <van-nav-bar title="招募报名" left-arrow @click-left="onApplicationCancelAction"></van-nav-bar>
        <van-cell-group>
            <van-field
                v-model="recruitment.title"
                label="招募项目"
                disabled
            ></van-field>
            <van-field
                v-model="applicationInfo.name"
                label="患者姓名"
                required
                clearable
                placeholder="请输入患者姓名"
                :error-message="errorMsg.name"
                @blur="validator('name')"
            ></van-field>
            <van-field
                v-model="applicationInfo.phone"
                required
                clearable
                label="患者手机号"
                placeholder="请输入患者手机号"
                :error-message="errorMsg.phone"
                @blur="validator('name')"
            ></van-field>
            <van-field
                v-model="applicationInfo.genderShow"
                required
                clearable
                label="患者性别"
                placeholder="请选择患者性别"
                :error-message="errorMsg.genderShow"
                @blur="validator('genderShow')"
                @focus="showGenderPopup = true"
            ></van-field>
            <van-popup v-model="showGenderPopup" position="bottom">
                <van-picker :columns="genderList" show-toolbar title="选择患者性别"
                            @change="onChangeGender" @confirm="onConfirmGender"
                            cancel-button-text=""
                            :visible-item-count=3></van-picker>
            </van-popup>
            <van-field
                v-model="applicationInfo.address"
                required
                label="患者地址"
                placeholder="请输入患者地址"
                :error-message="errorMsg.address"
                @blur="validator('address')"
                @focus="showAddress = true">
            </van-field>
            <address-select :show="showAddress" @confirm="addressSelectConfirm"
                            @cancel="addressSelectCancel"></address-select>
            <van-field
                v-model="applicationInfo.age"
                required
                clearable
                label="患者年龄"
                placeholder="请输入患者年龄"
                :error-message="errorMsg.age"
                @blur="validator('age')"
                type="number"
            ></van-field>
            <van-field
                v-model="applicationInfo.illHistory"
                label="病史表述"
                type="textarea"
                placeholder="病史表述"
                rows="2"
                autosize
            ></van-field>
            <van-field
                v-model="applicationInfo.familyIllHistory"
                label="家族遗传病史"
                type="textarea"
                placeholder="家族遗传病史表述"
                rows="2"
                autosize
            ></van-field>
            <van-field
                label="病例图片"
                center>
                <van-uploader slot="button" :after-read="onUploaderRead">
                    <van-icon name="photograph"></van-icon>
                </van-uploader>
            </van-field>
        </van-cell-group>
        <van-row class="submit-panel">
            <van-col span="11">
                <van-button size="small" block plain hairline type="primary"
                            @click="onApplicationCancelAction">取消
                </van-button>
            </van-col>
            <van-col span="11" offset="2">
                <van-button size="small" block type="primary" @click="onApplicationAction">提交报名
                </van-button>
            </van-col>
        </van-row>
    </div>
</template>
<style>
    .submit-panel {
        margin: 15px auto 10px auto;
        width: 95%;
    }
</style>
<script>
  import {
    NavBar,
    Button,
    Icon,
    Row,
    Col,
    Popup,
    Cell,
    CellGroup,
    Uploader,
    Field,
    Picker
  } from 'vant';
  import AddressSelect from "@/components/AddressSelect";
  import AsyncValidator from 'async-validator';
  import RecruitmentApi from "@/api/RecruitmentApi";
  import PatientApi from "@/api/PatientApi";
  import UserApi from "@/api/UserApi";
  import FileApi from "@/api/FileApi";

  export default {
    components: {
      [NavBar.name]: NavBar,
      [Button.name]: Button,
      [Icon.name]: Icon,
      [Row.name]: Row,
      [Col.name]: Col,
      [Popup.name]: Popup,
      [Cell.name]: Cell,
      [CellGroup.name]: CellGroup,
      [Uploader.name]: Uploader,
      [Field.name]: Field,
      [Picker.name]: Picker,
      [AddressSelect.name]: AddressSelect,
    },
    data: function () {
      return {
        recruitment: {
          title: '',
        },
        applicationInfo: {
          name: ''
        },
        errorMsg: {},
        rules: {},
        showAddress: false,
        showGenderPopup: false,
        genderList: [{text: '男', value: 1}, {text: '女', value: 2}]
      }
    },
    created: function () {
      this.onLoadRecruitmentInfo();
    },
    methods: {
      onApplicationAction: function () {
        this.validatorApplicationInfo().then(() => {
          RecruitmentApi.recruitmentApplication(this.applicationInfo).then(userId => {
            if (userId) {
              this.$toast('报名成功！即将跳转');
              if (!UserApi.isLogin()) {
                UserApi.saveUserId(userId);
              }
              setTimeout(() => {
                this.onGoBack();
              }, 2000);
            }
          });
        });
      },
      onApplicationCancelAction: function () {
        this.onGoBack();
      },
      onGoBack: function () {
        let redirectURL = this.$route.query.redirectURL;
        if (typeof redirectURL === 'undefined' || redirectURL.length <= 0) {
          redirectURL = '/recruitment/applicationList';
        }
        this.$router.push({path: redirectURL});
      },
      validatorApplicationInfo: function (item) {
        const validator = new AsyncValidator(this.rules);
        return validator.validate(this.applicationInfo, (errors, fields) => {
          if (errors) {
            this.handlerError(item, errors, fields)
          } else {
            this.errorMsg = {};
          }
        });
      },
      validator: function (item) {
        this.validatorApplicationInfo(item);
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
        this.applicationInfo.address = data[0].name + " " + data[1].name + " " + data[2].name;
        this.showAddress = false;
        this.validator('address');
      },
      addressSelectCancel: function () {
        this.applicationInfo.address = '';
        this.showAddress = false;
        this.validator('address');
      },
      onChangeGender: function (picker, value) {
        this.applicationInfo.gender = value.value;
        this.applicationInfo.genderShow = value.text;
        this.showGenderPopup = false;
      },
      onConfirmGender: function (value) {
        this.applicationInfo.gender = value.value;
        this.applicationInfo.genderShow = value.text;
        this.showGenderPopup = false;
      },
      onUploaderRead: function (file) {
        FileApi.uploadFile({
          data: file.content
        }).then((data) => {
          window.console.log(data);
        });
        window.console.log(file);
      },
      onLoadRecruitmentInfo: function () {
        let recruitmentId = this.$route.query.recruitmentId;
        RecruitmentApi.getRecruitmentById(recruitmentId).then(recruitmentInfo => {
          this.recruitment = recruitmentInfo;
          this.applicationInfo.recruitmentId = recruitmentInfo.recruitmentId;
        });
        PatientApi.getCurrentPatientInfo().then(patientInfo => {
          if (!patientInfo.patientId) {
            return;
          }
          this.applicationInfo.name = patientInfo.userInfoVO.realName;
          this.applicationInfo.phone = patientInfo.userInfoVO.phone;
          this.applicationInfo.genderShow = patientInfo.userInfoVO.gender.desc;
          this.applicationInfo.gender = patientInfo.userInfoVO.gender.code;
          this.applicationInfo.address = patientInfo.address;
          this.applicationInfo.age = patientInfo.age;
        });
        this.applicationInfo.nickname = this.$route.query.nickname;
        this.applicationInfo.openId = this.$route.query.openId;
        this.applicationInfo.doctorUserId = this.$route.query.doctorUserId;
      }
    }
  }
</script>
