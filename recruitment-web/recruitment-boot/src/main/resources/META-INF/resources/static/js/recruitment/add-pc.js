$(function () {
  const Add = {
    initTextarea: function () {
      $('#introductionArea').editor({
        toolbars: [['Source', 'Undo', 'Redo', 'Bold']]
      });
      $('#treatmentPlanArea').editor({
        toolbars: [['Source', 'Undo', 'Redo', 'Bold']]
      });
      $('#screeningStandardArea').editor({
        toolbars: [['Source', 'Undo', 'Redo', 'Bold']]
      });
      $('#entryCriteriaArea').editor({
        toolbars: [['Source', 'Undo', 'Redo', 'Bold']]
      });
      $('#patientRightsArea').editor({
        toolbars: [['Source', 'Undo', 'Redo', 'Bold']]
      });
    },
    bindTextareaAutoHeight: function () {
      $('#addRecruitmentForm textarea').on('keyup', function () {
        this.style.height = 'auto';
        this.style.height = this.scrollHeight + "px";
      });
    },
    bindAddRecruitmentBtnAction: function () {
      $('#addRecruitmentForm').validate({
        success: function () {
          const data = {};
          try {
            let centerListName = ['centerName', 'provinceId', 'cityId',
              'districtId'];
            $.each($('#addRecruitmentForm').serializeArray(),
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
              centerList.push(center);
            });
            data.researchCenterList = centerList;
          } catch (e) {
            console.error(e);
          }
          Ajax.post('/recruitment/add.json', data, function (result) {
            if (result) {
              $.alert('添加成功');
              setTimeout(function () {
                window.location.href = '/recruitment/list-pc';
              }, 2000);
            } else {
              $.alert('添加失败');
            }
          });
          return false;
        }
      });
    },

    bindCancelRecruitmentBtnAction: function () {
      $('#cancelRecruitmentBtn').on('click', function () {
        window.location.href = '/recruitment/list-pc'
      });
    },

    bindResearchCenterAction: function () {
      let researchCenterNode = $('.research-center');
      researchCenterNode.on('click', '.btn-primary', function () {
        let centerHtml = $(this).parent('.controls').prop("outerHTML");
        researchCenterNode.append(centerHtml);
      });
      researchCenterNode.on('click', '.btn-danger', function () {
        if (researchCenterNode.find('.controls').size() <= 1) {
          $.alert('请至少保留一个研究中心');
          return;
        }
        $(this).parent('.controls').remove();
      });
    },
    bindResearchCenterSelectAction: function () {
      let researchCenterNode = $('.research-center');
      researchCenterNode.on('click', '[item="regionSelectItem"]', function () {
        let $this = $(this);
        let regionName = $this.attr('regionName');
        let provinceId = $this.attr('provinceId');
        let cityId = $this.attr('cityId');
        let districtId = $this.attr('districtId');
        let controlsNode = $this.parents('.controls');
        controlsNode.find('[item="choiceText"]').html(regionName);
        controlsNode.find('[name="provinceId"]').val(provinceId);
        controlsNode.find('[name="cityId"]').val(cityId);
        controlsNode.find('[name="districtId"]').val(districtId);
      });
    },
    main: function () {
      // this.initTextarea();
      this.bindTextareaAutoHeight();
      this.bindAddRecruitmentBtnAction();
      this.bindCancelRecruitmentBtnAction();
      this.bindResearchCenterAction();
      this.bindResearchCenterSelectAction();
    }
  };
  Add.main();
});