$(function () {
  const ManagerList = {
    loadManagerInfo: function (currentPage) {
      const $this = this;
      $.ajax({
        url: '/user/manager/listPcInfo',
        type: 'get',
        data: {
          currentPage: currentPage,
          pageSize: 10
        },
        headers: {
          token: CookieUtil.getCookie("userId")
        },
        success: function (html) {
          $('#manager-panel').html(html);
          $this.managerPagination();
        }
      });
    },
    managerPagination: function () {
      const $this = this;
      $('.pagination').pagination({
        pages: parseInt($('#totalPages').val()),
        displayPage: 6,
        onSelect: function (page) {
          $this.loadManagerInfo(page);
        }
      });
    },
    main: function () {
      this.loadManagerInfo(1);
    }
  };
  ManagerList.main();
});