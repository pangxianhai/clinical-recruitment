<template>
    <div class="recruitment-list">
        <van-nav-bar title="招募大厅">
        </van-nav-bar>
        <van-swipe :autoplay="3000">
            <van-swipe-item>
                <img width="100%" src="../../assets/banner1.png"/>
            </van-swipe-item>
            <van-swipe-item>
                <img width="100%" src="../../assets/banner2.png"/>
            </van-swipe-item>
        </van-swipe>
        <van-search placeholder="输入标题，登记编号，适应症状等搜索" v-model="value"/>
        <van-row style="padding-bottom: 10px">
            <van-col class="title" span="8">智能推荐</van-col>
            <van-col class="choice" span="7" offset="1">所有疾病类型
                <van-icon name="arrow-down"/>
            </van-col>
            <van-col class="choice" span="5" offset="1">所有城市
                <van-icon name="arrow-down" @click="showAddress= !showAddress"/>
            </van-col>
        </van-row>
        <address-select :show="showAddress"></address-select>
        <van-list
            v-model="loading"
            :finished="finished"
            finished-text="没有更多了"
            @load="onLoad">
            <van-panel class="recruitment-panel" v-for="(item, index) in list" :title="item.title"
                       :key="index">
                <van-row type="flex">
                    <van-col span="5">登记编号:</van-col>
                    <van-col span="8">A0001234</van-col>
                    <van-col span="5">实验分期:</van-col>
                    <van-col span="3">III期</van-col>
                </van-row>
                <van-row type="flex">
                    <van-col span="5">药物名称:</van-col>
                    <van-col span="19">蟑螂药老鼠蟑螂鼠药蟑螂药老鼠药</van-col>
                </van-row>
                <van-row type="flex">
                    <van-col span="5">招募人数:</van-col>
                    <van-col span="8">3人</van-col>
                    <van-col span="5">招募状态:</van-col>
                    <van-col span="3">进行中</van-col>
                </van-row>
                <van-row type="flex">
                    <van-col span="5">适应症状:</van-col>
                    <van-col span="19">蟑螂药老鼠老鼠蟑螂药老鼠药</van-col>
                </van-row>
                <van-row type="flex" justify="end">
                    <van-col>
                        <van-button type="info" size="small">联系我们</van-button>
                    </van-col>
                    <van-col style="margin-left: 15px">
                        <van-button type="warning" size="small">我要参加</van-button>
                    </van-col>
                </van-row>
            </van-panel>
        </van-list>
        <van-tabbar>
            <van-tabbar-item icon="more-o">任务列表</van-tabbar-item>
            <van-tabbar-item icon="records">申请记录</van-tabbar-item>
            <van-tabbar-item icon="user-o">我</van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<style lang="less">
    .recruitment-list .van-field__right-icon .van-icon {
        color: #1989fa;
    }

    .recruitment-list .van-row .title {
        padding-left: 12px;
        font-size: 13px;
    }

    .recruitment-list .van-row .choice {
        font-size: 12px;
    }

    .recruitment-list .van-row .choice .van-icon {
        color: #1989fa;
    }

    .van-popup {
        width: 100%;
        transform: none;
        position: inherit;
    }

    .recruitment-panel {
        padding: 5px 10px 8px 10px;
    }

    .recruitment-panel .van-panel__header {
        padding: 0;
    }

    .recruitment-panel .van-col {
        font-size: 12px;
    }

    .recruitment-panel .van-panel__content .van-row {
        line-height: 22px;
        color: #888;
    }
</style>

<script>
  export default {
    data: function () {
      return {
        active: 0,
        sms: '',
        value: '',
        showAddress: false,
        list: [],
        loading: false,
        finished: false
      }
    },
    methods: {
      onNavClick(index) {
        this.mainActiveIndex = index;
      },
      onItemClick(data) {
        this.activeId = data.id;
      },
      showTree: function () {
        this.showList = !this.showList;
      },
      onLoad: function () {
        // 异步更新数据
        setTimeout(() => {
          for (let i = 0; i < 10; i++) {
            const item = {};
            item.title = '【流口】水的减肥徕卡健身房拉开睡觉地方是凉快地方就是df';
            this.list.push(item);
          }
          // 加载状态结束
          this.loading = false;

          // 数据全部加载完成
          if (this.list.length >= 40) {
            this.finished = true;
          }
        }, 500);
      }
    }
  }
</script>
