// pages/check/check.js
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
        id: 0,
      },
    ],
  },

  // onShow的时候向后端请求当前的工作列表
  onShow() {
    const data = [
      {
        year: '2019',
        month: '7',
        day: '16'
      },
      {
        year: 2019,
        month: 7,
        day: 18,
        todoText: '工作'
      }
    ];
    // 异步请求
    setTimeout(() => {
      this.calendar.setTodoLabels({
        // circle: true,
        pos: 'top',
        days: data
      });
    }, 1000);
    //this.calendar.enableArea(['2019-5-7', '2019-10-28']);
    //this.calendar.switchView('week');
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

})