'use strict';
import axios from 'axios';
import {Toast} from 'vant';

const Cookie = function () {
};

Cookie.prototype = {
  constructor: Cookie,
  getCookie: function (name) {
    const reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    let arr = document.cookie.match(reg)
    if (arr) {
      return (arr[2]);
    } else {
      return null;
    }
  }
};

const ajax = axios.create({
  timeout: 180000,
  withCredentials: true
});

ajax.interceptors.request.use((config) => {
  let token = CookieUtil.getCookie('GM_TOKEN');
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

export const Ajax = ajax;

export const CookieUtil = new Cookie();
