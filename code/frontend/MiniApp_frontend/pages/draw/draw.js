// pages/draw/draw.js
const { $Toast } = require('../../dist/base/index');
var util = require("../../utils/util.js")
const app = getApp();
import request from "../../api/request.js"
import { host, draw_history } from "../../api/url.js"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    draw_count: 0,
    total_hits: 9999,
    draw_history: [
      {
        payment: 100,
        method: "微信",
        time: "2019-9-5",
        meta: "1250129751920we"
      }
    ]
  },

  onReady() {
    this.refresh();
  },

  getDrawHistory() {
    if (this.data.draw_count >= this.data.total_hits) {
      return;
    }
    var req = new request();
    req.getRequest(host + draw_history,{pageCount: this.data.draw_count}, app.globalData.access_token).then(res => {
      if (res.statusCode === 200) {
        var content = res.data.data.content;
        var new_history = this.data.draw_history;
        var formal_length = new_history.length;
        for (var i in content) {
          var his = {}
          his.payment = content[i].payment;
          his.method = content[i].method;
          his.time = util.formatDate(content[i].create_time);
          his.meta = content[i].meta;
          new_history[parseInt(formal_length) + parseInt(i)] = his;
        }
        this.setData({
          draw_history: new_history,
          draw_count: this.data.draw_count + 15,
          total_hits: res.data.data.total_hits
        })
      }
      
    }).catch(err => {
      
    })
  },

  refresh() {
    this.setData({
      draw_count: 0,
      total_hits: 9999,
      draw_history: []
    })
    this.getDrawHistory();
  },

  // 上拉刷新
  onPullDownRefresh() {
    //console.log("onRefresh");
    this.refresh();
  },

  // 触底加载更多
  onReachBottom() {
    //console.log("onBottom");
    this.setData({
      isLoading: true,
    })
    this.getUserJob();
    this.setData({
      isLoading: false,
    })
  }

})