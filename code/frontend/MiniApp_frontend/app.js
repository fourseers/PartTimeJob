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
            this.globalData.isRegistered = false;
            wx.showToast({
              title: res.data.message,
              icon: "none"
            })
          }
          else if (res.statusCode === 200) {
            this.globalData.isRegistered = true;
            this.globalData.access_token = res.data.data.access_token;
            this.globalData.expires_in = res.data.data.expires_in;
            this.globalData.refresh_token = res.data.data.refresh_token;
          }
          else{
            this.globalData.isRegistered = false;
          }
        }).catch(err => {
          //console.log(err)
          this.globalData.isRegistered = false;
        })
      }
    })
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
    })
  },
  globalData: {
    userInfo: null,
    userGPS: null,
    isRegistered: false,
    showSendMessage: false,
    showModifySuccess: false,
    host: "http://202.120.40.8:30552",
    access_token: null,
    refresh_token: null,
    token_expires_in: null,
  }
})