<template>
    <van-popup v-model="show" position="bottom">
        <van-area :area-list="areaList" @confirm="confirm" @cancel="cancel"/>
    </van-popup>
</template>

<script>
  import AreaData from '@/util/AreaData'

  export default {
    name: 'address-select',
    props: {
      show: Boolean
    },
    data: function () {
      return {
        areaList: AreaData,
        name: 'address-select',
      }
    },
    created: function () {
    },
    methods: {
      confirm: function (data) {
        this.$emit('confirm', data);
      },
      cancel: function () {
        this.$emit('cancel');
      }
    }
  }
</script>
