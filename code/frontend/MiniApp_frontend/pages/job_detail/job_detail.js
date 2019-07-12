// pages/job-detail/job_detail.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   * job_name是岗位名称
   * job_detail是岗位详细描述
   * begin_[year, month, date]是岗位工作开始时间
   * end_[year, month, date]是岗位工作结束时间
   * need_amount是岗位需要的人数
   * begin_apply_[year, month, date]是岗位开始接受应聘的时间
   * end_apply_[year, month, date]是岗位结束接受应聘的时间
   * salary是岗位的薪水
   * create_time是岗位发布的时间
   * tags是岗位匹配的职业倾向
   * shop_name是岗位所属的店铺名称
   * brand是店铺的品牌名
   * industry是店铺所属的行业
   * address是店铺的地址
   * company_name是店铺所属公司的名称
   * shop_id是店铺的id，用于跳转到店铺的详细介绍页面 *TODO
   * visible用于决定是否显示对话框
   * longitude， latitude， markers用于小地图的显示
   */
  data: {
    job_name: "fourseers",
    job_detail: "这里是详细描述",
    begin_year: (new Date()).getFullYear(),
    begin_month: (new Date()).getMonth(),
    begin_date: (new Date()).getDate(),
    end_year: (new Date()).getFullYear(),
    end_month: (new Date()).getMonth(),
    end_date: (new Date()).getDate(),
    need_amount: 4,
    begin_apply_year: (new Date()).getFullYear(),
    begin_apply_month: (new Date()).getMonth(),
    begin_apply_date: (new Date()).getDate(),
    end_apply_year: (new Date()).getFullYear(),
    end_apply_month: (new Date()).getMonth(),
    end_apply_date: (new Date()).getDate(),
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
    shop_name: "软件学院",
    brand: "前端",
    industry: "板砖",
    address: "上海市闵行区上海交通大学",
    company_name: "脚痛大学",
    //shop_id不用显示，但是可以用于跳转页面
    shop_id: 0,
    //用于对话框的变量
    visible: false,
    longitude: 0.0,
    latitude: 0.0,
    markers: [{
      id: 1,
      latitude: 0.0,
      longitude: 0.0,
      name: ''
    }],
  },

  /**
   * 生命周期函数--监听页面加载
   * onLoad的时候似乎不能调用this.setData，实际情况要与后端通信后决定
   */
  onLoad: (options) => {
    //用options 中的job_id向后台请求更详细的信息
    //console.log(options);
  },

  /* 
   * TODO
   * onShow的时候向后端请求岗位的详细信息
   * onShow的时候向后端请求店铺的位置信息
   */
  onShow() {
    wx.getLocation({
      type: "gcj02",
      success: res => {
        this.setData({
          longitude: res.longitude,
          latitude: res.latitude,
          markers: [{
            id: 1,
            latitude: res.latitude,
            longitude: res.longitude,
            name: '软件学院'
          }]
        })
      }
    });
  },

  // 按立即报名按钮后弹出对话框
  handleClickApply() {
    this.setData({
      visible: true
    });
  },

  // 对话框确定后发送岗位申请
  handleSendApply() {
    // TODO
    // 发送岗位申请请求
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
