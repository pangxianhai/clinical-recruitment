$(function () {
  const ManagerAdd = {
    addManagerAction: function () {
      $('#addManagerForm').validate({
        success: function () {
          const data = {};
          $.each($('#addManagerForm').serializeArray(), function (i, item) {
            data[item.name] = item.value;
          });
          Ajax.post('/user/manager/add.json', data, function (result) {
            if (result) {
              $.alert('注册成功');
              setTimeout(function () {
                window.location.href = '/user/manager/list-pc';
              }, 2000);
            } else {
              $.alert('注册失败！！');
            }
          });
          console.log(data);
          return false;
        }
      });
    },
    main: function () {
      this.addManagerAction();
    }
  };
  ManagerAdd.main();
});