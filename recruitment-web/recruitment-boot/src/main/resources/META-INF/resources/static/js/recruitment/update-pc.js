$(function () {
  const RecruitmentUpdate = {
    bindUpdateRecruitmentBtnAction: function () {
      $('#updateRecruitmentForm').validate({
        success: function () {
          const data = {};
          $.each($('#updateRecruitmentForm').serializeArray(),
              function (i, item) {
                data[item.name] = item.value;
              });
          Ajax.post('/recruitment/update.json', data, function (result) {
            if (result) {
              $.alert('更新成功');
              setTimeout(function () {
                window.location.reload(true);
              }, 2000);
            } else {
              $.alert('更新失败');
            }
          });
          return false;
        }
      });
    },
    main: function () {
      this.bindUpdateRecruitmentBtnAction();
    }
  };
  RecruitmentUpdate.main();
});