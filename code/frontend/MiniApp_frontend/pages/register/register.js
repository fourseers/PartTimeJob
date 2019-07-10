// pages/register/register.js
const { $Toast } = require("../../dist/base/index");
const app = getApp();
import request from "../../api/request.js"
import { host, register, register_data } from "../../api/url.js"

Page({
  /*
    可以从userInfo中获取：
      用户性别gender，0位置、1男性、2女性
      用户所在国家country
      用户所在城市city
  */

  /*
    technology是所有职业倾向的数组，可以通过后端获得
    chosenTechnology是用户选择的所有职业倾向
    name是用户姓名
    identity是用户身份证号
    phoneNumber是用户手机号码
  */
  data: {
    technology: [
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
      },
      {
        id: 3,
        name: "测试行超出会发生什么情况啦啦啦啦啦啦",
        isChosen: false
      }
    ],
    educationList: ["本科以上", "本科毕业", "大专毕业", "高中毕业", "高中以下"],
    chosenTechnology: [],
    name: '',
    name_error: false,
    identity: '',
    identity_error: false,
    phoneNumber: '',
    phone_error: false,
    education: '',
    isLoading: false,
  },

  //这个方法实现了：用户点击可选tag后，将tag加入到已选职业倾向中
  chooseTechnology(e){
    var newChosen = this.data.chosenTechnology;
    var hasSame = false;
    //判断已选技术中是否有重复的
    for (var index in newChosen){
      if (newChosen[index].id === e.detail.name) {
        hasSame = true;
      }
    }
    if (hasSame === false) {
      var toChosen = this.data.technology;
      toChosen[e.detail.name].isChosen = true;
      newChosen.push(this.data.technology[e.detail.name]);
      this.setData({
        chosenTechnology: newChosen,
        technology: toChosen
      })
    }
    else{
      this.handleError();
    }
  },

  //onshow触发的时候向后台获取注册元数据
  onShow(){
    var req = new request();
    req.getRequest(host + register_data, null).then(res => {
      if(res.statusCode === 200){
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
      else if (res.statusCode === 400){
        // TODO: 添加请求失败的处理
      }
    }).catch(err => {
      // console.log(err);
      // TODO: 添加请求失败的处理
    })
  },

  //这个方法实现了：用户点击已选tag后，将tag从已选中删除
  deleteTechnology(e) {
    var newChosen = this.data.chosenTechnology;
    var toChosen = this.data.technology;
    var switchIndex = newChosen[e.detail.name].id;
    newChosen.splice(e.detail.name, 1);
    toChosen[switchIndex].isChosen = false;
    this.setData({
      chosenTechnology: newChosen,
      technology: toChosen
    })
  },

  //每次更新name的input组件后都重新获取name
  getName(e){
    if(e.detail.detail.value.length <= 1){
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
  getIdentity(e){
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if(reg.test(e.detail.detail.value) === false){
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
  getPhoneNumber(e){
    var reg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;
    if(reg.test(e.detail.detail.value) === false){
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
  getEducation(e){
    this.setData({
      education: this.data.educationList[e.detail.value]
    })
  },

  //这个方法用于提示用户已选相同倾向（已废弃）
  handleError() {
    $Toast({
      content: "您已选择相同倾向",
      type: "error"
    });
  },

  //向服务器发送请求
  //使用wx.request
  register() {
    //console.log(app.globalData.userInfo);
    if (this.data.name_error || this.data.name === ""){
      $Toast({
        content: "请输入正确的姓名",
        type: "error"
      });
    }
    else if (this.data.identity_error || this.data.identity === ""){
      $Toast({
        content: "请输入正确的身份证号",
        type: "error"
      });
    }
    else if (this.data.phone_error || this.data.phoneNumber === ""){
      $Toast({
        content: "请输入正确的手机号",
        type: "error"
      });
    }
    else if (this.data.education === ""){
      $Toast({
        content: "请选择文化水平",
        type: "error"
      });
    }
    else{
      wx.login({
        success: res => {
          this.setData({
            isLoading: true,
          })
          var req = new request();
          var postData = {
            "name": this.data.name,
            "gender": app.globalData.userInfo.gender,
            "identity": this.data.identity,
            "phone": this.data.phoneNumber,
            "country": app.globalData.userInfo.country,
            "city": app.globalData.userInfo.city,
            "education": this.data.education,
            "token": res.code
          };
          req.postRequest(host + register, JSON.stringify(postData)).then(res => {
            if (res.statusCode === 400) {
              app.globalData.isRegistered = false;
              $Toast({
                content: "注册失败",
                type: "error"
              });
              this.setData({
                isLoading: false
              })
            }
            else if (res.statusCode === 200) {
              app.globalData.isRegistered = true;
              app.globalData.showSendMessage = true;
              app.globalData.access_token = res.data.data.access_token;
              app.globalData.expires_in = res.data.data.expires_in;
              app.globalData.refresh_token = res.data.data.refresh_token;
              wx.navigateBack({
                
              })
            }
            else {
              app.globalData.isRegistered = false;
            }
          }).catch(err => {
            console.log(err)
          })
        }
      })
    }
  }

})