// pages/apply_detail/apply_detail.js
const app = getApp();
import request from "../../api/request.js";
import { host, application_list } from "../../api/url.js";
var util = require("../../utils/util.js")

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

  onShow(){
    var req = new request();
    req.getRequest(host + application_list, null, app.globalData.access_token).then(res => {
      if (res.statusCode === 200) {
        var list = res.data.data.content;
        var new_applications = {
          all: [],
          pass: [],
          refuse: []
        }
        for (var i in list) {
          var new_date = list[i].create_time.substring(0, 10);
          if (list[i].status === true) {
            var app = {
              name: new_date,
              status: "已通过"
            }
            new_applications.all.push(app);
            new_applications.pass.push(app);
          }
          else if (list[i].status === false) {
            var app = {
              name: new_date,
              status: "已拒绝"
            }
            new_applications.all.push(app);
            new_applications.refuse.push(app);
          }
          else {
            var app = {
              name: new_date,
              status: "待审核"
            }
            new_applications.all.push(app);
          }
        }
        this.setData({
          applications: new_applications,
          current_chosen: new_applications.all
        })
      }
    }).catch(err => {
      console.log(err);
    })
  },

  handleChangeTab(e) {
    this.setData({
      current_tab: e.detail.key,
      current_chosen: this.data.applications[e.detail.key]
    })
  }

})