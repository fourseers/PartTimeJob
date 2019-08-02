// pages/user/user.js
import request from "../../api/request.js"
const app = getApp()
const { $Toast } = require("../../dist/base/index");
const { $Message } = require('../../dist/base/index');

Page({

  /**
   * is_registered用于判断用户是否注册；如果没有注册，将显示“注册”button
   * userInfo存储用户信息，从globalData复制
   * hasUserInfo判断用户是否登录；如果没有登陆，就显示“获取用户头像”button
   * canIUse调用接口，判断设备是否支持登录
   * cells存储在“我”页面中所有可跳转到的页面信息
   */
  data: {
    is_registered: false,
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse("button.open-type.getUserInfo"),
    cells: [
      {
        title: "个人信息",
        url: "/pages/user_inform/user_inform",
        type: "businesscard",
      },
      {
        title: "日程管理",
        url: "/pages/timeline/timeline",
        type: "activity",
      },
      {
        title: "简历",
        url: "/pages/cv_list/cv_list",
        src: "/assets/icons/resume.png"
      },
      {
        title: "应聘记录",
        url: "/pages/apply_detail/apply_detail",
        src: "/assets/icons/job.png"
      },
      {
        title: "工资",
        url: "/pages/billing_page/billing_page",
        src: "/assets/icons/money.png"
      },
      {
        title: "关于",
        url: "/pages/index/index",
        src: "/assets/icons/about.png"
      }
    ]
  },

  // 首次加载uesr页面的时候向用户显示登陆成功message
  onLoad() {
    if (app.globalData.is_registered) {
      this.setData({
        is_registered: app.globalData.is_registered
      });
      $Message({
        content: '登录成功',
        type: 'success'
      });
    }
    else {
      $Message({
        content: "您还没有注册",
        type: "warning",
      });
    }
  },

  //生命周期函数
  onShow() {
    //判断是否从注册页面或用户信息修改页面返回。若是，显示相应的toast
    if (app.globalData.showSendMessage) {
      app.globalData.showSendMessage = false;
      this.handleRegisterSuccess();
    }
    if (app.globalData.showModifySuccess) {
      app.globalData.showModifySuccess = false;
      this.handleModifySuccess();
    }
    //从globalData获取是否已注册的信息，从而决定要不要显示注册button
    if(app.globalData.is_registered){
      this.setData({
        is_registered: app.globalData.is_registered
      });
    }
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

  //显示注册成功的toast
  handleRegisterSuccess() {
    $Toast({
      content: '注册成功',
      type: 'success'
    });
  },

  //显示用户信息修改成功的toast
  handleModifySuccess() {
    $Toast({
      content: "修改信息成功",
      type: "success"
    })
  },

})