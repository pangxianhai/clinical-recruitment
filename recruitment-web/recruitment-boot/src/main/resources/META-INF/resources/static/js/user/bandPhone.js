$(function () {
  const BandPhone = {
    bindPhoneAction: function () {
      $('#bindButton').on('click', function () {
        const phone = $('#phoneInput').val();
        if (phone.length !== 11 || isNaN(phone)) {
          $.alert('手机号码不合法！');
          return;
        }
        Ajax.post('/user/login/bandPhone.json', {
          phone: phone,
          userType: $('#userTypeValue').val()
        }, function (data) {
          if (data) {
            $.alert('帮定成功');
            window.location.href = $('#redirectURL').val();
          } else {
            $.alert('帮定失败');
          }
        });
      });
    },
    main: function () {
      $.init();
      this.bindPhoneAction();
    }
  };
  BandPhone.main();
});