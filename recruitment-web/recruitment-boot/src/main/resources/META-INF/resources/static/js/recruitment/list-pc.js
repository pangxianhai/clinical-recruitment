$(function () {
  const RecruitmentList = {
    loadRecruitmentInfo: function (currentPage) {
      const $this = this;
      $.ajax({
        url: '/recruitment/listPcInfo',
        type: 'get',
        data: {
          currentPage: currentPage,
          pageSize: 10
        },
        headers: {
          token: CookieUtil.getCookie("userId")
        },
        success: function (html) {
          $('#recruitment-panel').html(html);
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
          $this.loadRecruitmentInfo(page);
        }
      });
    },
    main: function () {
      this.loadRecruitmentInfo(1);
    }
  };
  RecruitmentList.main();
});