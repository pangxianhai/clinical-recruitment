'use strict';

import axios from 'axios';
import {Message} from 'element-ui';

export const CookieUtil = {
  getCookie: function (name) {
    const reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    let arr = document.cookie.match(reg);
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
  let userName = CookieUtil.getCookie('userName');
  let token = CookieUtil.getCookie('token');
  if (typeof userName === 'undefined' || userName == null) {
    userName = '';
  }
  if (typeof token === 'undefined' || token == null) {
    token = '';
  }
  config.headers = Object.assign({
    token: token,
    userName: userName
  }, config.headers);
  return config;
});

ajax.interceptors.response.use(({data}) => {
  window.console.log(data)
  if (data.code === 200) {
    return data.data;
  } else if (typeof data.message === 'string' && data.message.length > 0) {
    Message.error(data.message);
  } else {
    Message.error("操作失败!");
  }
});

export const ApiUtil = {
  get: async (url, params) => {
    try {
      return await ajax.request({
        method: 'get',
        url: url,
        params: params
      });
    } catch (e) {
      Message.error('操作失败');
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
      Message.error('操作失败');
      window.console.error(e);
    }
  },

  put: async (url, params) => {
    try {
      return await ajax.request({
        method: 'put',
        url: url,
        data: params
      });
    } catch (e) {
      Message.error('操作失败');
      window.console.error(e);
    }
  },

  patch: async (url, params) => {
    try {
      return await ajax.request({
        method: 'patch',
        url: url,
        data: params
      });
    } catch (e) {
      Message.error('操作失败');
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
    return !StringUtil.isEmpty(text);
  }
};

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
      router.push({path: redirectURL}, function () {
      });
    }, 2000);
  }
};

export const CollectionUtil = {
  isEmpty: function (list) {
    if (typeof list === 'undefined' || null == list || list.length === 0) {
      return true;
    } else {
      return false;
    }
  },
  isNotEmpty: function (list) {
    return !this.isEmpty(list);
  }
};
