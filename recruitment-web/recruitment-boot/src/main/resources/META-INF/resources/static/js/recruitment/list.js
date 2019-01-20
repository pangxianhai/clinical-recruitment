$(function () {
  const recruitment = {
    initIndicationPicker: function () {
      $('#indicationPicker').picker({
        cols: [
          {
            textAlign: 'center',
            values: ['iPhone 4', 'iPhone 4S', 'iPhone 5', 'iPhone 5S',
              'iPhone 6', 'iPhone 6 Plus', 'iPad 2', 'iPad Retina', 'iPad Air',
              'iPad mini', 'iPad mini 2', 'iPad mini 3']
          }
        ]
      });
    },
    initAddressPicker: function () {
      $('#addressPicker').picker({
        cols: [
          {
            textAlign: 'center',
            values: ['上海', '北京']
          }, {
            textAlign: 'center',
            values: ['成都', '南充']
          }
        ]
      });
    },
    main: function () {
      $.init();
      this.initIndicationPicker();
      this.initAddressPicker();
    }
  };
  recruitment.main();
});