// pages/choose_date/choose_date.js
const { $Toast } = require('../../dist/base/index');
var util = require("../../utils/util.js")
var select_time = 0;

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
    able_dates: [],
    tips: "",
    error_visible: false,
    error_action: [
      {
        name: "刷新页面",
      }
    ],
    date_selected: false
  },

  onLoad(options) {
    // TODO
    // console.log(options.id)
  },

  onReady() {
    select_time = 0;

    var new_config = this.data.calendar_config;
    new_config.defaultDay = util.formatDate(new Date());

    var applied_dates = [
      {
        begin_date: "2019-06-25",
        end_date: "2019-07-29"
      },
      {
        begin_date: "2019-08-01",
        end_date: "2019-08-08"
      }
    ]
    
    //console.log(util.getDates(date_range))
    var able_dates = util.getDates(applied_dates);
    this.calendar.enableDays(able_dates);
    this.calendar.setSelectedDays(util.getDatesJson(applied_dates))
    this.setData({
      calendar_config: new_config,
      able_dates: able_dates,
      begin_date: {},
      end_date: {},
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
    // 发送岗位申请请求
    var req = new request();
    this.setData({
      isLoading: true,
    })
    req.postRequest(host + apply_job, {
      job_id: parseInt(job_id),
      cv_id: "5d365f928ba346f03eb1177a",//5d318647a095e24d3285f8ea
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
      /*
      var tap_date = [{
        year: selected.year,
        month: selected.month,
        day: selected.day,
      }]
      if(selected.choosed === true) {
        this.calendar.setUnselectedDays(tap_date)
      }
      else{
        this.calendar.setSelectedDays(tap_date)
      }
      */
    }
  },

  handleRefresh(e) {
    this.onReady();
    this.setData({
      error_visible: false
    })
  },

  handleTimeSlot() {
    if (this.data.begin_date === {} || this.data.end_date === {}) {
      $Toast({
        content: '请选择要应聘的时间段',
        type: 'error'
      });
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
        if (this.data.able_dates.indexOf(util.formatDate(begin_date)) === -1) {
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