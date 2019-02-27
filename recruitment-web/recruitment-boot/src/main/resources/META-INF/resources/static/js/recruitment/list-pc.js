$(function () {
  const RecruitmentList = {
    loadRecruitmentInfo: function (currentPage) {
      $('.loading').show();
      const data = {};
      $.each($('.form-search').serializeArray(), function (i, item) {
        data[item.name] = item.value;
      });
      const $this = this;
      $.ajax({
        url: '/recruitment/listPcInfo',
        type: 'get',
        data: $.extend(data, {
          currentPage: currentPage,
          pageSize: 10
        }),
        headers: {
          token: CookieUtil.getCookie("userId")
        },
        success: function (html) {
          $('#recruitment-panel').html(html);
          $('.loading').hide();
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
          title: '标题输入太长',
          registerCode: '登记编号输入太长',
          indication: '适应症状输入太长'
        },
        success: function () {
          $('#tips-error .msg-error').hide();
          $this.loadRecruitmentInfo(1);
          return false;
        }
      });
    },
    main: function () {
      $('#tips-error .msg-error').hide();
      this.loadRecruitmentInfo(1);
      this.bindSearchAction();
    }
  };
  RecruitmentList.main();
});