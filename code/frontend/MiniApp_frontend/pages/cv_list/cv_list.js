// pages/cv_list/cv_list.js
const app = getApp();
import request from "../../api/request.js";
import { host, cv_list } from "../../api/url.js";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    cv_list: [
      {
        name: "我的简历",
        id: 0
      },
      {
        name: "我的另一个简历",
        id: 1
      }
    ]
  },

  onShow() {
    var req = new request();
    req.getRequest(host + cv_list, null, app.globalData.access_token).then(res => {
      if(res.statusCode === 200) {
        this.setData({
          cv_list: res.data.data
        })
      }
    }).catch(err => {
      console.log(err);
    })
  },

  handleAdd() {
    wx.navigateTo({
      url: "/pages/cv_inform/cv_inform?id=0",
    })
  }

})