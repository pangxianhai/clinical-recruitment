'use strict';

import axios from 'axios';
import {Message} from 'element-ui';

export const CookieUtil = {
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
  if (data.ret) {
    return data.data;
  } else if (typeof data.message === 'string' && data.message.length > 0) {
    Message.error(data.message);
  }
});

export const ApiUtil = {
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
  },

  patch: async (url, params) => {
    try {
      const response = await ajax.request({
        method: 'patch',
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

export const StringUtil = {
  isEmpty: (text) => {
    if (typeof text === 'undefined' || null == text || text.length === 0) {
      return true;
    } else {
      return false;
    }
  },
  isNotEmpty: (text) => {
    return !this.isEmpty(text);
  }
}

export const RouterUtil = {
  goToBack: (route, router, defaultPath) => {
    if (StringUtil.isEmpty(defaultPath)) {
      defaultPath = '/recruitment/list';
    }
    let redirectURL = route.query.redirectURL;
    if (StringUtil.isEmpty(redirectURL)) {
      redirectURL = defaultPath;
    }
    setTimeout(() => {
      router.push({path: redirectURL});
    }, 2000);
  }
};
