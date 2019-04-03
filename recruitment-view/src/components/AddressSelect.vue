<template>
    <van-popup v-model="show" position="bottom">
        <van-area :area-list="areaList" @confirm="confirm" @cancel="cancel"/>
    </van-popup>
</template>

<script>
  import Area from '@/util/Area'

  export default {
    name: 'address-select',
    props: {
      show: Boolean
    },
    data: function () {
      return {
        areaList: Area,
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
