// pages/check_inform/check_inform.js
const app = getApp();
const { $Toast } = require("../../dist/base/index");
import request from "../../api/request.js"
import { host, job_detail, check_status, checkin, checkout } from "../../api/url.js"
var job_id = 0;
var default_radius = 100;
var default_check_avail = 15; //minutes

Page({

  /**
   * 页面的初始数据
   * 地图上要有如下标记：
   * 1. 用户所在位置的标记
   * 2. 打卡上班地点的标记
   */
  data: {
    longitude: 0.0,
    latitude: 0.0,
    des_longitude: 0.0,
    des_latitude: 0.0,
    markers: [],
    circles: [],
    job_name: "",
    begin_check_time: "",
    end_check_time: "",
    isCheckin: false,
    isCheckout: false,
    address: "",
    action_visible: false,
    actions: [
      {
        name: "上班打卡"
      },
      {
        name: "下班打卡"
      },
      {
        name: "请假"
      }
    ]
  },

  // onLoad，保存从check页面传来的job的id
  onLoad(options){
    //console.log(options);
    job_id = options.id;
  },

  /*
   * onShow,使用在onLoad中保持的id来向后端发送请求，得到以下数据
   *    1. 工作地点经纬度
   *    2. 打卡允许半径（暂无）
   *    3. 工作名
   *    4. 允许打卡时间
   */
  onShow() {
    var req = new request();

    req.getRequest(host + check_status, {
      job_id: job_id
    }, app.globalData.access_token).then(res => {
      if (res.statusCode === 200) {
        var data = this.data.data;
        this.setData({
          begin_check_time: data.expected_checkin,
          end_check_time: data.expected_checkout,
          isCheckedin: data.checkin !== null,
          isCheckedout: data.checkout !== null
        })
      }
    }).catch(err => {
      console.log(err)
    })
    //console.log("test");
    req.getRequest(host + job_detail + job_id, null, app.globalData.access_token).then(res => {
      if(res.statusCode === 200) {
        var info = res.data.data;
        this.setData({
          job_name: info.job_name,
          address: info.shop.address,
        })
        wx.getLocation({
          type: "gcj02",
          success: res => {
            this.setData({
              longitude: res.longitude,
              latitude: res.latitude,
              des_longitude: info.shop.longitude,
              des_latitude: info.shop.latitude,
              markers: [{
                id: 0,
                latitude: res.latitude,
                longitude: res.longitude,
                name: '我的位置',
              }, {
                id: 1,
                latitude: info.shop.latitude,
                longitude: info.shop.longitude,
                name: '商家位置',
              }],
              circles: [{
                radius: default_radius,
                latitude: info.shop.latitude,
                longitude: info.shop.longitude,
                fillColor: "#fdcb6e77",
                color: "#fdcb6e77",
              }]
            })
          }
        });
      }
      else if (res.statusCode === 400) {

      }
    }).catch(err => {
      console.log(err)
    });
  },

  handleTransport(e) {
    this.setData({
      longitude: this.data.des_longitude,
      latitude: this.data.des_latitude
    })
  },

  handleClick() {
    this.setData({
      action_visible: true,
    })
  },

  handleCancel() {
    this.setData({
      action_visible: false,
    })
  },

  handleClickItem({ detail }) {
    if (detail.index === 0){
      // console.log("上班打卡");
      if (this.data.isCheckedin) {
        $Toast({
          content: "您已完成上班打卡",
          type: "warning"
        })
      }
      else {
        checkin();
      }
    }
    else if (detail.index === 1){
      // console.log("下班打卡");
      if (this.data.isCheckedout) {
        $Toast({
          content: "您已完成下班打卡",
          type: "warning"
        })
      }
      else {
        checkout();
      }
    }
    else if (detail.index === 2){
      //console.log("请假")
      $Toast({
        content: "敬请期待",
        type: "warning"
      })
      /*
      wx.navigateTo({
        url: "/pages/leave/leave",
      })
      */
    }
    this.setData({
      action_visible: false,
    })
  },

  checkin() {
    //console.log(e);
    var req = new request();

    req.postRequest(host + checkin, 
    {
      "job_id": job_id,
      "latitude": this.data.latitude,
      "longitude": this.data.longitude
    }, app.globalData.access_token).then(res => {
      if(res.statusCode === 200){
        app.globalData.showSendMessage = true;
        wx.navigateBack({
          
        })
      }
      if(res.statusCode === 400){
        $Toast({
          content: res.data.message,
          type: 'error'
        });
      }
    }).catch(err => {

    })
  },

  checkout() {
    //console.log(e);
    var req = new request();

    req.postRequest(host + checkout, 
    {
      "job_id": job_id,
      "latitude": this.data.latitude,
      "longitude": this.data.longitude
    }, app.globalData.access_token).then(res => {
      if(res.statusCode === 200){
        app.globalData.showSendMessage = true;
        wx.navigateBack({
          
        })
      }
      if(res.statusCode === 400){
        $Toast({
          content: res.data.message,
          type: 'error'
        });
      }
    }).catch(err => {

    })
  }

})