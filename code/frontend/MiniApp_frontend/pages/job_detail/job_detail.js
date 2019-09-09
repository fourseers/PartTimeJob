// pages/job-detail/job_detail.js
const { $Toast } = require('../../dist/base/index');
const app = getApp();
import request from "../../api/request.js"
import { host, job_detail, apply_job } from "../../api/url.js"
var identifier = 0;
var util = require("../../utils/util.js")

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
    //begin_year: (new Date()).getFullYear(),
    //begin_month: (new Date()).getMonth(),
    begin_date: "",
    //end_year: (new Date()).getFullYear(),
    //end_month: (new Date()).getMonth(),
    end_date: "",
    need_amount: 4,
    //begin_apply_year: (new Date()).getFullYear(),
    //begin_apply_month: (new Date()).getMonth(),
    begin_apply_date: "",
    //end_apply_year: (new Date()).getFullYear(),
    //end_apply_month: (new Date()).getMonth(),
    end_apply_date: "",
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
    time_table_visible: false,
    time_table_actions: []
  },

  /**
   * 生命周期函数--监听页面加载
   * onLoad的时候似乎不能调用this.setData，实际情况要与后端通信后决定
   */
  onLoad: (options) => {
    //用options 中的identifier向后台请求更详细的信息
    //console.log(options);
    identifier = options.identifier;
  },

  /* 
   * onShow的时候向后端请求岗位的详细信息
   * onShow的时候向后端请求店铺的位置信息
   */
  onShow() {
    if (app.globalData.showSendMessage) {
      this.handleSuccess();
      app.globalData.showSendMessage = false;
    }
    var req = new request();
    req.getRequest(host + job_detail + identifier, null, app.globalData.access_token).then(res => {

      if (res.statusCode === 200) {
        var info = res.data.data[0];

        var begin_date = new Date(info.begin_date);
        var end_date = new Date(info.end_date);
        var begin_apply_date = new Date(info.begin_apply_time);
        var end_apply_date = new Date(info.end_apply_time);
        this.setData({
          job_name: info.job_name,
          job_detail: info.job_detail,
          //begin_year: begin_date.getFullYear(),
          //begin_month: begin_date.getMonth()+1,
          begin_date: util.formatDate(begin_date),
          //end_year: end_date.getFullYear(),
          //end_month: end_date.getMonth()+1,
          end_date: util.formatDate(end_date),
          need_amount: 4,
          //begin_apply_year: begin_apply_date.getFullYear(),
          //begin_apply_month: begin_apply_date.getMonth()+1,
          begin_apply_date: util.formatDate(begin_apply_date),
          //end_apply_year: end_apply_date.getFullYear(),
          //end_apply_month: end_apply_date.getMonth()+1,
          end_apply_date: util.formatDate(end_apply_date),
          salary: info.salary.toFixed(2),
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

        //获取并保存可报名的时间段及其对应id
        var time_list = res.data.data;
        var new_time_table = [];
        for (var i in time_list) {
          var new_time = {};
          new_time.name = "时间段：" + time_list[i].begin_time + "~" + time_list[i].end_time;
          new_time.id = time_list[i].job_id;
          new_time_table.push(new_time)
        }
        this.setData({
          time_table_actions: new_time_table
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
    this.setData({
      time_table_visible: true
    })
  },

  handleSuccess() {
    $Toast({
      content: '申请岗位成功',
      type: 'success'
    });
  },

  handleCancel() {
    this.setData({
      time_table_visible: false
    })
  },

  handleClickItem(e) {
    //console.log(e)
    this.setData({
      time_table_visible: false
    })
    wx.navigateTo({
      url: "/pages/choose_date/choose_date?id=" + this.data.time_table_actions[e.detail.index].id + "&begin_date=" + this.data.begin_date + "&end_date=" + this.data.end_date,
    })
  }

})
