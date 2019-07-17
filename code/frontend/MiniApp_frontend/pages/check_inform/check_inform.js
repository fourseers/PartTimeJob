// pages/check_inform/check_inform.js
const app = getApp();

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
    circles: []
  },

  onShow() {
    wx.getLocation({
      type: "gcj02",
      success: res => {
        this.setData({
          longitude: res.longitude,
          latitude: res.latitude,
          des_longitude: res.longitude + 0.005,
          des_latitude: res.latitude + 0.005,
          markers: [{
            id: 0,
            latitude: res.latitude,
            longitude: res.longitude,
            name: '您的位置',
          },{
            id: 1,
            latitude: res.latitude+0.005,
            longitude: res.longitude+0.005,
            name: 'TA的位置',
          }],
          circles: [{
            radius: 100,
            latitude: res.latitude+0.005,
            longitude: res.longitude+0.005,
            fillColor: "#fdcb6e77",
            color: "#fdcb6e77",
          }]
        })
      }
    });
  },

  handleTransport(e) {
    this.setData({
      longitude: this.data.des_longitude,
      latitude: this.data.des_latitude
    })
  },

  handleCheck(e) {
    //console.log(e);
  }

})