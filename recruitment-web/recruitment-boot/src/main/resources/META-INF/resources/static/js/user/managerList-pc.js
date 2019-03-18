$(function () {
  const ManagerList = {
    loadManagerInfo: function (currentPage) {
      $('.loading').show();
      const data = {};
      $.each($('.form-search').serializeArray(), function (i, item) {
        data[item.name] = item.value;
      });
      const $this = this;
      $.ajax({
        url: '/user/manager/listPcInfo',
        type: 'get',
        data: $.extend(data, {
          currentPage: currentPage,
          pageSize: 10
        }),
        headers: {
          token: CookieUtil.getCookie("userId")
        },
        success: function (html) {
          $('#manager-panel').html(html);
          $('.loading').hide();
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
          realName: '姓名输入太长',
          phoneLike: '手机号输入太长'
        },
        success: function () {
          $('#tips-error .msg-error').hide();
          $this.loadManagerInfo(1);
          return false;
        }
      });
    },
    bindFreezeManageUser: function () {
      $('#manager-panel').on('click', '[item="freeze"]', function () {
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
    bindUnfreezeManageUser: function () {
      $('#manager-panel').on('click', '[item="unfreeze"]', function () {
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
      $('#tips-error .msg-error').hide();
      this.loadManagerInfo(1);
      this.bindSearchAction();
      this.bindFreezeManageUser();
      this.bindUnfreezeManageUser();
    }
  };
  ManagerList.main();
});