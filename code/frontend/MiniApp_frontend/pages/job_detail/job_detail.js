// pages/job-detail/job_detail.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    job_name: "fourseers",
    job_detail: "这里是详细描述",
    begin_date: new Date(),
    end_date: new Date(),
    need_amount: 4,
    begin_apply_date: new Date(),
    end_apply_date: new Date(),
    salary: "5K-10K",
    create_time: new Date(),
    //need_gender: 0,
    //education: "本科",
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
    ],
    shop_name: "",
    brand: "",
    industry: "",
    address: "",
    company_name: "",
    //shop_id不用显示，但是可以用于跳转页面
    shop_id: 0,
    //用于对话框的变量
    visible: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: (options) => {
    //用options 中的job_id向后台请求更详细的信息
    //console.log(options);
  },

  // 按立即报名按钮后弹出对话框
  handleClickApply() {
    this.setData({
      visible: true
    });
  },

  // 对话框确定后发送岗位申请
  handleSendApply() {
    app.globalData.showSendMessage = true;
    wx.navigateBack({
      
    })
  },

  // 对话框取消后隐藏对话框
  handleClose() {
    this.setData({
      visible: false
    })
  }

})