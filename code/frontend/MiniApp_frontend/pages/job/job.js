// pages/job/job.js
const { $Toast } = require('../../dist/base/index');
const app = getApp();
import request from "../../api/request.js"
import { host, job_list } from "../../api/url.js"

Page({

  /**
   * 页面的初始数据
   * jobs通过向后端发送请求来获取
   * position默认值为“附近商家”，在用户选择位置后改变
   */
  data: {
    jobs: [],
    position: "附近商家",
    latitude: 0.0,
    longitude: 0.0,
    pageCount: 0,
    isLoading: false,
  },

  onReady() {
    wx.getLocation({
      type: "gcj02",
      success: res => {
        this.setData({
          longitude: res.longitude,
          latitude: res.latitude,
        })
        this.refresh();
      }
    });

  },

  /*
   * 如果从job_detail确认应聘并返回，那就显示应聘成功的toast
   */
  onShow(options) {

    if (app.globalData.showSendMessage) {
      this.handleSuccess();
      app.globalData.showSendMessage = false;
    }
  },

  getUserJob() {
    var req = new request();
    req.getRequest(host + job_list, 
    {
      latitude: this.data.latitude,
      longitude: this.data.longitude,
      pageCount: this.data.pageCount,
    }, app.globalData.access_token).then(res => {
      if(res.statusCode === 401){
        //console.log("user should login!");
      }
      if(res.statusCode === 200){
        var job_list = res.data.data.content;
        var new_jobs = this.data.jobs;
        var formal_length = new_jobs.length;
        for (var i in job_list){
          var new_job = {};
          new_job.id = job_list[i].job_id;
          new_job.name = job_list[i].job_name;
          new_job.detail = job_list[i].job_detail;
          new_job.tags = job_list[i].tag_list;
          new_jobs[parseInt(formal_length) + parseInt(i)] = new_job
        }
        
        this.setData({
          jobs: new_jobs,
          pageCount: this.data.pageCount + 1
        })
      }
    }).catch(err => {
      console.log(err);
    })
  },

  refresh() {
    this.setData({
      pageCount: 0,
      jobs: []
    })
    this.getUserJob();
  },

  // 上拉刷新
  onPullDownRefresh() {
    //console.log("onRefresh");
    this.refresh();
  },

  // 触底加载更多
  onReachBottom() {
    //console.log("onBottom");
    this.setData({
      isLoading: true,
    })
    this.getUserJob();
    this.setData({
      isLoading: false,
    })
  },

  // 用户选择位置
  choosePosition() {
    wx.chooseLocation({
      success: (res) => {
        this.setData({
          position: res.address,
          latitude: res.latitude,
          longitude: res.longitude
        })
        this.refresh();
      }
    })
  },

  // 用于显示toast
  handleSuccess() {
    $Toast({
      content: '申请岗位成功',
      type: 'success'
    });
  }

})