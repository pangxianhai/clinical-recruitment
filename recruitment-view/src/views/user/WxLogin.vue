<template>
    <div class="wx-login">
        <van-cell-group>
            <van-cell icon="user-circle-o" title="成为受试者" is-link
                      @click="toRegister('/patient/register')">
            </van-cell>
            <van-cell icon="contact" title="成为推荐人" is-link
                      @click="toRegister('/reference/register')"></van-cell>
            <van-cell icon="friends-o" title="成为研究员" is-link
                      @click="toRegister('/researcher/register')"></van-cell>
        </van-cell-group>
    </div>
</template>

<style>
    .wx-login .van-icon-user-circle-o {
        color: rgba(204, 6, 223, 0.63);
    }

    .wx-login .van-icon-contact {
        color: rgba(119, 9, 222, 0.72);
    }

    .wx-login .van-icon-friends-o {
        color: rgba(238, 93, 13, 0.81);
    }
</style>

<script>
  import {StringUtil} from '@/util/Util';
  import UserApi from '@/api/UserApi';
  import {ApplicationAction} from '@/constants/Global';

  export default {
    data: function () {
      return {
        wxLoginRes: {}
      }
    },
    created: function () {
      this.$toast.loading({
        duration: 0,
        mask: true,
        forbidClick: true,
        loadingType: 'spinner',
        message: '微信登录中...'
      });
      let code = this.$route.query.code;
      if (StringUtil.isEmpty(code)) {
        this.$toast.clear();
        return;
      }
      UserApi.wxLogin(code).then(wxLoginRes => {
        this.$toast.clear();
        this.wxLoginRes = wxLoginRes;
        if (typeof wxLoginRes.userId !== 'undefined') {
          UserApi.saveLoginInfo(wxLoginRes);
        }
        if (ApplicationAction.ATTEND === this.$route.query.action) {
          if (wxLoginRes.hasPatient) {
            this.onGoBack();
          } else {
            this.toRegister('/patient/register');
          }
        } else if (ApplicationAction.REFERENCE === this.$route.query.action) {
          if (wxLoginRes.hasReference) {
            this.onGoBack();
          } else {
            this.toRegister('/reference/register');
          }
        } else if (ApplicationAction.RESEARCHER === this.$route.query.action) {
          if (wxLoginRes.hasResearcher) {
            this.onGoBack();
          } else {
            this.toRegister('/researcher/register');
          }
        } else {
          this.onGoBack();
        }
      })
    },
    methods: {
      toRegister: function (path) {
        window.console.log(this.wxLoginRes);
        this.$router.push({
          path: path,
          query: {
            redirectURL: this.$route.query.redirectURL,
            openId: this.wxLoginRes.openId,
            nickname: this.wxLoginRes.nickname
          },
        }, function () {

        });
      },
      onGoBack: function () {
        let redirectURL = this.$route.query.redirectURL;
        if (typeof redirectURL === 'undefined' || redirectURL.length <= 0) {
          redirectURL = '/recruitment/list';
        }
        this.$router.push({path: String(redirectURL)}, function () {

        });
      },
    }
  }
</script>