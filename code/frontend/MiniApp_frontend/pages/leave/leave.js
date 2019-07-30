// pages/leave/leave.js
var util = require("../../utils/util.js")

Page({

  /**
   * 页面的初始数据
   * begin_choose: 可请假的时间的开始
   * end_choose: 可请假的时间的结束
   * begin_date: 选择的开始请假时间
   * end_date: 选择的结束请假时间
   */
  data: {
    begin_choose: 0,
    end_choose: 0,
    begin_date: 1970-1-1,
    end_date: 1970-1-1
  },

  onShow() {
    var begin_choose = util.formatBeginDate(new Date());
    var end_choose = util.formatEndDate(new Date());
    console.log(begin_choose)
    this.setData({
      begin_choose: begin_choose,
      end_choose: end_choose,
      begin_date: begin_choose,
      end_date: begin_choose
    })
  }

})