$(function () {
  const ApplicationList = {
    doctorSelect: function () {
      $('#doctor-select').autocomplete({
        serviceUrl: '/doctor/list.json',
        onSelect: function (suggestion) {
          $('#doctorId').val(suggestion.data.id);
        },
        onInvalidateSelection: function () {

        },
        dataType: "json",
        paramName: "doctorName",
        noCache: true,
        transformResult: function (result) {
          const suggestions = [];
          if (result.ret) {
            result.data.data.forEach(function (doctorInfo) {
              const item = {
                data: {
                  id: doctorInfo.doctorId
                }
              };
              if (typeof doctorInfo.userInfoVO !== 'undefined') {
                item.value = doctorInfo.userInfoVO.realName;
              } else {
                item.value = '张三';
              }
              suggestions.push(item);
            });
          }
          return {suggestions: suggestions};
        }
      });
    },
    patientSelect: function () {
      $('#patient-select').autocomplete({
        serviceUrl: '/patient/list.json',
        onSelect: function (suggestion) {
          $('#patientId').val(suggestion.data.id);
        },
        onInvalidateSelection: function () {

        },
        dataType: "json",
        paramName: "patientName",
        noCache: true,
        transformResult: function (result) {
          const suggestions = [];
          if (result.ret) {
            result.data.data.forEach(function (patientInfo) {
              const item = {
                data: {
                  id: patientInfo.patientId
                }
              };
              if (typeof patientInfo.userInfoVO !== 'undefined') {
                item.value = patientInfo.userInfoVO.realName;
              } else {
                item.value = '李四';
              }
              suggestions.push(item);
            });
          }
          return {suggestions: suggestions};
        }
      });
    },
    main: function () {
      this.doctorSelect();
      this.patientSelect();
    }
  };
  ApplicationList.main()
});