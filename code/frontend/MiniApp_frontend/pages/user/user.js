// pages/user/user.js
import request from "../../api/request.js"
const app = getApp()
const { $Toast } = require("../../dist/base/index");

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
    canIUse: wx.canIUse("button.open-type.getUserInfo")
  },

  //生命周期函数
  onShow() {
    if (app.globalData.showSendMessage) {
      app.globalData.showSendMessage = false;
      this.handleRegisterSuccess();
    }
    this.setData({
      isRegistered: app.globalData.isRegistered
    })
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

  //尝试获得用户信息
  getUserInfo(e) {
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },

  //用户按下“注册”button后跳转到登录页面
  handleRegister () {
    wx.navigateTo({
      url: "/pages/register/register",
    })
  },

  handleRegisterSuccess() {
    $Toast({
      content: '注册成功',
      type: 'success'
    });
  },

  handleSchedule() {
    wx.navigateTo({
      url: "/pages/schedule/schedule",
    })
  },

  handleInform() {
    wx.navigateTo({
      url: "/pages/user_inform/user_inform",
    })
  },

  testReq() {
    console.log(app.globalData.access_token)
  },

  handleTapGrid(e) {
    switch (e.currentTarget.id){
      case "个人信息":
        this.handleInform();
        break;
      case "日程":
        this.handleSchedule();
        break;
      default:
        wx.showToast({
          title: "敬请期待",
          icon: "none",
        });
        this.testReq();
        break;
    }
  }

})