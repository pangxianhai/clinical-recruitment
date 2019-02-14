$(function () {
  const Detail = {
    bindSignUpAction: function () {
      $('#sign_up_button').on('click', function () {
        const recruitmentId = $('#recruitmentId').val();
        Ajax.post('/recruitmentapplication', {
          recruitmentId: recruitmentId,
          doctorId: $('#doctorId').val()
        }, function (data) {
          if (data) {
            $.alert('报名成功');
          } else {
            $.alert('报名失败');
          }
        });
      })
    },
    bindRecommendQrCodeAction: function () {
      $('#recommend_qrcode').on('click', function () {
        const recruitmentId = $('#recruitmentId').val();
        const detailUrl = window.location.protocol + "//"
            + window.location.host +
            '/detail/' + recruitmentId + "?doctorId="
            + CookieUtil.getCookie("userId");
        $('#qrCode').empty();
        const qrcode = new QRCode(document.getElementById("qrCode"), {
          width: 250,
          height: 250
        });
        qrcode.makeCode(detailUrl);
        $.popup('.popup-qrcode');
      });
    },
    main: function () {
      $.init();
      this.bindSignUpAction();
      this.bindRecommendQrCodeAction();
    }
  };
  Detail.main();
});