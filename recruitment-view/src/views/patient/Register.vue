<template>
    <div class="patient-register">
        <van-nav-bar title=" 患者注册"></van-nav-bar>
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
  import AsyncValidator from 'async-validator';
  import PatientApi from '@/api/PatientApi';
  import {Toast} from 'vant';

  export default {
    data: function () {
      return {
        showAddress: false,
        patientInfo: {},
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
    methods: {
      patientRegister: function () {
        this.validatorAll().then(() => {
          PatientApi.registerPatient(this.patientInfo).then(data => {
            if (data) {
              Toast.success('注册成功！');
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
      addressSelectConfirm: function (data) {
        this.patientInfo.address = data[1].name + data[2].name;
        this.showAddress = false;
        this.validator('address');
      },
      addressSelectCancel: function () {
        this.patientInfo.address = '';
        this.showAddress = false;
        this.validator('address');
      }
    }
  }
</script>
