const Cookie = function () {
};

Cookie.prototype = {
  constructor: Cookie,
  getCookie: function (name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) {
      return (arr[2]);
    } else {
      return null;
    }
  }
};
const CookieUtil = new Cookie();

const Ajax = {
  post: function (url, params, success) {
    $.ajax({
      url: url,
      type: 'post',
      dataType: "json",
      contentType: "application/json;charset=utf-8",
      data: JSON.stringify(params),
      headers: {
        token: CookieUtil.getCookie("userId")
      },
      success: function (result) {
        if (result.ret) {
          success(result.data);
        } else {
          $.alert(result.message);
        }
      }
    });
  },
  get: function (url, params, success) {
    $.ajax({
      url: url,
      type: 'get',
      dataType: "json",
      contentType: "application/json;charset=utf-8",
      data: params,
      headers: {
        token: CookieUtil.getCookie("userId")
      },
      success: function (result) {
        if (result.ret) {
          success(result.data);
        } else {
          $.alert(result.message);
        }
      }
    });
  }
}

const StringUtil = {
  isEmpty: function (text) {
    return typeof text === 'undefined' || null == text || text.length === 0;
  },
  isNotEmpty: function (text) {
    return !StringUtil.isEmpty(text);
  }

}

// let ajax = axios.create({
//   timeout: 180000,
//   withCredentials: true
// });
//
// ajax.interceptors.request.use((config) => {
//   Object.assign(config.headers, {
//     token: CookieUtil.getCookie("userId")
//   });
//   return config;
// });
//
// ajax.interceptors.response.use(({data}) => {
//   if (!data.ret && data.code === 102002) {
//     console.log("未登陆");
//     window.location.href = '/user/login?redirectURL=' + encodeURIComponent(
//         window.location.href);
//   }
//   if (data.ret) {
//     return data.data;
//   } else if (typeof data.message === 'string' && data.message.length > 0) {
//     let vue = new Vue({});
//     vue.$message({
//       showClose: true,
//       message: data.message,
//       type: 'error'
//     });
//   }
// });
//
// Vue.prototype.ajax = ajax;