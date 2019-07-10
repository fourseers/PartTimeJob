// pages/user_inform/user_inform.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    gender: false,
    name: "",
    name_error: false,
    identity: "",
    identity_error: false,
    phoneNumber: "",
    phone_error: false,
    country: "",
    country_error: false,
    city: "",
    city_error: false,
    education: "",
    educationList: ["本科以上", "本科毕业", "大专毕业", "高中毕业", "高中以下"]
  },

  //onshow的时候从后端获取当前用户的信息，填充为用户表单上的默认信息
  onShow(){

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