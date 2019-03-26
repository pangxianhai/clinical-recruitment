$(function () {
  const Detail = {
    bindRecruitmentBegin: function () {
      $('.sui-container').on('click', '[item="begin"]', function () {
        let recruitmentId = $(this).attr('recruitmentId');
        Ajax.post('/recruitment/' + recruitmentId + '/begin.json', {},
            function (data) {
              if (data) {
                $.alert('操作成功');
                setTimeout(function () {
                  window.location.reload(true);
                }, 2000);
              } else {
                $.alert('操作失败');
              }
            });
      });
    },
    bindRecruitmentEnd: function () {
      $('.sui-container').on('click', '[item="stop"]', function () {
        let recruitmentId = $(this).attr('recruitmentId');
        Ajax.post('/recruitment/' + recruitmentId + '/end.json', {},
            function (data) {
              if (data) {
                $.alert('操作成功');
                setTimeout(function () {
                  window.location.reload(true);
                }, 2000);
              } else {
                $.alert('操作失败');
              }
            });
      });
    },
    main: function () {
      this.bindRecruitmentBegin();
      this.bindRecruitmentEnd();
    }
  };
  Detail.main();
});