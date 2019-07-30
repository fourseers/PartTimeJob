// pages/check/check.
const { $Toast } = require("../../dist/base/index");
var util = require("../../utils/util.js")
const app = getApp();
import request from "../../api/request.js"
import { host, schedule } from "../../api/url.js"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    calendar_config: {
      disablePastDay: true,
      defaultDay: '2019-7-15',
    },
    checks: [
      {
        title: "搬砖工打卡",
        checkinTime: "8:30",
        checkoutTime: "12:30",
        canCheck: true,
        id: 1,
      },
    ],
  },

  // onShow的时候向后端请求当前的工作列表
  onShow() {
    if (app.globalData.showSendMessage) {
      app.globalData.showSendMessage = false;
      this.handleCheckSuccess();
    }

    var req = new request();
    var curr_date = new Date();
    console.log(util.format)
    req.getRequest
  },

  afterTapDay(e) {
    console.log('afterTapDay', e.detail);
  },

  whenChangeMonth(e) {
    //console.log('whenChangeMonth', e.detail);
  },

  onTapDay(e) {
    //console.log('onTapDay', e.detail);
  },

  afterCalendarRender(e) {
    //console.log('afterCalendarRender', e);
  },

  //显示注册成功的toast
  handleCheckSuccess() {
    $Toast({
      content: '打卡成功',
      type: 'success'
    });
  },

})