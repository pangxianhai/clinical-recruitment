$(function () {
  const PatientList = {
    loadPatientInfo: function (currentPage) {
      $('.loading').show();
      const $this = this;
      $.ajax({
        url: '/patient/listPcInfo',
        type: 'get',
        data: {
          currentPage: currentPage,
          pageSize: 10
        },
        headers: {
          token: CookieUtil.getCookie("userId")
        },
        success: function (html) {
          $('#patient-panel').html(html);
          $('.loading').hide();
          $this.patientPagination();
        }
      });
    },
    patientPagination: function () {
      const $this = this;
      $('.pagination').pagination({
        pages: parseInt($('#totalPages').val()),
        displayPage: 6,
        onSelect: function (page) {
          $this.loadPatientInfo(page);
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
          relName: '患者姓名输入太长',
          phone: '手机号输入太长'
        },
        success: function () {
          $('#tips-error .msg-error').hide();
          $this.loadRecruitmentInfo(1);
          return false;
        }
      });
    },
    main: function () {
      this.loadPatientInfo(1);
      this.bindSearchAction();
    }
  };
  PatientList.main();
});