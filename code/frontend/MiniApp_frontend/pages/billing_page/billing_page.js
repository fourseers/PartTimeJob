// pages/billing_page/billing_page.js
const app = getApp();
import request from "../../api/request.js";
import { host, billing } from "../../api/url.js";


Page({

  /**
   * 页面的初始数据
   */
  data: {
    page_count: 0,
    total_pages: 9999,
    current_tab: "all",
    billings: {
      all: [
        {
          name: "岗位一",
          value: "100元",
          date: "2019-8-1",
          status: "已到账"
        },
        {
          name: "岗位二",
          value: "200元",
          date: "2019-8-1",
          status: "未到账"
        }
      ],
      yes: [
        {
          name: "岗位一",
          value: "100元",
          date: "2019-8-1",
          status: "已到账"
        }
      ],
      no: [
        {
          name: "岗位二",
          value: "200元",
          date: "2019-8-1",
          status: "未到账"
        }
      ]
    },
    current_chosen: [
      {
        name: "岗位一",
        value: "100元",
        date: "2019-8-1",
        status: "已到账"
      },
      {
        name: "岗位二",
        value: "200元",
        date: "2019-8-1",
        status: "未到账"
      }
    ]
  },

  onShow() {
    this.refresh();
    this.getBillings();
  },

  getBillings() {
    if (this.data.page_count >= this.data.total_pages) {
      return;
    }
    var req = new request();
    req.getRequest(host + billing, {
      pageCount: this.data.page_count
    }, app.globalData.access_token).then(res => {
      if (res.statusCode === 200) {
        var list = res.data.data.content;
        var new_applications = {
          all: [],
          yes: [],
          no: []
        }
        for (var i in list) {
          if (list[i].salary_confirmed === true) {
            var app = {
              name: list[i].job_name,
              value: list[i].payment,
              date: list[i].work_date,
              status: "已到账"
            }
            new_applications.all.push(app);
            new_applications.yes.push(app);
          }
          else if (list[i].salary_confirmed === false) {
            var app = {
              name: list[i].job_name,
              value: "",
              date: list[i].work_date,
              status: "未到账"
            }
            new_applications.all.push(app);
            new_applications.no.push(app);
          }
          else {
            var app = {
              name: list[i].job_name,
              value: "",
              date: list[i].work_date,
              status: "待审核"
            }
            new_applications.all.push(app);
          }
        }
        this.setData({
          applications: new_applications,
          current_chosen: new_applications.all,
          total_pages: res.data.data.total_pages,
          page_count: this.data.page_count + 1
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
  },

  refresh() {
    this.setData({
      page_count: 0,
      total_pages: 9999,
      billings: {},
      current_chosen: []
    })
  },

  // 上拉刷新
  onPullDownRefresh() {
    //console.log("onRefresh");
    this.refresh();
  },

  // 触底加载更多
  onReachBottom() {
    //console.log("onBottom");
    this.getUserJob();
  },

})