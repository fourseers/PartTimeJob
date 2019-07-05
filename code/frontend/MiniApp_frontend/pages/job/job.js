// pages/job/job.js
const { $Toast } = require('../../dist/base/index');
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    jobs: [
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
    ],
    position: "附近商家",
    scrollTop: 0
  },

  /*
   * 如果从job_detail确认应聘并返回，那就显示应聘成功的toast
   */
  onShow(options) {
    if (app.globalData.showSendMessage) {
      this.handleSuccess();
    }
    app.globalData.showSendMessage = false;
  },

  // 上拉刷新
  onPullDownRefresh() {
    //console.log("onRefresh");
  },

  // 触底加载更多
  onReachBottom() {
    //console.log("onBottom");
  },

  //选择位置
  choosePosition() {
    wx.chooseLocation({
      success: (res) => {
        this.setData({
          position: res.address
        })
      }
    })
  },

  //显示toast的function
  handleSuccess() {
    $Toast({
      content: '申请岗位成功',
      type: 'success'
    });
  }

})