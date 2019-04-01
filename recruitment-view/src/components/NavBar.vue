<template>
    <div>
        <van-nav-bar :title="title" :left-text="leftText" :right-text="rightText">
        </van-nav-bar>
    </div>
</template>

<script>
  export default {
    name: 'nav-bar',
    props: {
      title: String,
      leftText: String,
      rightText: String
    }
  }
</script>
