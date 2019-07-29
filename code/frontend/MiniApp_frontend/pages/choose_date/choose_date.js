// pages/choose_date/choose_date.js
var util = require("../../utils/util.js")

Page({

  /**
   * 页面的初始数据
   */
  data: {
    calendar_config: {
      // disablePastDay: true,
      defaultDay: '2019-7-15',
      multi: true
    },
  },

  onReady() {
    var new_config = this.data.calendar_config;
    new_config.defaultDay = util.formatDate(new Date());
    this.setData({
      calendar_config: new_config
    })

    
    //console.log(util.getDates(date_range))
    // this.calendar.enableDays(util.getDates(date_range))
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
  }

})