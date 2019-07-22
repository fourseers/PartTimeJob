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
        title: "搬砖工上班打卡",
        checkBeginTime: "8:30",
        checkEndTime: "9:00",
        canCheck: true,
        id: 0,
      },
      {
        title: "搬砖工下班打卡",
        checkBeginTime: "20:30",
        checkEndTime: "21:00",
        canCheck: false,
        id: 1,
      },
    ],
  },

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