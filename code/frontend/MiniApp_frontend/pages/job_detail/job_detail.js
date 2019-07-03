// pages/job-detail/job_detail.js
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
    shop_id: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //用options 中的job_id向后台请求更详细的信息
    console.log(options);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})