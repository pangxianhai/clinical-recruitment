$(function () {
  const RecruitmentUpdate = {
    initResearch: function () {
      $('.research-center .controls').each(function () {
        let $this = $(this);
        let centerIdNode = $this.find('[name="centerId"]');
        $.ajax({
          url: '/region/selectPc',
          type: 'get',
          data: {
            provinceId: centerIdNode.attr('provinceId'),
            cityId: centerIdNode.attr('cityId'),
            districtId: centerIdNode.attr('districtId')
          },
          success: function (html) {
            $this.find('.region-select').html(html);
          }
        })
      });
    },
    bindUpdateRecruitmentBtnAction: function () {
      $('#updateRecruitmentForm').validate({
        success: function () {
          const data = {};
          let centerListName = ['centerId', 'centerName', 'provinceId',
            'cityId', 'districtId'];
          $.each($('#updateRecruitmentForm').serializeArray(),
              function (i, item) {
                if (centerListName.indexOf(item.name) === -1) {
                  data[item.name] = item.value;
                }
              });
          let centerList = [];
          $('.research-center .controls').each(function () {
            let center = {};
            let $this = $(this);
            center.name = $this.find('[name="centerName"]').val();
            center.provinceId = $this.find('[name="provinceId"]').val();
            center.cityId = $this.find('[name="cityId"]').val();
            center.districtId = $this.find('[name="districtId"]').val();
            center.centerId = $this.find('[name="centerId"]').val();
            centerList.push(center);
          });
          data.researchCenterList = centerList;
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
    bindResearchCenterAction: function () {
      let researchCenterNode = $('.research-center');
      researchCenterNode.on('click', '.btn-primary', function () {
        let controlsNode = $(this).parent('.controls');
        let centerIdNode = controlsNode.find('[name="centerId"]');
        let centerId = centerIdNode.val();
        centerIdNode.val('');
        let centerHtml = controlsNode.prop("outerHTML");
        researchCenterNode.append(centerHtml);
        centerIdNode.val(centerId);
      });
      researchCenterNode.on('click', '.btn-danger', function () {
        if (researchCenterNode.find('.controls').size() <= 1) {
          $.alert('请至少保留一个研究中心');
          return;
        }
        $(this).parent('.controls').remove();
      });
    },
    main: function () {
      this.bindUpdateRecruitmentBtnAction();
      this.initResearch();
      this.bindResearchCenterAction();
    }
  };
  RecruitmentUpdate.main();
});