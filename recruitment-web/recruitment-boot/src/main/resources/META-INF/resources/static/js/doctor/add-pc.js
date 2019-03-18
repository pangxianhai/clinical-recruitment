$(function () {
  const DoctorAdd = {
    bindDoctorAddressSelectAction: function () {
      let patientAddressNode = $('.doctor-address');
      patientAddressNode.on('click', '[item="regionSelectItem"]', function () {
        let $this = $(this);
        let regionName = $this.attr('regionName');
        let provinceId = $this.attr('provinceId');
        let cityId = $this.attr('cityId');
        let districtId = $this.attr('districtId');
        patientAddressNode.find('[item="choiceText"]').html(regionName);
        patientAddressNode.find('[name="provinceId"]').val(provinceId);
        patientAddressNode.find('[name="cityId"]').val(cityId);
        patientAddressNode.find('[name="districtId"]').val(districtId);
      });
    },
    bindAddDoctorAction: function () {
      $('#addDoctorForm').validate({
        success: function () {
          const data = {};
          $.each($('#addDoctorForm').serializeArray(),
              function (i, item) {
                data[item.name] = item.value;
              });
          Ajax.post('/doctor/add.json', data, function (result) {
            if (result) {
              $.alert('添加成功');
              setTimeout(function () {
                window.location.href = '/doctor/list-pc';
              }, 2000);
            } else {
              $.alert('添加失败');
            }
          });
          return false;
        }
      });
    },
    main: function () {
      this.bindDoctorAddressSelectAction();
      this.bindAddDoctorAction();
    }
  };
  DoctorAdd.main();
});