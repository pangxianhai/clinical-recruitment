$(function () {
  const RegionSelect = {
    main: function () {
      $('body').on('click', '[item="regionSelectItem"]', function () {
        let $this = $(this);
        let regionName = $this.attr('regionName');
        let provinceId = $this.attr('provinceId');
        let cityId = $this.attr('cityId');
        let districtId = $this.attr('districtId');
        let regionSelectNode = $this.parents('.region-select');
        regionSelectNode.find('[item="choiceText"]').html(regionName);
        regionSelectNode.find('[name="provinceId"]').val(provinceId);
        regionSelectNode.find('[name="cityId"]').val(cityId);
        regionSelectNode.find('[name="districtId"]').val(districtId);
      });
    }
  };
  RegionSelect.main();
});