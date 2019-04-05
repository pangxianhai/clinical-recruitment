'use strict';

import axios from 'axios';
import {Toast} from 'vant';

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
    document.cookie = name + "=" + this.getCookie(name) + ";expires=0";
  }
};

const ajax = axios.create({
  baseURL: '/apis/',
  timeout: 180000,
  withCredentials: true
});

ajax.interceptors.request.use((config) => {
  let token = CookieUtil.getCookie('user_id');
  if (typeof token === 'undefined' || token == null) {
    token = '';
  }
  config.headers = Object.assign({
    token: token
  }, config.headers);
  return config;
});

ajax.interceptors.response.use(({data}) => {
  if (!data.ret && data.code === 102002) {
    window.location.href = data.data.loginAddress + "/login?redirectUrl="
        + encodeURIComponent(window.location.href);
  } else if (data.ret) {
    return data.data;
  } else if (typeof data.message === 'string' && data.message.length > 0) {
    Toast.fail(data.message);
  }
});

const Api = {
  get: async (url, params) => {
    try {
      const response = await ajax.request({
        method: 'get',
        url: url,
        params: params
      });
      return response;
    } catch (e) {
      window.console.error(e);
    }
  },

  post: async (url, params) => {
    try {
      const response = await ajax.request({
        method: 'post',
        url: url,
        data: params
      });
      return response;
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
}

export const ApiUtil = Api;

export const CookieUtil = Cookie;
