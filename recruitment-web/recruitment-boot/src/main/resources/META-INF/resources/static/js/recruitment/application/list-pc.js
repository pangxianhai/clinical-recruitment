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
    loadRecruitmentApplicationInfo: function (currentPage) {
      $('.loading').show();
      const data = {};
      $.each($('.form-search').serializeArray(), function (i, item) {
        data[item.name] = item.value;
      });
      const $this = this;
      $.ajax({
        url: '/recruitmentapplication/listInfo-pc',
        type: 'get',
        data: $.extend(data, {
          currentPage: currentPage,
          pageSize: 10
        }),
        headers: {
          token: CookieUtil.getCookie("userId")
        },
        success: function (html) {
          $('#application-panel').html(html);
          $('.loading').hide();
          $this.recruitmentApplicationPagination();
        }
      });
    },
    recruitmentApplicationPagination: function () {
      const $this = this;
      $('.pagination').pagination({
        pages: parseInt($('#totalPages').val()),
        displayPage: 6,
        onSelect: function (page) {
          $this.loadRecruitmentApplicationInfo(page);
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
          registerCode: '登记编号输入太长'
        },
        success: function () {
          $('#tips-error .msg-error').hide();
          $this.loadRecruitmentApplicationInfo(1);
          return false;
        }
      });
    },
    main: function () {
      this.doctorSelect();
      this.patientSelect();
      this.loadRecruitmentApplicationInfo(1);
      this.bindSearchAction();
    }
  };
  ApplicationList.main()
});