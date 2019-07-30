// pages/choose_date/choose_date.js
const { $Toast } = require('../../dist/base/index');
var util = require("../../utils/util.js")
var select_time = 0;
var job_id = 0;
const app = getApp();
import request from "../../api/request.js";
import { host, job_detail, cv_list, applied_time, apply_job } from "../../api/url.js";
var begin_date;
var end_date;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    calendar_config: {
      // disablePastDay: true,
      // defaultDay: '2019-7-15',
      multi: true
    },
    visible: false,
    begin_date: {},
    end_date: {},
    unable_dates: [],
    tips: "",
    error_visible: false,
    error_action: [
      {
        name: "刷新页面",
      }
    ],
    date_selected: false,
    cv_list: [],
    cd_id: ""
  },

  onLoad(options) {
    // TODO
    job_id = options.id;
    begin_date = options.begin_date;
    end_date = options.end_date;
  },

  onReady() {
    // var req = new request();
  },

  onShow() {
    var req = new request();
    select_time = 0;

    req.getRequest(host + applied_time + job_id, null, app.globalData.access_token).then(res => {
      if (res.statusCode === 200) {
        var new_config = this.data.calendar_config;
        new_config.defaultDay = util.formatDate(new Date());
        var unable_dates = util.getDates(res.data.data.applied_dates);
        var able = [{
          begin_date: begin_date,
          end_date: end_date
        }]
        var able_dates = util.getDates(able);
        var able_dates_json = util.getDatesJson(able);
        for (var i in unable_dates) {
          for (var j in able_dates) {
            if (unable_dates[i] == able_dates[j]) {
              able_dates.splice(j, 1)
              able_dates_json.splice(j, 1)
            }
          }
        }
        this.calendar.enableDays(able_dates);
        this.calendar.setSelectedDays(able_dates_json);
        // this.calendar.disableDay(unable_dates);
        console.log(unable_dates)
        this.setData({
          calendar_config: new_config,
          unable_dates: unable_dates,
          begin_date: {},
          end_date: {},
        })
      }
      if (res.statusCode === 400) {
        wx.navigateBack({

        })
      }
    }).catch(err => {
      console.log(err);
    });

    req.getRequest(host + cv_list, null, app.globalData.access_token).then(res => {
      if (res.statusCode === 200) {
        this.setData({
          cv_list: res.data.data
        })
      }
    }).catch(err => {
      console.log(err);
    });


  },

  handleAddCv() {
    wx.navigateTo({
      url: "/pages/cv_list/cv_list",
    })
  },

  radioChange(e) {
    this.setData({
      cv_id: e.detail.value
    })
  },

  // 按立即报名按钮后弹出对话框
  handleClickApply() {
    this.setData({
      visible: true
    });
  },

  // 对话框确定后发送岗位申请
  handleSendApply() {
    if (this.data.date_selected === false) {
      $Toast({
        content: "请选择一个合适的时间区间",
        type: "error"
      })
      return;
    }
    console.log(this.data.cv_id);
    if (this.data.cv_id === null) {
      $Toast({
        content: "请选择一条简历",
        type: "error"
      })
      return;
    }
    
    // 发送岗位申请请求
    var req = new request();
    this.setData({
      isLoading: true,
    })
    req.postRequest(host + apply_job, {
      job_id: parseInt(job_id),
      cv_id: this.data.cv_id,
      begin_date: this.data.begin_date.year + "-" + this.data.begin_date.month + "-" + this.data.begin_date.day,
      end_date: this.data.end_date.year + "-" + this.data.end_date.month + "-" + this.data.end_date.day
    }, app.globalData.access_token).then(res => {
      if (res.statusCode === 200) {
        app.globalData.showSendMessage = true;
        wx.navigateBack({

        })
      }
      if (res.statusCode === 400) {
        this.handleClose();
        $Toast({
          content: res.data.message,
          type: 'error'
        });
        this.setData({
          isLoading: false,
        })
      }
    }).catch(err => {
      console.log(err)
    })
  },

  // 对话框取消后隐藏对话框
  handleClose() {
    this.setData({
      visible: false
    })
  },

  afterTapDay(e) {
    var selected = e.detail.currentSelected;
    if (select_time === 0) {
      this.calendar.setTodoLabels({
        pos: 'top', // 待办点标记位置 ['top', 'bottom']
        days: [{
          year: selected.year,
          month: selected.month,
          day: selected.day,
          todoText: "开始"
        }]
      })
      this.setData({
        begin_date: {
          year: selected.year,
          month: selected.month,
          day: selected.day, 
        }
      })
      select_time += 1
    }
    else if (select_time === 1) {
      this.calendar.setTodoLabels({
        pos: 'top', // 待办点标记位置 ['top', 'bottom']
        days: [{
          year: selected.year,
          month: selected.month,
          day: selected.day,
          todoText: "结束"
        }]
      })
      this.setData({
        end_date: {
          year: selected.year,
          month: selected.month,
          day: selected.day,
        }
      })
      select_time += 1
    }
    else if (this.data.date_selected === false) {
      this.setData({
        tips: "你只能选择一个开始时间和一个结束时间",
        error_visible: true
      })
    }
    else if (this.data.date_selected === true) {
      $Toast({
        content: "你已选定了合适的时间区间",
        type: "warning"
      })
      
      var tap_date = [{
        year: selected.year,
        month: selected.month,
        day: selected.day,
      }]
      // console.log(tap_date)
      if(selected.choosed === true) {
        this.calendar.setUnselectedDays(tap_date)
      }
      else{
        this.calendar.setSelectedDays(tap_date)
      }
      
    }
  },

  handleRefresh(e) {
    this.calendar.clearTodoLabels();
    this.onReady();
    this.setData({
      error_visible: false
    })
  },

  handleTimeSlot() {
    if (JSON.stringify(this.data.begin_date) == "{}" && JSON.stringify(this.data.end_date) == "{}") {
      $Toast({
        content: '请选择要应聘的时间段',
        type: 'error'
      });
      return;
    }
    if (JSON.stringify(this.data.end_date) == "{}" && select_time == 1) {
      $Toast({
        content: "选定时间区间成功",
        type: "success"
      })
      this.setData({
        end_date: this.data.begin_date
      })
      return;
    }
    var begin_date = new Date(this.data.begin_date.year, this.data.begin_date.month - 1, this.data.begin_date.day);
    var end_date = new Date(this.data.end_date.year, this.data.end_date.month - 1, this.data.end_date.day);
    if (begin_date > end_date) {
      this.setData({
        tips: "开始时间不能在结束时间之后",
        error_visible: true
      })
      return;
    }
    else {
      while (true) {
        // console.log(util.formatDate(begin_date))
        if (this.data.unable_dates.indexOf(util.formatDate(begin_date)) !== -1) {
          this.setData({
            tips: "选择的时间段中存在不允许的日期",
            error_visible: true
          })
          return;
        }
        if ((begin_date.getDate() === end_date.getDate()) && (begin_date.getMonth() === end_date.getMonth()) && (begin_date.getFullYear() === end_date.getFullYear())) {
          break;
        }
        begin_date.setDate(begin_date.getDate() + 1);
      }
      // 如果从while-loop中跳出，那么说明选择的时间段是允许的
      var begin_date = new Date(this.data.begin_date.year, this.data.begin_date.month - 1, this.data.begin_date.day);
      var end_date = new Date(this.data.end_date.year, this.data.end_date.month - 1, this.data.end_date.day);
      var dates = [{
        begin_date: util.formatDate(begin_date),
        end_date: util.formatDate(end_date)
      }]
      this.calendar.setUnselectedDays(util.getDatesJson(dates));
      this.setData({
        date_selected: true,
      })
      $Toast({
        content: "选定时间区间成功",
        type: "success"
      })
    }
  },

  onTapDay(e) {
    //console.log('onTapDay', e.detail);
  },

  whenChangeMonth(e) {
    //console.log('whenChangeMonth', e.detail);
  },

  afterCalendarRender(e) {
    //console.log('afterCalendarRender', e);
  }

})