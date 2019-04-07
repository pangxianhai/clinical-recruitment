<template>
    <div>
        <van-nav-bar title="联系我们" left-arrow @click-left="onGoBack"></van-nav-bar>
        <van-swipe>
            <van-swipe-item>
                <img width="100%" src="@/assets/contactUs.png"/>
            </van-swipe-item>
        </van-swipe>
        <van-collapse v-model="activeNames">
            <van-collapse-item title="爱＆募平台" name="1">
                爱＆募平台是由成都艾特睿医药科技有限公司发起，致力于通过互联网技术传递新药临床试验信息资源，
                帮助患者获得新药研发带来的受益，促进我国医药行业创新药物的发展。爱＆募平台传递是爱心、
                专业介绍新药药物临床试验，并且对于长期困扰患者的罕见疾病，架起患者与专业医生之间的桥梁。
            </van-collapse-item>
            <van-collapse-item title="成都艾特睿医药科技有限公司" name="2">
                成都艾特睿医药科技有限公司（以下简称“艾特睿医药”）是第三方专业的临床研究现场管理组织（SMO）。
                艾特睿医药秉承着务实创新的理念，力求将中国的SMO服务专业化，即肿瘤试验CRC、I期试验CRC、专业适应症CRC。
                艾特睿医药与国内外多家优秀的创新药研发公司达成长期良好的合作关系，并且在国内与多家药物临床试验长期、
                稳定的合作。艾特睿医药在北京、上海、成都、广州、长沙、哈尔滨、杭州、合肥、苏州等多个城市建立办事处。
                艾特睿医药立志为中国新药临床试验保驾护航，做临床试验研究者合格、优秀的助手。
            </van-collapse-item>
            <van-collapse-item title="联系地址" name="3">
                四川省成都市高新区天府大道北段1480号1栋1-5层
            </van-collapse-item>
        </van-collapse>

    </div>
</template>

<script>
  export default {
    data: function () {
      return {
        activeNames: ['1', '2', '3']
      }
    },
    methods: {
      onGoBack: function () {
        let redirectURL = this.$route.query.redirectURL;
        this.$router.push({path: redirectURL});
      }
    }
  }
</script>
