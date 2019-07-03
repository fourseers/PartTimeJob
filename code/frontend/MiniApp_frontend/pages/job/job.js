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
    ],
    position: "附近商家",
    scrollTop: 0
  },

  onLoad(options) {
    
  },

  onPullDownRefresh() {
    console.log("onRefresh");
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {
    console.log("onBottom");
    
  },

  choosePosition() {
    wx.chooseLocation({
      success: res => {
        this.setData({
          position: res.address
        })
      }
    })
  },

  //页面滚动执行方式
  //似乎用于实现吸顶组件
  onPageScroll(event) {
    this.setData({
      scrollTop: event.scrollTop
    })
  }
})