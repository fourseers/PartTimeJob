// pages/wallet/wallet.js
const app = getApp()
import request from "../../api/request.js"
import { host, balance } from "../../api/url.js"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {
    },
    balance: 0.0,
    cells: [
      {
        title: "支取记录",
        url: ""
      },
      {
        title: "工作记录",
        url: "/pages/billing_page/billing_page"
      },
    ]
  },

  onShow() {
    //得到用户信息
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
    // console.log(this.data.userInfo)
    // 得到用户余额
    var req = new request();
    req.getRequest(host + balance, null, app.globalData.access_token).then(res => {
      if (res.statusCode === 200) {
        this.setData({
          balance: res.data.data,
        })
      }
    }).catch(err => {

    })
  }
  
})