// pages/user/user.js
const app = getApp()

Page({

  /**
   * isRegistered用于判断用户是否注册；如果没有注册，将显示“注册”button
   * userInfo存储用户信息，从globalData复制
   * hasUserInfo判断用户是否登录；如果没有登陆，就显示“获取用户头像”button
   * canIUse调用接口，判断设备是否支持登录
   */
  data: {
    isRegistered: false,
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },

  //生命周期函数
  onLoad() {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },

  //尝试获得用户信息，将用户信息发送给后端，判断用户是否注册
  getUserInfo(e) {
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
    // 将用户信息post给后端，判断微信用户的信息是否已经注册
    wx.request({
      url: '',
      method: 'POST',
      header: {
        'Content-Type': 'json'
      },
      success: function (res) {

      },
      fail: function () {
        console.log("接口调用失败");
      }
    })
  },

  //用户按下“注册”button后跳转到登录页面
  handleRegister () {
    wx.navigateTo({
      url: "/pages/register/register",
    })
  },

})