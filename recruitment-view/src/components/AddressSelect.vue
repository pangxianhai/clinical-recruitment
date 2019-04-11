<template>
    <van-popup v-model="show" position="bottom">
        <van-area :area-list="areaList" @confirm="confirm" @cancel="cancel"/>
    </van-popup>
</template>

<script>
  import {Area,Popup} from 'vant';
  import AreaData from '@/util/AreaData'

  export default {
    name: 'address-select',
    props: {
      show: Boolean
    },
    data: function () {
      return {
        areaList: AreaData,
      }
    },
    components: {
      [Area.name]: Area,
      [Popup.name]: Popup,
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
