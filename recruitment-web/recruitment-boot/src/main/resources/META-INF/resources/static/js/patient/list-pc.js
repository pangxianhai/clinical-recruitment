$(function () {
  const PatientList = {
    loadPatientInfo: function (currentPage) {
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
    main: function () {
      this.loadPatientInfo(1);
    }
  };
  PatientList.main();
});