// pages/job/job.js
const { $Toast } = require('../../dist/base/index');
const { $Message } = require('../../dist/base/index');
const app = getApp();
import request from "../../api/request.js"
import { host, job_list, register_data, jobs_smart } from "../../api/url.js"
var job_list_url = job_list

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
        title: "距离",
        list: ["无限制", "1公里以内", "2公里以内", "5公里以内", "10公里以内", "30公里以内"],
        func: "changeDistance"
      }, {
        key: "salary",
        title: "工资",
        list: ["无限制", "0~50元/日", "50~200元/日", "200元以上/日"],
        func: "changeSalary"
      }, {
        key: "date",
        title: "时间",
        list: ["无限制", "1日以内", "3日以内", "7日以内", "30日以内"],
        func: "changeDate"
      }, {
        key: "tag",
        title: "标签",
        list: [],
        func: "changeTag"
      }, {
        key: "mode",
        title: "模式",
        list: ["普通搜索", "智能搜索"],
        func: "changeMode"
      }
    ],
    current_tab: "",
    daysToCome: "",
    geoRange: 30,
    maxSalary: "",
    minSalary: "",
    tag: "",
    back_to_main: true,
    actions: [
      {
        name: "确定"
      }
    ],
    isSpinning: true,
  },

  // 初始化标签picker
  onLoad() {
    var req = new request();
    req.getRequest(host + register_data, null).then(res => {
      if (res.statusCode === 200) {
        var tags = res.data.data.tags
        var new_tabs = this.data.tabs;
        var get_tags = []
        for (var i in tags) {
          get_tags.push(tags[i].name)
        }
        new_tabs[3].list = get_tags;
        this.setData({
          tabs: new_tabs
        })
      }
    }).catch(err => {

    })
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
    this.setData({
      isSpinning: false
    })
  },

  getUserJob() {
    if (this.data.jobCount >= this.data.total_hits) {
      return;
    }
    var req = new request();
    req.getRequest(host + job_list_url, 
      {
        latitude: this.data.latitude,
        longitude: this.data.longitude,
        entryOffset: this.data.jobCount,
        daysToCome: this.data.daysToCome,
        geoRange: this.data.geoRange,
        maxSalary: this.data.maxSalary,
        minSalary: this.data.minSalary,
        tag: this.data.tag
      }, app.globalData.access_token).then(res => {
      if(res.statusCode === 401){
        //console.log("user should login!");
      }
      if(res.statusCode === 200){
        console.log(res)
        var job_list = res.data.data.content;
        var new_jobs = this.data.jobs;
        var formal_length = new_jobs.length;
        for (var i in job_list){
          var new_job = {};
          new_job.id = job_list[i].job_id;
          new_job.name = job_list[i].job_name;
          new_job.detail = job_list[i].job_detail.slice(0, 50);
          new_job.identifier = job_list[i].identifier;
          if (job_list[i].score) {
            new_job.score = (job_list[i].score * 100).toFixed(2)
          }
          
          var temp_tags = [];
          if (job_list[i].tags) {
            var tags = job_list[i].tags.split(" ");
            for (var index in tags) {
              temp_tags.push({
                id: index,
                name: tags[index],
              })
            }
            this.setData({
              total_hits: res.data.data.total_hits
            })
          }
          else {
            temp_tags = job_list[i].tag_list;
            this.setData({
              total_hits: res.data.data.total_elements
            })
          }
          new_job.tags = temp_tags;
          new_jobs[parseInt(formal_length) + parseInt(i)] = new_job;
        }
        this.setData({
          jobs: new_jobs,
          jobCount: this.data.jobCount + 15
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

  handleChangeTab(e) {
    this.setData({
      current_tab: e.detail.key,
    })
  },

  handleClick(e) {
    //console.log(e)
    wx.reLaunch({
      url: "/pages/user/user",
    })
  },

  changeDistance(e) {
    const list = ["", 1, 2, 5, 10, 30];
    this.setData({
      current_tab: "",
      geoRange: list[parseInt(e.detail.value)]
    })
    this.refresh();
  },

  changeSalary(e) {
    const max = ["", 50, 200, 999999999];
    const min = ["", 0, 50, 200];
    this.setData({
      current_tab: "",
      maxSalary: max[parseInt(e.detail.value)],
      minSalary: min[parseInt(e.detail.value)],
    })
    this.refresh();
  },

  changeDate(e) {
    const date = ["", 1, 3, 7, 30];
    this.setData({
      current_tab: "",
      daysToCome: date[parseInt(e.detail.value)],
    })
    this.refresh();
  },

  changeTag(e) {
    const tags = this.data.tabs[3].list;
    this.setData({
      current_tab: "",
      tag: tags[parseInt(e.detail.value)]
    })
    this.refresh();
  },

  changeMode(e) {
    if (e.detail.value === "0") {
      job_list_url = job_list
    }
    else if (e.detail.value === "1") {
      job_list_url = jobs_smart
    }
    this.refresh();
  }

})