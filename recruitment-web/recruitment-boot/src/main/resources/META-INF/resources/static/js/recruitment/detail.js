$(function () {
  const Detail = {
    bindSignUpAction: function () {
      $('#sign_up_button').on('click', function () {
        const recruitmentId = $('#recruitmentId').val();
        Ajax.post('/recruitmentapplication', {
          recruitmentId: recruitmentId
        }, function (data) {
          if (data) {
            $.alert('报名成功');
          } else {
            $.alert('报名失败');
          }
        });
      })
    },
    main: function () {
      $.init();
      this.bindSignUpAction();
    }
  };
  Detail.main();
});