$(function () {
  const Register = {
    addressInit: function () {
      $('#addressInput').cityPicker({
        toolbarTemplate: '<header class="bar bar-nav"><button class="button button-link pull-right close-picker">确定</button><h1 class="title">选择地址</h1></header>'
      });
    },
    registerAction: function () {
      $('#registerButton').on('click', function () {
        alert($('#addressInput').val());
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