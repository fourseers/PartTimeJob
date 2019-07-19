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
    position: "附近商家"
  },

  /*
   * 如果从job_detail确认应聘并返回，那就显示应聘成功的toast
   */
  onShow(options) {
    if (app.globalData.showSendMessage) {
      this.handleSuccess();
      app.globalData.showSendMessage = false;
    }
    this.getUserJob();
  },

  getUserJob() {
    var req = new request();
    req.getRequest(host + job_list, null, app.globalData.access_token).then(res => {
      if(res.statusCode === 401){
        //console.log("user should login!");
      }
      if(res.statusCode === 200){
        var job_list = res.data.data.content;
        var new_jobs = [];
        for (var i in job_list){
          var new_job = {};
          new_job.id = job_list[i].job_id;
          new_job.name = job_list[i].job_name;
          new_job.detail = job_list[i].job_detail;
          new_job.tags = job_list[i].tag_list;
          new_jobs[i] = new_job
        }
        
        this.setData({
          jobs: new_jobs
        })
      }
    }).catch(err => {
      console.log(err);
    })
  },

  // 上拉刷新
  onPullDownRefresh() {
    //console.log("onRefresh");
    this.getUserJob();
  },

  // 触底加载更多
  onReachBottom() {
    //console.log("onBottom");
  },

  // 用户选择位置
  choosePosition() {
    wx.chooseLocation({
      success: (res) => {
        this.setData({
          position: res.address
        })
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