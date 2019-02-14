$(function () {
  const recruitment = {
    initIndicationPicker: function () {
      const $this = this;
      $('#indicationPicker').picker({
        toolbarTemplate: '<header class="bar bar-nav"><button class="button button-link pull-left">清除</button><button class="button button-link pull-right close-picker">确定</button><h1 class="title">请选择</h1></header>',
        cols: [
          {
            textAlign: 'center',
            values: $('#indicationOptions').val().split(',')
          }
        ],
        onClose: function () {
          $('#recruitment-list').html('');
          $this.loadRecruitmentInfo();
        },
        onOpen: function () {
          $('.picker-modal').on('click', '.pull-left', function () {
            $('#indicationPicker').val('');
            $('#indicationPicker').picker("close");
          })
        }
      });
    },
    initAddressPicker: function () {
      const $this = this;
      $('#addressPicker').cityPicker({
        toolbarTemplate: '<header class="bar bar-nav"><button class="button button-link pull-left">清除</button><button class="button button-link pull-right close-picker">确定</button><h1 class="title">选择地址</h1></header>',
        onClose: function () {
          $('#recruitment-list').html('');
          $this.loadRecruitmentInfo();
        },
        onOpen: function () {
          $('.city-picker').on('click', '.pull-left', function () {
            $("#addressPicker").val('');
            $('#addressPicker').picker("close");
          })
        }
      });
    },
    loadRecruitmentInfo: function () {
      $.showPreloader('正在加载...');
      const data = {};
      $.each($('#searchForm').serializeArray(), function (i, item) {
        data[item.name] = item.value;
      });
      $.ajax({
        url: '/recruitment/listInfo',
        type: 'get',
        data: data,
        headers: {
          token: CookieUtil.getCookie("userId")
        },
        success: function (html) {
          $('#recruitment-list').append(html);
          $.hidePreloader();
        }
      });
    },
    bindSearchAction: function () {
      const $this = this;
      $('#searchButton').on('click', function () {
        $('#recruitment-list').html('');
        $this.loadRecruitmentInfo();
      })
    },
    bindSignUpAction: function () {
      $('#recruitment-list').on('click', '[item="sign_up_button"]',
          function () {
            const recruitmentId = $(this).attr("recruitmentId");
            Ajax.post('/recruitmentapplication', {
              recruitmentId: recruitmentId
            }, function (data) {
              if (data) {
                $.alert('报名成功');
              } else {
                $.alert('报名失败');
              }
            });
          });
    },
    bindRecommendQrCodeAction: function () {
      $('#recruitment-list').on('click', '[item="recommend_qrcode"]',
          function () {
            const recruitmentId = $(this).attr("recruitmentId");
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
      this.loadRecruitmentInfo();
      this.initIndicationPicker();
      this.initAddressPicker();
      this.bindSignUpAction();
      this.bindSearchAction();
      this.bindRecommendQrCodeAction();
    }
  };
  recruitment.main();
});