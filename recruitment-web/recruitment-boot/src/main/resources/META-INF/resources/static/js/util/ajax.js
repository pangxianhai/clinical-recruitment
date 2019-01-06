let ajax = axios.create({
  timeout: 180000,
  withCredentials: true
});

ajax.interceptors.request.use((config) => {
  Object.assign(config.headers, {
    token: 'aaaaaaaa'
  });
  return config;
});

ajax.interceptors.response.use(({data}) => {
  if (!data.ret && data.code === 302004) {
    console.log("未登陆");
  }
  if (data.ret) {
    return data.data;
  } else if (typeof data.message === 'string' && data.message.length > 0) {
    let vue = new Vue({});
    vue.$message({
      showClose: true,
      message: data.message,
      type: 'error'
    });
  }
});

Vue.prototype.ajax = ajax;