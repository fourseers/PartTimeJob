// pages/schedule/schedule.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    timeList: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23],
    schedule: [
      [false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false],
      [false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false],
      [false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false],
      [false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false],
      [false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false],
      [false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false],
      [false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false]
    ],
    week: [0, 1, 2, 3, 4, 5, 6],
    pageWidth: 0.0
  },

  onLoad(){
    this.setData({
      pageWidth: wx.getSystemInfoSync().windowWidth
    });
  },

  handleScroll(e) {
    console.log(e);
  },

  handleTap(e) {
    console.log(e);
  }

/*
  handleTouchStart(e){
    var newData = this.data.listData;
    var chosenIndex = parseInt(e.changedTouches[0].pageX / (this.data.pageWidth / 8));
    //console.log(e.target.id !== "" && chosenIndex > 0);
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

*/
})