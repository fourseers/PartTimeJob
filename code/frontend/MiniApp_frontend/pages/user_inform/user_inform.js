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
    phoneNumber: "13812345678",
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
    educationList: [],
    isLoading: false,
    info_saved: null,
  },

  onShow(){
    var req = new request();

    // 向后台获取注册元数据, 包括文化水平list、tags list
    req.getRequest(host + register_data, null).then(res => {
      if (res.statusCode === 200) {
        // 给后端返回的tags的列表中的每个json都添加isChosen字段
        var tags = res.data.tags;
        for (var index in tags) {
          tags[index].isChosen = false;
        }
        // 利用后端返回的tags和education来设置前端js的default
        this.setData({
          educationList: res.data.education,
          technology: tags,
        })
      }
      else if (res.statusCode === 400) {
        // TODO: 添加请求不返回200的处理
      }
    }).catch(err => {
      // console.log(err);
      // TODO: 添加请求失败的处理
    });

    //onshow的时候从后端获取当前用户的信息，填充为用户表单上的默认信息
    req.getRequest(host + user_info, null, app.globalData.access_token).then(res => {
      if (res.statusCode === 200) {
        var info = res.data.data.info;
        this.setData({
          name: info.name,
          gender: info.gender,
          identity: info.identity,
          phoneNumber: info.phone,
          country: info.country,
          city: info.city,
          education: info.education,
          info_saved: info
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

  //用于性别switch的切换
  changeGender(e) {
    this.setData({
      gender: !this.data.gender
    })
  },

  //每次更新name的input组件后都重新获取name
  getName(e) {
    if (e.detail.detail.value.length <= 1) {
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

  //每次更新phoneNumber的input组件后都重新获取phoneNumber
  getPhoneNumber(e) {
    var reg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;
    if (reg.test(e.detail.detail.value) === false) {
      this.setData({
        phone_error: true
      })
    }
    else {
      this.setData({
        phoneNumber: e.detail.detail.value,
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
      education: this.data.educationList[e.detail.value]
    })
    //判断是否有修改，如果没有就不用post了
    if (this.data.educationList[e.detail.value] !== this.data.info_saved.education){
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

  //向后端发送post请求，从而修改用户的个人信息
  modify() {
    var postData = {};
    var isModified = false;
    if (this.data.phone_modified) {
      postData.phone = this.data.phoneNumber;
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
      req.postRequest(host + modify_info, JSON.stringify(postDat), app.globalData.access_token).then(res => {
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