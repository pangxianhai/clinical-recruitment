$(function () {
  const DoctorList = {
    loadDoctorInfo: function (currentPage) {
      $('.loading').show();
      const $this = this;
      const data = {};
      $.each($('.form-search').serializeArray(), function (i, item) {
        data[item.name] = item.value;
      });
      $.ajax({
        url: '/doctor/listPcInfo',
        type: 'get',
        data: $.extend(data, {
          currentPage: currentPage,
          pageSize: 10
        }),
        headers: {
          token: CookieUtil.getCookie("userId")
        },
        success: function (html) {
          $('#doctor-panel').html(html);
          $('.loading').hide();
          $this.doctorPagination();
        }
      });
    },
    doctorPagination: function () {
      const $this = this;
      $('.pagination').pagination({
        pages: parseInt($('#totalPages').val()),
        displayPage: 6,
        onSelect: function (page) {
          $this.loadDoctorInfo(page);
        }
      });
    },
    bindSearchAction: function () {
      const $this = this;
      $('.form-search').validate({
        highlight: function (input, error, inputErrorClass) {
          input.addClass(inputErrorClass);
          error.hide();
          $('#tips-error .msg-con').html(error.find('span').html());
          $('#tips-error .msg-error').show();
          return false;
        },
        unhighlight: function (input, error, inputErrorClass) {
          $('#tips-error .msg-error').hide();
          input.removeClass(inputErrorClass);
          return false;
        },
        messages: {
          realName: '医生姓名输入太长',
          phoneLike: '手机号输入太长'
        },
        success: function () {
          $('#tips-error .msg-error').hide();
          $this.loadDoctorInfo(1);
          return false;
        }
      });
    },
    bindFreezeDoctor: function () {
      $('#doctor-panel').on('click', '[item="freeze"]', function () {
        let userId = $(this).attr('userId');
        Ajax.post('/user/' + userId + '/freeze.json', {},
            function (result) {
              if (result) {
                $.alert('操作成功！！');
                setTimeout(function () {
                  window.location.reload(true);
                }, 2000);
              } else {
                $.alert('操作失败！！');
              }
            });
      });
    },
    bindUnfreezeDoctor: function () {
      $('#doctor-panel').on('click', '[item="unfreeze"]', function () {
        let userId = $(this).attr('userId');
        Ajax.post('/user/' + userId + '/unfreeze.json', {},
            function (result) {
              if (result) {
                $.alert('操作成功！！');
                setTimeout(function () {
                  window.location.reload(true);
                }, 2000);
              } else {
                $.alert('操作失败！！');
              }
            });
      });
    },
    main: function () {
      this.loadDoctorInfo(1);
      this.bindSearchAction();
      this.bindFreezeDoctor();
      this.bindUnfreezeDoctor();
    }
  };
  DoctorList.main();
});