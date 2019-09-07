// pages/job/job.js
const { $Toast } = require('../../dist/base/index');
const { $Message } = require('../../dist/base/index');
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
    jobCount: 0,
    isLoading: false,
    total_hits: 9999,
    tabs: [
      {
        key: "distance",
        title: "距离"
      }, {
        key: "salary",
        title: "工资"
      }, {
        key: "date",
        title: "时间"
      }
    ],
    current_tab: "distance",
    back_to_main: true,
    actions: [
      {
        name: "确定"
      }
    ]
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

    //判断用户是否注册
    if (app.globalData.is_registered === true) {
      this.setData({
        back_to_main: false
      })
    }
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
    if (this.data.jobCount >= this.data.total_hits) {
      return;
    }
    var req = new request();
    req.getRequest(host + job_list, 
      {
        latitude: this.data.latitude,
        longitude: this.data.longitude,
        entryOffset: this.data.jobCount,
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
          new_job.detail = job_list[i].job_detail.slice(0, 50);
          var tags = job_list[i].tags.split(" ");
          var temp_tags = []
          for (var index in tags) {
            temp_tags.push({
              id: index,
              name: tags[index],
            })
          }
          new_job.tags = temp_tags;
          // new_job.tags
          new_jobs[parseInt(formal_length) + parseInt(i)] = new_job
        }
        
        this.setData({
          jobs: new_jobs,
          jobCount: this.data.jobCount + 15,
          total_hits: res.data.data.total_hits
        })
      }
    }).catch(err => {
      console.log(err);
    })
  },

  refresh() {
    this.setData({
      jobCount: 0,
      jobs: [],
      total_hits: 9999
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
  },

  handleChangeTab({ detail }) {
    console.log(detail)
  },

  handleClick(e) {
    //console.log(e)
    wx.reLaunch({
      url: "/pages/user/user",
    })
  }

})