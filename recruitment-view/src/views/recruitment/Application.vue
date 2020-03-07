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
                v-model="applicationInfo.patientName"
                label="患者姓名"
                required
                clearable
                placeholder="请输入患者姓名"
                :error-message="errorMsg.patientName"
                @blur="validator('patientName')"
            ></van-field>
            <van-field
                v-model="applicationInfo.patientPhone"
                required
                clearable
                label="患者手机号"
                placeholder="请输入患者手机号"
                :error-message="errorMsg.patientPhone"
                @blur="validator('patientPhone')"
            ></van-field>
            <van-field
                v-model="applicationInfo.patientGenderShow"
                required
                clearable
                label="患者性别"
                placeholder="请选择患者性别"
                :error-message="errorMsg.patientGenderShow"
                @blur="validator('patientGenderShow')"
                @focus="showGenderPopup = true"
            ></van-field>
            <van-popup v-model="showGenderPopup" position="bottom">
                <van-picker :columns="genderList" show-toolbar title="选择患者性别"
                            @change="onChangeGender" @confirm="onConfirmGender"
                            cancel-button-text=""
                            :visible-item-count=3></van-picker>
            </van-popup>
            <van-field
                v-model="applicationInfo.patientAddress"
                required
                label="患者地址"
                placeholder="请输入患者地址"
                :error-message="errorMsg.patientAddress"
                @blur="validator('patientAddress')"
                @focus="showAddress = true">
            </van-field>
            <address-select :show="showAddress" @confirm="addressSelectConfirm"
                            @cancel="addressSelectCancel"></address-select>
            <van-field
                v-model="applicationInfo.patientAge"
                required
                clearable
                label="患者年龄"
                placeholder="请输入患者年龄"
                :error-message="errorMsg.patientAge"
                @blur="validator('patientAge')"
                type="number"
            ></van-field>
            <van-field
                v-model="applicationInfo.departmentShow"
                required
                clearable
                label="研究机构"
                placeholder="请选择研究机构"
                :error-message="errorMsg.departmentShow"
                @blur="validator('departmentShow')"
                @focus="showDepartmentPopup=true"
            ></van-field>
            <van-popup v-model="showDepartmentPopup" position="bottom">
                <van-picker show-toolbar title="机构及科室列表"
                            :columns="departmentColumns"
                            @confirm="onConfirmDepartment"
                            @cancel="onCancelDepartment"
                />
            </van-popup>

            <van-field
                v-model="applicationInfo.referencePhone"
                clearable
                label="推荐人手机号"
                placeholder="请推荐人手机号"
                :error-message="errorMsg.referencePhone"
                @blur="validator('referencePhone')"
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
            <van-row type="flex" justify="center">
                <van-col>
                    <van-checkbox class="service-agreement" v-model="agreementService">
                        我同意服务协议
                        <a @click="onToServiceAgreement">（服务协议）</a>
                    </van-checkbox>
                </van-col>
            </van-row>
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
  import {ImagePreview} from 'vant';
  import AsyncValidator from 'async-validator';
  import RecruitmentApi from "@/api/RecruitmentApi";
  import UserApi from "@/api/UserApi";
  import FileApi from "@/api/FileApi";
  import Vue from "vue";
  import md5 from 'js-md5';
  import {ApplicationAction} from '@/constants/Global';
  import {StringUtil} from "../../util/Util";

  export default {
    data: function () {
      return {
        recruitment: {
          title: '',
        },
        applicationInfo: {
          recruitmentId: 0,
          patientName: '',
          patientPhone: '',
          patientGender: 0,
          patientGenderShow: '',
          patientAddress: '',
          patientAge: '',
          departmentId: 0,
          departmentShow: '',
          diseaseImageList: [],
          referencePhone: '',
          diseaseDesc: ''
        },
        errorMsg: {},
        rules: {},
        showAddress: false,
        showGenderPopup: false,
        genderList: [{text: '男', value: 1}, {text: '女', value: 2}],
        showDepartmentPopup: false,
        departmentColumns: [],
        uploadImageList: [],
        agreementService: false
      }
    },
    created: function () {
      this.onLoadUserInfo();
      this.onLoadRecruitmentInfo();
    },
    methods: {
      onLoadRecruitmentInfo: function () {
        let recruitmentId = this.$route.params.recruitmentId;
        if (typeof recruitmentId === 'undefined') {
          return;
        }
        RecruitmentApi.getRecruitmentById(recruitmentId).then(recruitmentInfo => {
          this.recruitment = recruitmentInfo;
          this.applicationInfo.recruitmentId = recruitmentInfo.recruitmentId;
        });
        this.loadDepartment(recruitmentId);
      },
      onLoadUserInfo: function () {
        let action = this.$route.query.action;
        if (!UserApi.isLogin()) {
          this.onToLogin('您未登录，正在为您跳转登录页面', action);
        } else {
          UserApi.getCurrentUserInfo().then(userDetailInfo => {
            if (ApplicationAction.REFERENCE === action) {
              if (!userDetailInfo.referenceInfoRes) {
                this.onToLogin('您还未成为推荐人，请先注册推荐人', action);
                return;
              }
              this.applicationInfo.referencePhone = userDetailInfo.phone;
            } else {
              //其他都默认参与
              if (!userDetailInfo.patientInfoRes) {
                this.onToLogin('您还未成为受试者，请先注册受试者', action);
                return;
              }
              this.applicationInfo.patientName = userDetailInfo.realName;
              this.applicationInfo.patientPhone = userDetailInfo.phone;
              this.applicationInfo.patientGender = userDetailInfo.gender.code;
              this.applicationInfo.patientGenderShow = userDetailInfo.gender.desc;
              this.applicationInfo.patientAddress = userDetailInfo.patientInfoRes.address;
              this.applicationInfo.patientAge = userDetailInfo.patientInfoRes.age;
              let referenceUserId = this.$route.query.referenceUserId;

              if (StringUtil.isNotEmpty(referenceUserId)) {
                UserApi.getUserInfoById(referenceUserId).then(userInfo => {
                  if (userInfo) {
                    this.applicationInfo.referencePhone = userInfo.phone;
                  }
                });
              }
            }
          });
        }
      },
      loadDepartment: function (recruitmentId) {
        RecruitmentApi.getRecruitmentDepartment(recruitmentId).then(departmentDetailList => {
          let departmentColumns = [];
          for (let i = 0; i < departmentDetailList.length; ++i) {
            let departmentInfo = departmentDetailList[i];
            departmentColumns.push({
              text: departmentInfo.organizationRes.name + '/' + departmentInfo.name,
              value: departmentInfo.departmentId
            });
          }
          this.departmentColumns = departmentColumns;
        });
      },
      onGoBack: function () {
        let redirectURL = this.$route.query.redirectURL;
        if (typeof redirectURL === 'undefined' || redirectURL.length <= 0) {
          redirectURL = '/recruitment/applicationList';
        }
        this.$router.push({path: String(redirectURL)}, function () {
        });
      },

      onToServiceAgreement: function () {
        this.$router.push({
          path: '/site/serviceAgreement',
          query: {
            redirectURL: this.$route.path
          }
        }, function () {

        });
      },
      onToLogin: function (notifyMsg, action) {
        this.$toast(notifyMsg);
        setTimeout(() => {
          this.$router.push({
            path: '/user/login',
            query: {
              redirectURL: this.$route.fullPath,
              action: action
            },
          }, function () {

          });
        }, 3000);
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
        this.applicationInfo.patientAddress = data[0].name + " " + data[1].name + " "
            + data[2].name;
        this.showAddress = false;
        this.validator('patientAddress');
      },
      addressSelectCancel: function () {
        this.applicationInfo.address = '';
        this.showAddress = false;
        this.validator('patientAddress');
      },
      onChangeGender: function (picker, value) {
        this.applicationInfo.patientGender = value.value;
        this.applicationInfo.patientGenderShow = value.text;
        this.showGenderPopup = false;
      },
      onConfirmGender: function (value) {
        this.applicationInfo.patientGender = value.value;
        this.applicationInfo.patientGenderShow = value.text;
        this.showGenderPopup = false;
      },
      onConfirmDepartment: function (value) {
        this.applicationInfo.departmentId = value.value;
        this.applicationInfo.departmentShow = value.text;
        this.showDepartmentPopup = false;
      },
      onCancelDepartment: function () {
        this.showDepartmentPopup = false;
      },
      onUploaderRead: function (file) {
        this.$toast({
          type: 'loading',
          mask: true,
          duration: 10000,
          forbidClick: true,
          loadingType: 'spinner',
          message: '图片上传中...'
        });
        let fileName = md5(file.content) + "." + file.file.name.split('.')[1];
        FileApi.uploadDirectOss(fileName, file.content, process.env).then(result => {
          this.uploadImageList.push({
            imageId: result.name,
            thumbnailUrl: result.url + "?x-oss-process=image/resize,m_fixed,w_80,h_80",
            imageUrl: result.url,
          });
          let i = this.uploadImageList.length - 1;
          Vue.set(this.uploadImageList, i, this.uploadImageList[i]);
          this.$toast.clear();
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
      },
      onApplicationAction: function () {
        this.validatorApplicationInfo().then(() => {
          if (!this.agreementService) {
            this.$toast('请您同意服务协议');
            return;
          }
          let diseaseImageList = [];
          this.uploadImageList.forEach((imageInfo) => {
            diseaseImageList.push(imageInfo.imageId);
          });
          this.applicationInfo.diseaseImageList = diseaseImageList;
          window.console.log(this.applicationInfo);
          RecruitmentApi.recruitmentApplication(this.applicationInfo.recruitmentId,
              this.applicationInfo).then(success => {
            if (success) {
              this.$toast('报名成功！即将跳转');
              setTimeout(() => {
                this.onGoBack();
              }, 2000);
            }
          });
        });
      },
      onApplicationCancelAction: function () {
        this.onGoBack();
      }
    }
  }
</script>
