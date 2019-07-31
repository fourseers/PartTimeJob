// pages/job-detail/job_detail.js
const { $Toast } = require('../../dist/base/index');
const app = getApp();
import request from "../../api/request.js"
import { host, job_detail, apply_job } from "../../api/url.js"
var job_id = 0;

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
    isLoading: false,
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
    address: "上海市闵行区上海交通大学",
    //shop_id不用显示，但是可以用于跳转页面
    shop_id: 0,
    //用于对话框的变量
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
    job_id = options.id;
  },

  /* 
   * onShow的时候向后端请求岗位的详细信息
   * onShow的时候向后端请求店铺的位置信息
   */
  onShow() {
    var req = new request();
    req.getRequest(host + job_detail + job_id, null, app.globalData.access_token).then(res => {
      var info = res.data.data;
      if (res.statusCode === 200) {
        var begin_date = new Date(info.begin_date);
        var end_date = new Date(info.end_date);
        var begin_apply_date = new Date(info.begin_apply_time);
        var end_apply_date = new Date(info.end_apply_time);
        this.setData({
          job_name: info.job_name,
          job_detail: info.job_detail,
          begin_year: begin_date.getFullYear(),
          begin_month: begin_date.getMonth()+1,
          begin_date: begin_date.getDate(),
          end_year: end_date.getFullYear(),
          end_month: end_date.getMonth()+1,
          end_date: end_date.getDate(),
          need_amount: 4,
          begin_apply_year: begin_apply_date.getFullYear(),
          begin_apply_month: begin_apply_date.getMonth()+1,
          begin_apply_date: begin_apply_date.getDate(),
          end_apply_year: end_apply_date.getFullYear(),
          end_apply_month: end_apply_date.getMonth()+1,
          end_apply_date: end_apply_date.getDate(),
          salary: info.salary,
          tags: info.tag_list,
          shop_id: info.shop.shop_id,
          shop_name: info.shop.shop_name,
          address: info.shop.address,
          longitude: info.shop.longitude,
          latitude: info.shop.latitude,
          markers: [{
            id: 1,
            latitude: info.shop.latitude,
            longitude: info.shop.longitude,
            name: info.shop.shop_name
          }]
        })
      }
    }).catch(err => {
      console.log(err);
    })
    /*
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
    */
  },

  // 按立即报名按钮后弹出对话框
  handleClickApply() {
    wx.navigateTo({
      url: "/pages/choose_date/choose_date?id=" + job_id + "&begin_date=" + this.data.begin_apply_year + "-" + this.data.begin_apply_month + "-" + this.data.begin_apply_date + "&end_date=" + this.data.end_apply_year + "-" + this.data.end_apply_month + "-" + this.data.end_apply_date,
    })
  },

})
