// pages/apply_detail/apply_detail.js
Page({

  /**
   * 页面的初始数据
   * status
   */
  data: {
    current_tab: "all",
    applications: {
      all: [
        {
          name: "岗位一",
          status: "已通过"
        },
        {
          name: "岗位二",
          status: "已拒绝"
        }
      ],
      pass: [
        {
          name: "岗位一",
          status: "已通过"
        }
      ],
      refuse: [
        {
          name: "岗位二",
          status: "已拒绝"
        }
      ]
    },
    current_chosen: [
      {
        name: "岗位一",
        status: "已通过"
      },
      {
        name: "岗位二",
        status: "已拒绝"
      }
    ]
  },

  handleChangeTab(e) {
    this.setData({
      current_tab: e.detail.key,
      current_chosen: this.data.applications[e.detail.key]
    })
  }

})