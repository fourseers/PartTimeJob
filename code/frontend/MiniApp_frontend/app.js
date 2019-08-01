//app.js
import request from "./api/request.js"
import { login } from "./api/url.js"

App({
  onLaunch: function () {

    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    this.login();

    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
        // 获取用户地址
        // 可以在app.json中修改弹出的对话框
        if (!res.authSetting["scope.userLocation"]) {
          wx.getLocation({
            type: "gcj02",
            success: res => {
              this.globalData.userGPS = res;
            }
          });
        }
      }
    });
    /*
    // 显示欢迎页面2秒后跳转到"我"页面
    setTimeout(function () {
      wx.reLaunch({
        url: "/pages/user/user",
      })
    }, 2000)
    */
  },
  globalData: {
    userInfo: null,
    userGPS: null,
    is_registered: false,
    showSendMessage: false,
    showModifySuccess: false,
    host: "http://202.120.40.8:30552",
    access_token: null,
    refresh_token: null,
    token_expires_in: null,

    city: null,
    code: null,
  },

  // use app.login() from other .js file to refresh the token
  login() {
    wx.login({
      success: res => {
        var req = new request();
        //console.log(res);
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        var postData = {
          "token": res.code
        }
        req.postRequest(this.globalData.host + login, JSON.stringify(postData)).then(res => {
          if (res.statusCode === 400) {
            this.globalData.is_registered = false;
          }
          else if (res.statusCode === 200) {
            this.globalData.is_registered = true;
            this.globalData.access_token = res.data.data.access_token;
            this.globalData.expires_in = res.data.data.expires_in;
            this.globalData.refresh_token = res.data.data.refresh_token;
          }
          else {
            this.globalData.is_registered = false;
          }
        }).catch(err => {
          //console.log(err)
          this.globalData.is_registered = false;
        })
      }
    });
  }
})