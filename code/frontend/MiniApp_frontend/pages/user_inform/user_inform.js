// pages/user_inform/user_inform.js
const app = getApp();
import request from "../../api/request.js";
import { host, user_info, register_data } from "../../api/url.js";

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
    country: "China",
    country_error: false,
    city: "Shanghai",
    city_error: false,
    education: "primary school",
    educationList: [],
    isLoading: false
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
  },

  //每次更新education的input组件后都重新获取education
  getEducation(e) {
    this.setData({
      education: this.data.educationList[e.detail.value]
    })
  }

})