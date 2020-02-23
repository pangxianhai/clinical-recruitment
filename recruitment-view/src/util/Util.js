'use strict';

import axios from 'axios';
import {Toast} from 'vant';
import {CookieNameConstant} from '@/constants/Global';

const Cookie = {
  getCookie: function (name) {
    const reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    let arr = document.cookie.match(reg)
    if (arr) {
      return (arr[2]);
    } else {
      return null;
    }
  },
  setCookie: function (name, value) {
    document.cookie = name + "=" + encodeURIComponent(value)
        + ";path=/;domain="
        + window.location.hostname;
  },
  deleteCookie: function (name) {
    document.cookie = name + "=" + this.getCookie(name) + ";expires="
        + new Date().toGMTString()
        + ";path=/;domain="
        + window.location.hostname;
  }
};

const ajax = axios.create({
  baseURL: '/apis/',
  timeout: 180000,
  withCredentials: true
});

ajax.interceptors.request.use((config) => {
  let userName = CookieUtil.getCookie(CookieNameConstant.USER_NAME);
  let token = CookieUtil.getCookie(CookieNameConstant.TOKEN_NAME);
  if (StringUtil.isEmpty(token)) {
    token = '';
  }
  if (StringUtil.isEmpty(userName)) {
    userName = '';
  }
  config.headers = Object.assign({
    userName: userName,
    token: token
  }, config.headers);
  return config;
});

ajax.interceptors.response.use(({data}) => {
  if (data.success) {
    return data.data;
  } else if (typeof data.msg === 'string' && data.msg.length > 0) {
    Toast.fail(data.msg);
  } else {
    Toast.fail('未知错误!');
  }
});

const Api = {
  get: async (url, params) => {
    try {
      return await ajax.request({
        method: 'get',
        url: url,
        params: params
      });
    } catch (e) {
      window.console.error(e);
    }
  },

  post: async (url, params) => {
    try {
      return await ajax.request({
        method: 'post',
        url: url,
        data: params
      });
    } catch (e) {
      window.console.error(e);
    }
  },

  put: async (url, params) => {
    try {
      const response = await ajax.request({
        method: 'put',
        url: url,
        data: params
      });
      const data = await response.data;
      return {
        data
      }
    } catch (e) {
      window.console.error(e);
    }
  }
};

const stringUtil = {
  isEmpty: function (text) {
    return !this.isNotEmpty(text);
  },
  isNotEmpty: function (text) {
    return typeof text === 'string' && text.length > 0;
  }

};

export const ApiUtil = Api;

export const CookieUtil = Cookie;

export const StringUtil = stringUtil;
