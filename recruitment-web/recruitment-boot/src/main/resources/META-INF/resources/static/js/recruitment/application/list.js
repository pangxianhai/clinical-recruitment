$(function () {
  const ApplicationList = {
    loadRecruitmentApplicationInfo: function () {
      $.showPreloader('正在加载...');
      $.ajax({
        url: '/recruitmentapplication/listInfo',
        type: 'get',
        headers: {
          token: CookieUtil.getCookie("userId")
        },
        success: function (html) {
          $('#recruitmentApplicationList').append(html);
          $.hidePreloader();
        }
      });
    },
    main: function () {
      $.init();
      this.loadRecruitmentApplicationInfo();
    }
  };
  ApplicationList.main();
});