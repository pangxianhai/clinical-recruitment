$(function () {
  const Register = {
    addressInit: function () {
      $('#addressInput').cityPicker({
        toolbarTemplate: '<header class="bar bar-nav"><button class="button button-link pull-right close-picker">确定</button><h1 class="title">选择地址</h1></header>'
      });
    },
    registerAction: function () {
      $('#registerButton').on('click', function () {
        const name = $('#nameInput').val();
        if (StringUtil.isEmpty(name)) {
          $.toast("年龄不能为空");
          return;
        }
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
        const medicalInstitution = $('#medicalInstitutionInput').val();
        if (StringUtil.isEmpty(medicalInstitution)) {
          $.toast("执业机构不能为空");
          return;
        }
        const medicalCategory = $('#medicalCategoryInput').val();
        if (StringUtil.isEmpty(medicalCategory)) {
          $.toast("执业类型不能为空");
          return;
        }
        Ajax.post('/doctor/register.json', {
          name: name,
          phone: phone,
          address: address,
          medicalInstitution: medicalInstitution,
          medicalCategory: medicalCategory,
          openId: $('#openId').val(),
          nickname: $('#nickname').val(),
          gender: $('#genderInput').val()
        }, function (data) {
          if (data) {
            $.alert('注册成功', function () {
              window.location.href = $('#redirectURL').val();
            });
          } else {
            $.alert('注册失败');
          }
        })
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