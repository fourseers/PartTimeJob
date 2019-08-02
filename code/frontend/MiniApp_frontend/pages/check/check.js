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
    },
    month_checks: {},
    checks: [
      {
        job_name: "搬砖工打卡",
        shop_name: "交大",
        begin_time: "8:30",
        end_time: "12:30",
        job_id: 1,
      },
    ],
  },

  // onShow的时候向后端请求当前的工作列表
  onReady() {
    // 显示打卡成功信息
    if (app.globalData.showSendMessage) {
      app.globalData.showSendMessage = false;
      this.handleCheckSuccess();
    }

    // 可以查看的工作从当天到30天后
    var req = new request();
    var begin_date = new Date();
    var end_date = new Date();
    end_date.setDate(begin_date.getDate() + 30)

    // 拿到所有日期的list，
    var json = [{
      begin_date: util.formatDate(begin_date),
      end_date: util.formatDate(end_date)
    }]
    var dates_list1 = util.getDates(json)
    // 用JSON保存日期对应的工作表
    var month_checks = {} 
    for (var i in dates_list1) {
      month_checks[dates_list1[i]] = new Array()
    }

    this.calendar.enableArea([util.formatDate(begin_date), util.formatDate(end_date)]);

    req.getRequest(host + schedule, json[0], app.globalData.access_token).then(res => {
      if (res.statusCode === 200) {
        var data = res.data.data;
        for (var i in data) {
          // 拿到第i个工作的工作日期的list
          var dates_json = [{
            begin_date: data[i].begin_date,
            end_date: data[i].end_date,
          }]
          //填充month_checks
          var date_list = util.getDates(dates_json)
          for (var j in date_list) {
            var today = new Date();
            today.setHours(0, 0, 0, 0);
            if (new Date(date_list[j]) >= today) {
              month_checks[date_list[j]].push({
                job_name: data[i].job_name,
                shop_name: data[i].shop_name,
                begin_time: data[i].begin_time,
                end_time: data[i].end_time,
                job_id: data[i].job_id
              })
            }
          }
        }
        this.setData({
          month_checks: month_checks,
          checks: month_checks[util.formatDate(begin_date)]
        })
      }
      else if (res.statusCode === 400) {
        // TODO
      }
    }).catch(err => {
      console.log(err)
    })
  },

  afterTapDay(e) {
    // console.log('afterTapDay', e.detail);
    var format_date = e.detail.year + "-" + (String(e.detail.month).length == 1 ? "0" + e.detail.month : e.detail.month) + "-" + (String(e.detail.day).length == 1 ? "0" + e.detail.day : e.detail.day);
    this.setData({
      checks: this.data.month_checks[format_date]
    })
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