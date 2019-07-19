// pages/user_inform/user_inform.js
const { $Toast } = require("../../dist/base/index");
const app = getApp();
import request from "../../api/request.js";
import { host, user_info, register_data, modify_info } from "../../api/url.js";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    gender: true,
    name: "SJH",
    name_error: false,
    identity: "310110123456781000",
    identity_error: false,
    phone_number: "13812345678",
    phone_error: false,
    phone_modified: false,
    country: "China",
    country_error: false,
    country_modified: false,
    city: "Shanghai",
    city_error: false,
    city_modified: false,
    education: "primary school",
    education_modified: false,
    education_list: [],
    isLoading: false,
    info_saved: null,
    tags: [],
    chosen_tags: [],
    tags_modified: false
  },

  onShow(){
    var req = new request();

    // 向后台获取注册元数据, 包括文化水平list、tags list
    req.getRequest(host + register_data, null).then(res => {
      if (res.statusCode === 200) {
        // console.log(res);
        // 给后端返回的tags的列表中的每个json都添加isChosen字段
        var tags = res.data.data.tags;
        for (var index in tags) {
          tags[index].isChosen = false;
        }
        // 利用后端返回的tags和education来设置前端js的default
        this.setData({
          education_list: res.data.data.education,
          tags: tags,
        })
        this.getUserInfo();
      }
      else if (res.statusCode === 400) {
        // TODO: 添加请求不返回200的处理
      }
    }).catch(err => {
      // console.log(err);
      // TODO: 添加请求失败的处理
    });
  },

  getUserInfo() {
    var req = new request();
    //onshow的时候从后端获取当前用户的信息，填充为用户表单上的默认信息
    req.getRequest(host + user_info, null, app.globalData.access_token).then(res => {
      if (res.statusCode === 200) {
        var info = res.data.data.info;

        var toChosen = this.data.tags;
        for (var i in info.tags) {
          var switchId = info.tags[i].id;
          for (var j in this.data.tags) {
            if (this.data.tags[j].id === switchId) {
              toChosen[j].isChosen = true;
            }
          }
        }
        this.setData({
          name: info.name,
          gender: info.gender,
          identity: info.identity,
          phone_number: info.phone,
          country: info.country,
          city: info.city,
          education: info.education,
          info_saved: info,
          chosen_tags: info.tags,
          tags: toChosen,
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
    //判断是否有修改，如果没有就不用post了
    if (e.detail.detail.value !== this.data.info_saved.phone){
      this.setData({
        phone_modified: true
      })
    }
    else{
      this.setData({
        phone_modified: false
      })
    }
  },

  //每次更新country的input组件后都重新获取country
  getCountry(e) {
    // TODO 添加国家的正则表达式 or 使用picker
    // var reg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;
    // if (reg.test(e.detail.detail.value) === false) {
    if (false) {
      this.setData({
        country_error: true
      })
    }
    else {
      this.setData({
        country: e.detail.detail.value,
        country_error: false
      })
    }
    //判断是否有修改，如果没有就不用post了
    if (e.detail.detail.value !== this.data.info_saved.country) {
      this.setData({
        country_modified: true
      })
    }
    else {
      this.setData({
        country_modified: false
      })
    }
  },

  //每次更新city的input组件后都重新获取city
  getCity(e) {
    // TODO 添加城市的正则表达式 or 使用picker
    // var reg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;
    // if (reg.test(e.detail.detail.value) === false) {
    if (false) {
      this.setData({
        city_error: true
      })
    }
    else {
      this.setData({
        city: e.detail.detail.value,
        city_error: false
      })
    }
    //判断是否有修改，如果没有就不用post了
    if (e.detail.detail.value !== this.data.info_saved.city) {
      this.setData({
        city_modified: true
      })
    }
    else {
      this.setData({
        city_modified: false
      })
    }
  },

  //每次更新education的input组件后都重新获取education
  getEducation(e) {
    this.setData({
      education: this.data.education_list[e.detail.value]
    })
    //判断是否有修改，如果没有就不用post了
    if (this.data.education_list[e.detail.value] !== this.data.info_saved.education){
      this.setData({
        education_modified: true
      })
    }
    else {
      this.setData({
        education_modified: false
      })
    }
  },

  //这个方法实现了：用户点击可选tag后，将tag加入到已选职业倾向中
  chooseTags(e) {
    var newChosen = this.data.chosen_tags;
    var hasSame = false;
    //判断已选技术中是否有重复的
    for (var index in newChosen) {
      if (newChosen[index].id === e.detail.name) {
        hasSame = true;
      }
    }
    if (hasSame === false) {
      var toChosen = this.data.tags;
      toChosen[e.detail.name].isChosen = true;
      newChosen.push(this.data.tags[e.detail.name]);
      this.setData({
        chosen_tags: newChosen,
        tags: toChosen,
        tags_modified: true,
      })
    }
    else {
      this.handleError();
    }
  },

  //这个方法实现了：用户点击已选tag后，将tag从已选中删除
  deleteTags(e) {
    var newChosen = this.data.chosen_tags;
    var toChosen = this.data.tags;
    var switchId = newChosen[e.detail.name].id;
    newChosen.splice(e.detail.name, 1);
    // 获取取消选取的tag在所有tags中的index
    var index = 0;
    for(var i in this.data.tags){
      if (this.data.tags[i].id === switchId) {
        index = i;
        break;
      }
    }
    toChosen[index].isChosen = false;
    this.setData({
      chosen_tags: newChosen,
      tags: toChosen,
      tags_modified: true
    })
  },

  //这个方法用于提示用户已选相同倾向（已废弃）
  handleError() {
    $Toast({
      content: "您已选择相同倾向",
      type: "error"
    });
  },

  //向后端发送post请求，从而修改用户的个人信息
  modify() {
    var postData = {};
    var isModified = false;
    if (this.data.phone_modified) {
      postData.phone = this.data.phone_number;
      isModified = true;
    }
    if (this.data.country_modified) {
      postData.country = this.data.country;
      isModified = true;
    }
    if (this.data.city_modified) {
      postData.city = this.data.city;
      isModified = true;
    }
    if (this.data.education_modified) {
      postData.education = this.data.education;
      isModified = true;
    }
    if (this.data.tags_modified) {
      var tags = [];
      for (var i in this.data.chosen_tags) {
        tags.push(this.data.chosen_tags[i].id);
      }
      postData.tags = tags;
      isModified = true;
    }
    if (!isModified){
      $Toast({
        content: "您没有修改个人信息！",
        type: "error"
      });
    }
    else {
      var req = new request();
      this.setData({
        isLoading: true,
      })
      console.log(postData);
      req.postRequest(host + modify_info, JSON.stringify(postData), app.globalData.access_token).then(res => {
        if (res.statusCode === 200) {
          app.globalData.showModifySuccess = true;
          wx.navigateBack({
            
          })
        }
        if (res.statusCode === 400) {

        }
      }).catch(err => {
        // console.log(err);
      });
      this.setData({
        isLoading: false
      });
    }
  }

})