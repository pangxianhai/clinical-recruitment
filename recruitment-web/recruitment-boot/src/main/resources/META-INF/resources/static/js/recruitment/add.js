$(function () {
  const Add = {
    toNextAction: function () {
      $('#open-introduction').on('click', function () {
        $.popup('.popup-introduction');
      });
      $('#open-treatment-plan').on('click', function () {
        $.popup('.popup-treatment-plan');
      });
      $('#open-screening-standard').on('click', function () {
        $.popup('.popup-screening-standard');
      });
      $('#open-entry-criteria').on('click', function () {
        $.popup('.popup-entry-criteria');
      });
      $('#open-patient-rights').on('click', function () {
        $.popup('.popup-patient-rights');
      });
      $('#open-research-center').on('click', function () {
        $.popup('.popup-research-center');
      });
    },
    choiceAddressAction: function (centerNodeId) {
      $('#' + centerNodeId + ' [item="choice-center-address"]').cityPicker({
        toolbarTemplate: '<header class="bar bar-nav"><button class="button button-link pull-right close-picker">确定</button><h1 class="title">选择地址</h1></header>'
      });
    },
    bindAddResearchCenterAction: function () {
      $('.popup-research-center').on('click', '[item="add-center-button"]',
          () => {
            const centerSize = $('[item="choice-center-address"]').size();
            const centerNodeId = "center-node-" + centerSize;
            const html = '<li id="' + centerNodeId + '">' +
                $('#center-node').html() + '</li>';
            $('.research-center-list').append(html);
            this.choiceAddressAction(centerNodeId);
          });
    },
    bindDeleteResearchCenterAction: function () {
      $('.popup-research-center').on('click', '[item="delete-center-button"]',
          function () {
            const nodeSize = $('.popup-research-center li').size();
            if (1 === nodeSize) {
              $.alert('必须保留一个研究中心');
            } else {
              const liNode = $(this).parents("li");
              liNode.remove();
            }
          });
    },
    bindAddSubmitAction: function () {
      $('#addSubmit').on('click', function () {
        const data = {};
        const researchCenterNameList = [];
        const researchCenterAddressList = [];
        const researchCenterList = [];
        $.each($('#recruitmentAddForm').serializeArray(), function (i, item) {
          if (item.name === 'researchCenterName') {
            researchCenterNameList.push(item.value);
          } else if (item.name === 'researchCenterAddress') {
            researchCenterAddressList.push(item.value);
          } else {
            data[item.name] = item.value;
          }
        });
        $.each(researchCenterNameList, function (i, item) {
          const researchCenter = {};
          researchCenter.name = item;
          researchCenter.address = researchCenterAddressList[i];
          researchCenterList.push(researchCenter);
        });
        data.researchCenterList = researchCenterList;
        Ajax.post('/recruitment/add.json', data, function (result) {
          if (result) {
            $.alert('添加成功');
            window.location.href = '/recruitment/list';
          } else {
            $.alert('添加失败');
          }
        });
      })
    },
    main: function () {
      $.init();
      this.toNextAction();
      this.choiceAddressAction("center-node");
      this.bindAddResearchCenterAction();
      this.bindDeleteResearchCenterAction();
      this.bindAddSubmitAction();
    }
  };
  Add.main();
});