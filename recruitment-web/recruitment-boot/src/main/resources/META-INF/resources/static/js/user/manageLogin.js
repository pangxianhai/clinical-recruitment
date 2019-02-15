$(function () {
  const MangeLogin = {
    bindManageLoginAction: function () {
      $('#manageLoginForm').validate({
        success: function () {
          const data = {};
          $.each($('#manageLoginForm').serializeArray(), function (i, item) {
            data[item.name] = item.value;
          });
          Ajax.post('/user/login/manage.json', data, function (result) {
            if (result) {
              $.alert('登陆成功');
            } else {
              $.alert('登陆失败');
            }
          });
          return false;
        }
      });
    },
    main: function () {
      this.bindManageLoginAction();
    }
  };
  MangeLogin.main();
});