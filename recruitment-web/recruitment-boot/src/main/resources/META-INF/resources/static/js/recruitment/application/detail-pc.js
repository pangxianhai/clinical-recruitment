$(function () {
  const Detail = {
    bindAccedeAction: function () {
      $('.sui-container').on('click', '[item="accede"]', function () {
        let $this = $(this);
        let applicationId = $this.attr("applicationId");
        Ajax.post('/recruitmentapplication/' + applicationId + '/accede.json',
            {}, function (data) {
              if (data) {
                $.alert('操作成功');
                setTimeout(function () {
                  window.location.reload(true);
                }, 2000);
              } else {
                $.alert('操作失败');
              }
            })
      });
    },
    bindCancelAccedeAction: function () {
      $('.sui-container').on('click', '[item="cancelAccede"]', function () {
        let $this = $(this);
        let applicationId = $this.attr("applicationId");
        Ajax.post(
            '/recruitmentapplication/' + applicationId + '/cancelAccede.json',
            {}, function (data) {
              if (data) {
                $.alert('操作成功');
                setTimeout(function () {
                  window.location.reload(true);
                }, 2000);
              } else {
                $.alert('操作失败');
              }
            })
      });
    },
    main: function () {
      this.bindAccedeAction();
      this.bindCancelAccedeAction();
    }
  };
  Detail.main();
});