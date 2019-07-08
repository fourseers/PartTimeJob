// pages/schedule/schedule.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    listData: [
      ["0时", false, false, false, false, false, false, false],
      ["1时", false, false, false, false, false, false, false],
      ["2时", false, false, false, false, false, false, false],
      ["3时", false, false, false, false, false, false, false],
      ["4时", false, false, false, false, false, false, false],
      ["5时", false, false, false, false, false, false, false],
      ["6时", false, false, false, false, false, false, false],
      ["7时", false, false, false, false, false, false, false],
      ["8时", false, false, false, false, false, false, false],
      ["9时", false, false, false, false, false, false, false],
      ["10时", false, false, false, false, false, false, false],
      ["11时", false, false, false, false, false, false, false],
      ["12时", false, false, false, false, false, false, false],
      ["13时", false, false, false, false, false, false, false],
      ["14时", false, false, false, false, false, false, false],
      ["15时", false, false, false, false, false, false, false],
      ["16时", false, false, false, false, false, false, false],
      ["17时", false, false, false, false, false, false, false],
      ["18时", false, false, false, false, false, false, false],
      ["19时", false, false, false, false, false, false, false],
      ["20时", false, false, false, false, false, false, false],
      ["21时", false, false, false, false, false, false, false],
      ["22时", false, false, false, false, false, false, false],
      ["23时", false, false, false, false, false, false, false]
    ],
    pageWidth: 0.0
  },

  onLoad(){
    this.setData({
      pageWidth: wx.getSystemInfoSync().windowWidth
    });
  },

  handleTouchStart(e){
    var newData = this.data.listData;
    var chosenIndex = parseInt(e.changedTouches[0].pageX / (this.data.pageWidth / 8));
    if (e.target.id !== "" && chosenIndex > 0) {
      newData[e.target.id][chosenIndex] = !newData[e.target.id][chosenIndex];
    }
    this.setData({
      listData: newData
    })
  },

  handleTouchMove(e) {
    console.log(e);
  },

  handleTouchEnd(e) {
    console.log(e);
  },
})