$(function () {
  const DoctorList = {
    loadDoctorInfo: function (currentPage) {
      const $this = this;
      $.ajax({
        url: '/doctor/listPcInfo',
        type: 'get',
        data: {
          currentPage: currentPage,
          pageSize: 10
        },
        headers: {
          token: CookieUtil.getCookie("userId")
        },
        success: function (html) {
          $('#doctor-panel').html(html);
          $this.recruitmentPagination();
        }
      });
    },
    recruitmentPagination: function () {
      const $this = this;
      $('.pagination').pagination({
        pages: parseInt($('#totalPages').val()),
        displayPage: 6,
        onSelect: function (page) {
          $this.loadDoctorInfo(page);
        }
      });
    },
    main: function () {
      this.loadDoctorInfo(1);
    }
  };
  DoctorList.main();
});