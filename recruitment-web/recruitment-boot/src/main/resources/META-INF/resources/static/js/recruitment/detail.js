$(function () {
  const Detail = {
    application: function (recruitmentId) {
      Ajax.post('/recruitmentapplication/application.json', {
        recruitmentId: recruitmentId,
        doctorId: $('#doctorId').val()
      }, function (data) {
        if (data) {
          $.alert('报名成功');
        } else {
          $.alert('报名失败');
        }
      });
    },
    bindSignUpAction: function () {
      let $this = this;
      $('#sign_up_button').on('click', function () {
        const recruitmentId = $('#recruitmentId').val();
        $this.application(recruitmentId);
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
    initApplication: function () {
      let action = $('#action').val();
      if ('application' === action) {
        this.application($('#recruitmentId').val());
        let param = [];
        let doctorId = $('#doctorId').val();
        if (doctorId.length > 0) {
          param.push('doctorId=' + doctorId);
        }
        history.pushState(null, null, '?' + param.join('&'));
      }
    },
    main: function () {
      $.init();
      this.bindSignUpAction();
      this.bindRecommendQrCodeAction();
      this.initApplication();
    }
  };
  Detail.main();
});