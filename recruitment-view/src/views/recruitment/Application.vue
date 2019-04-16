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
                v-model="applicationInfo.diseaseDesc"
                label="病例描述"
                type="textarea"
                placeholder="病例描述"
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
            <van-row gutter="20" style="text-align: center;min-height: 80px">
                <van-col span="8" v-for="(image,index) in uploadImageList" :key="index">
                    <img width="80" height="80" :src="image.thumbnailUrl"
                         @click="previewImage(index)"/>
                </van-col>
            </van-row>
            <van-checkbox class="service-agreement" v-model="agreementService">
                我同意服务协议
                <a @click="onToServiceAgreement">（服务协议）</a>
            </van-checkbox>
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

    .service-agreement {
        text-align: center;
        color: #323233;
        font-size: 14px;
        padding-bottom: 10px;
        margin-top: 12px;
    }

    .service-agreement a {
        color: #07c160;
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
    Picker,
    Checkbox,
    CheckboxGroup,
    ImagePreview,
    Notify
  } from 'vant';
  import AddressSelect from "@/components/AddressSelect";
  import AsyncValidator from 'async-validator';
  import RecruitmentApi from "@/api/RecruitmentApi";
  import PatientApi from "@/api/PatientApi";
  import UserApi from "@/api/UserApi";
  import FileApi from "@/api/FileApi";
  import Vue from "vue"

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
      [Checkbox.name]: Checkbox,
      [CheckboxGroup.name]: CheckboxGroup,
      [ImagePreview.name]: ImagePreview,
      [Notify.name]: Notify,
    },
    data: function () {
      return {
        recruitment: {
          title: '',
        },
        applicationInfo: {
          name: '',
          diseaseImageList: []
        },
        errorMsg: {},
        rules: {},
        showAddress: false,
        showGenderPopup: false,
        genderList: [{text: '男', value: 1}, {text: '女', value: 2}],
        uploadImageList: [],
        agreementService: false
      }
    },
    created: function () {
      this.onLoadRecruitmentInfo();
    },
    methods: {
      onApplicationAction: function () {
        this.validatorApplicationInfo().then(() => {
          if (!this.agreementService) {
            Notify('请您同意服务协议');
            return;
          }
          let diseaseImageList = [];
          this.uploadImageList.forEach((imageInfo) => {
            diseaseImageList.push(imageInfo.imageId);
          });
          this.applicationInfo.diseaseImageList = diseaseImageList;
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
        }).then((imageInfo) => {
          this.uploadImageList.push({
            imageId: imageInfo.imageId,
            thumbnailUrl: imageInfo.thumbnailUrl,
            imageUrl: imageInfo.imageUrl,
          });
          let i = this.uploadImageList.length - 1;
          Vue.set(this.uploadImageList, i, this.uploadImageList[i]);
        });
      },
      onLoadRecruitmentInfo: function () {
        let recruitmentId = this.$route.query.recruitmentId;
        if (typeof recruitmentId === 'undefined') {
          return;
        }
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
      },
      onToServiceAgreement: function () {
        this.$router.push({
          path: '/site/serviceAgreement',
          query: {
            redirectURL: this.$route.path
          }
        });
      },
      previewImage: function (index) {
        let imageList = [];
        this.uploadImageList.forEach((imageInfo) => {
          imageList.push(imageInfo.imageUrl);
        });
        ImagePreview({
          images: imageList,
          startPosition: index
        });
      }
    }
  }
</script>
