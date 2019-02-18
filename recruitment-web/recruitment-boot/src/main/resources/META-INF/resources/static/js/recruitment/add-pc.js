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
          alert('验证成功');
        }
      });
    },

    bindCancelRecruitmentBtnAction: function () {
      $('#cancelRecruitmentBtn').on('click', function () {
        window.location.href = '/recruitment/list-pc'
      });
    },

    main: function () {
      this.initTextarea();
      this.bindAddRecruitmentBtnAction();
      this.bindCancelRecruitmentBtnAction();
    }
  };
  Add.main();
});