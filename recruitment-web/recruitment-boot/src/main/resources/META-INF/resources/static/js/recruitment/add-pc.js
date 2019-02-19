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

    bindAddRecruitmentBtnAction: function () {
      $('#addRecruitmentForm').validate({
        success: function () {
          const data = {};
          $.each($('#addRecruitmentForm').serializeArray(), function (i, item) {
            data[item.name] = item.value;
          });
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

    main: function () {
      // this.initTextarea();
      this.bindAddRecruitmentBtnAction();
      this.bindCancelRecruitmentBtnAction();
    }
  };
  Add.main();
});