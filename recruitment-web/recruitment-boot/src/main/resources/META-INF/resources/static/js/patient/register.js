$(function () {
  const Register = {
    addressInit: function () {
      $('#addressInput').cityPicker({
        toolbarTemplate: '<header class="bar bar-nav"><button class="button button-link pull-right close-picker">确定</button><h1 class="title">选择地址</h1></header>'
      });
    },
    registerAction: function () {
      $('#registerButton').on('click', function () {
        const phone = $('#phoneInput').val();
        if (StringUtil.isEmpty(phone)) {
          $.toast("手机号不能为空");
          return;
        }
        const address = $('#addressInput').val();
        if (StringUtil.isEmpty(address)) {
          $.toast("地址不能为空");
          return;
        }
        const age = $('#ageInput').val();
        if (StringUtil.isEmpty(age)) {
          $.toast("年龄不能为空");
          return;
        }
        if (isNaN(age)) {
          $.toast("年龄只能是数字");
          return;
        }
        const name = $('#nameInput').val();
        if (StringUtil.isEmpty(name)) {
          $.toast("年龄不能为空");
          return;
        }
        Ajax.post('/patient/register.json', {
          phone: phone,
          address: address,
          age: age,
          name: name,
          openId: $('#openId').val(),
          nickname: $('#nickname').val(),
          gender: $('#genderInput').val()
        }, function (data) {
          if (data) {
            if ('application' === $('#action').val()) {
              window.location.href = $('#redirectURL').val();
            } else {
              $.alert('注册成功', function () {
                window.location.href = $('#redirectURL').val();
              });
            }
          } else {
            $.alert('操作失败');
          }
        });
      });
    },
    main: function () {
      $.init();
      this.addressInit();
      this.registerAction();
    }
  };
  Register.main();
});