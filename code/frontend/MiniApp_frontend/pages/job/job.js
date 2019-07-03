// pages/job/job.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    jobs: [
      {
        id: 0,
        name: "工作一",
        detail: "工作一的附加描述",
        tags: [
          {
            id: 0,
            name: "厨师",
            isChosen: false
          },
          {
            id: 1,
            name: "收银",
            isChosen: false
          },
          {
            id: 2,
            name: "打杂",
            isChosen: false
          },
          {
            id: 3,
            name: "测试行超出会发生什么情况啦啦啦啦啦啦",
            isChosen: false
          }
        ]
      },
      {
        id: 1,
        name: "工作二",
        detail: "工作二的附加描述",
        tags: [
          {
            id: 0,
            name: "厨师",
            isChosen: false
          },
          {
            id: 1,
            name: "收银",
            isChosen: false
          },
          {
            id: 2,
            name: "打杂",
            isChosen: false
          }
        ]
      }
    ]
  },

  onLoad: function (options) {
    console.log("onLoad");
  },

  onPullDownRefresh: function () {
    console.log("onRefresh");
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    console.log("onBottom");
  },

  testClick(e) {
    console.log(e);
  }
})