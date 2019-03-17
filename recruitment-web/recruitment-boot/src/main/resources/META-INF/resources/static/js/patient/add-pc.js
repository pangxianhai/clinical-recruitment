$(function () {
  const PatientAdd = {
    bindPatientAddressSelectAction: function () {
      let patientAddressNode = $('.patient-address');
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
    bindAddPatientAction: function () {
      $('#addPatientForm').validate({
        success: function () {
          const data = {};
          $.each($('#addPatientForm').serializeArray(),
              function (i, item) {
                data[item.name] = item.value;
              });
          Ajax.post('/patient/add.json', data, function (result) {
            if (result) {
              $.alert('添加成功');
              setTimeout(function () {
                window.location.href = '/patient/list-pc';
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
      this.bindPatientAddressSelectAction();
      this.bindAddPatientAction()
    }
  };
  PatientAdd.main();
});