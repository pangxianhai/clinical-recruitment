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
};

const StringUtil = {
  isEmpty: function (text) {
    return typeof text === 'undefined' || null == text || text.length === 0;
  },
  isNotEmpty: function (text) {
    return !StringUtil.isEmpty(text);
  }
};