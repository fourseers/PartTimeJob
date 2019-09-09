// pages/cv_inform/cv_inform.js
const { $Toast } = require("../../dist/base/index");
const app = getApp();
import request from "../../api/request.js"
import { host, register_data, cv_curd, user_info } from "../../api/url.js"
var job_id;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    exp_index: ["经历1"],
    experience: [""],
    del_modal_visible: false,
    title: "",
    name: "",
    name_error: false,
    gender: false,
    height: "",
    weight: "",
    phone_number: "",
    phone_error: false,
    identity: "",
    identity_error: false,
    evaluation: "",
    education_list: [],
    education: "",
    isLoading: false,
  },

  onLoad(options) {
    job_id = options.id;
  },

  onReady() {
    var req = new request();
    req.getRequest(host + register_data, null).then(res => {
      if (res.statusCode === 200) {
        // 给后端返回的tags的列表中的每个json都添加isChosen字段
        var tags = res.data.data.tags;
        for (var index in tags) {
          tags[index].isChosen = false;
        }
        // 利用后端返回的tags和education来设置前端js的default
        this.setData({
          education_list: res.data.data.education,
        })
      }
      else if (res.statusCode === 400) {
        // TODO: 添加请求失败的处理
      }
    }).catch(err => {
      // console.log(err);
      // TODO: 添加请求失败的处理
    });

    req.getRequest(host + user_info, null, app.globalData.access_token).then(res => {
      if (res.statusCode === 200) {
        var info = res.data.data.info;
        this.setData({
          name: info.name,
          gender: info.gender,
          phone_number: info.phone,
          identity: info.identity,
          education: info.education
        })
      }
      if (res.statusCode === 400) {
        // TODO: 添加请求不返回200的处理
      }
    }).catch(err => {
      // console.log(err);
      // TODO: 添加请求失败的处理
    });
  },

  onShow(options){
    if(job_id === "0"){
      //console.log("添加简历")
    }
    else{ //向后端请求该id的简历的信息
      //console.log("修改简历")
      var req = new request();
      req.getRequest(host + cv_curd + "?cv_id=" + job_id, null, app.globalData.access_token).then(res => {
        //console.log(res);
        if(res.statusCode === 200) {
          var data = res.data.data;
          if (data.experiences.length > 1) {
            var new_exp_index = this.data.exp_index;
            for (var i in data.experiences) {
              new_exp_index[i] = "经历" + (parseInt(i) + 1)
            }
            this.setData({
              exp_index: new_exp_index
            })
          }
          this.setData({
            education: data.education,
            experience: data.experiences,
            gender: data.gender,
            height: data.height,
            identity: data.identity,
            name: data.name,
            phone_number: data.phone,
            evaluation: data.statement,
            title: data.title,
            weight: data.weight
          })
        }
        else if(res.statusCode === 400) {
          
        }
      }).catch(err => {
        console.log(err);
      })
    }
  },

  getTitle(e) {
    this.setData({
      title: e.detail.detail.value
    })
  },

  //每次更新name的input组件后都重新获取name
  getName(e) {
    var reg = /^[\u4E00-\u9FA5A-Za-z]+$/;
    if ((e.detail.detail.value.length <= 1) || reg.test(e.detail.detail.value) === false) {
      this.setData({
        name_error: true
      })
    }
    else {
      this.setData({
        name: e.detail.detail.value,
        name_error: false
      })
    }
  },

  //用于性别switch的切换
  changeGender(e) {
    this.setData({
      gender: !this.data.gender
    })
  },

  //每次更新height的input组件后都重新获取height
  getHeight(e) {
    this.setData({
      height: e.detail.detail.value
    })
  },

  //每次更新weight的input组件后都重新获取weight
  getWeight(e) {
    this.setData({
      weight: e.detail.detail.value
    })
  },

  //每次更新phone_number的input组件后都重新获取phone_number
  getPhoneNumber(e) {
    var reg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;
    if (reg.test(e.detail.detail.value) === false) {
      this.setData({
        phone_error: true
      })
    }
    else {
      this.setData({
        phone_number: e.detail.detail.value,
        phone_error: false
      })
    }
  },

  //每次更新identity的input组件后都重新获取identity
  getIdentity(e) {
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if (reg.test(e.detail.detail.value) === false) {
      this.setData({
        identity_error: true
      })
    }
    else {
      this.setData({
        identity: e.detail.detail.value,
        identity_error: false
      })
    }
  },

  //每次更新education的input组件后都重新获取education
  getEducation(e) {
    this.setData({
      education: this.data.education_list[e.detail.value]
    })
  },

  //每次更新evaluation的input组件后都重新获取evaluation
  getEvaluation(e) {
    this.setData({
      evaluation: e.detail.detail.value,
    })
  },

  //每次更新experience的input组件后都重新获取evaluation
  getExperiences(e) {
    var newExp = this.data.experience;
    newExp[e.target.id] = e.detail.detail.value;
    //console.log(newExp);
    this.setData({
      experience: newExp
    })
  },

  //添加工作经历input
  addExperience() {
    var newInd = this.data.exp_index;
    newInd[newInd.length] = "经历" + (newInd.length + 1);
    var newExp = this.data.experience;
    newExp[newInd.length - 1] = ""
    this.setData({
      exp_index: newInd,
      experience: newExp,
    })
  },

  //删除最后一个工作经历input
  deleteExperience() {
    var newInd = this.data.exp_index;
    var length = this.data.exp_index.length;
    if (length <= 1) {
      $Toast({
        content: "必须输入至少一条工作经历",
        type: "error"
      });
    }
    else if (this.data.experience[length - 1]) {
      this.setData({
        del_modal_visible: true
      })
    }
    else {
      newInd.pop();
      this.setData({
        exp_index: newInd,
      })
    }
  },

  //（对话框）确认删除
  delConfirm() {
    var newInd = this.data.exp_index;
    var newExp = this.data.experience;
    newInd.pop();
    newExp.pop();
    this.setData({
      del_modal_visible: false,
      exp_index: newInd,
      experience: newExp
    })
  },

  //（对话框）取消删除
  delCancel() {
    this.setData({
      del_modal_visible: true
    })
  },

  //保存简历
  handleSave() {
    if (this.data.height === "") {
      $Toast({
        content: "请输入您的身高",
        type: "error"
      });
      return;
    }
    else if (this.data.weight === "") {
      $Toast({
        content: "请输入您的体重",
        type: "error"
      });
      return;
    }
    else if (this.data.evaluation === "") {
      $Toast({
        content: "请输入自我评价",
        type: "error"
      });
      return;
    }
    for (var i = 0; i < this.data.exp_index.length; i++) {
      if (this.data.experience[i] === "") {
        $Toast({
          content: "请不要保存空的工作经历！",
          type: "error"
        });
        return;
      }
    }
    // 向后端请求，添加一条新的简历
    var req = new request();
    var post_data = {
      education: this.data.education,
      experiences: this.data.experience,
      gender: !this.data.gender,
      height: this.data.height,
      identity: this.data.identity,
      name: this.data.name,
      phone: this.data.phone_number,
      statement: this.data.evaluation,
      title: this.data.title,
      weight: this.data.weight
    }
    this.setData({
      isLoading: true,
    })
    if (job_id === "0") {
      req.postRequest(host + cv_curd, post_data, app.globalData.access_token).then(res => {
        if (res.statusCode === 200) {
          $Toast({
            content: "保存简历成功",
            type: "success"
          });
          setTimeout(function () {
            wx.navigateBack({

            })
          }, 1000)
        }
        else if (res.statusCode === 400) {
          $Toast({
            content: "保存简历失败，请重试",
            type: "error"
          });
        }
        this.setData({
          isLoading: false,
        })
      }).catch(err => {
        console.log(err)
      })
    }
    else {
      post_data.cv_id = job_id;
      req.putRequest(host + cv_curd, post_data, app.globalData.access_token).then(res => {
        if (res.statusCode === 200) {
          $Toast({
            content: "保存简历成功",
            type: "success"
          });
          setTimeout(function () {
            wx.navigateBack({

            })
          }, 1000)
        }
        else if (res.statusCode === 400) {
          $Toast({
            content: "保存简历失败，请重试",
            type: "error"
          });
        }
        this.setData({
          isLoading: false,
        })
      }).catch(err => {
        console.log(err)
      })
    }
  }
})