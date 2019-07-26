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
    tags是所有职业倾向的数组，可以通过后端获得
    chosen_tags是用户选择的所有职业倾向
    name是用户姓名
    identity是用户身份证号
    phone_number是用户手机号码
    ()_error用于显示用户是否输入异常值
    education_list是所有备选学历
    education是用于已选学历
    isLoading用于决定用户请求是否正在发送，如正在发送就在按钮处显示loading动画
  */
  data: {
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
      },
      {
        id: 3,
        name: "测试行超出会发生什么情况啦啦啦啦啦啦",
        isChosen: false
      }
    ],
    chosen_tags: [],
    education_list: ["本科以上", "本科毕业", "大专毕业", "高中毕业", "高中以下"],
    gender: true,
    name: "",
    name_error: false,
    identity: "",
    identity_error: false,
    phone_number: "",
    phone_error: false,
    country: "",
    country_error: false,
    city: "",
    city_error: false,
    education: "",
    education_list: [],
    isLoading: false,
    city_code: ""
  },

  /* 
   * onshow触发的时候向后台获取注册元数据
   * 元数据包括education_list和tags
   */
  onShow(){
    //console.log(app.globalData.userInfo);
    var req = new request();
    req.getRequest(host + register_data, null).then(res => {
      if(res.statusCode === 200){
        // 给后端返回的tags的列表中的每个json都添加isChosen字段
        var tags = res.data.data.tags;
        for (var index in tags) {
          tags[index].isChosen = false;
        }
        // 利用后端返回的tags和education来设置前端js的default
        this.setData({
          education_list: res.data.data.education,
          tags: tags,
          city: app.globalData.userInfo.city,
          country: app.globalData.userInfo.country,
          gender: app.globalData.userInfo.gender
        })
      }
      else if (res.statusCode === 400){
        // TODO: 添加请求失败的处理
      }
    }).catch(err => {
      // console.log(err);
      // TODO: 添加请求失败的处理
    });

    if (app.globalData.city !== null) {
      console.log(app.globalData.city);
      this.setData({
        city: app.globalData.city,
        city_code: app.globalData.code,
      })
      app.globalData.city = null;
      app.globalData.code = null;
    }
  },

  //这个方法实现了：用户点击可选tag后，将tag加入到已选职业倾向中
  chooseTags(e) {
    var newChosen = this.data.chosen_tags;
    var has_same = false;
    //判断已选技术中是否有重复的
    for (var index in newChosen) {
      if (newChosen[index].id === e.detail.name) {
        has_same = true;
      }
    }
    if (has_same === false) {
      var toChosen = this.data.tags;
      toChosen[e.detail.name].isChosen = true;
      newChosen.push(this.data.tags[e.detail.name]);
      this.setData({
        chosen_tags: newChosen,
        tags: toChosen
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
    for (var i in this.data.tags) {
      if (this.data.tags[i].id === switchId) {
        index = i;
        break;
      }
    }
    toChosen[index].isChosen = false;
    this.setData({
      chosen_tags: newChosen,
      tags: toChosen
    })
  },
  
  //用于性别switch的切换
  changeGender(e) {
    this.setData({
      gender: !this.data.gender
    })
  },

  //每次更新name的input组件后都重新获取name
  getName(e){
    var reg = /^[\u4E00-\u9FA5A-Za-z]+$/;
    if ((e.detail.detail.value.length <= 1) || reg.test(e.detail.detail.value) === false){
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

  //每次更新phone_number的input组件后都重新获取phone_number
  getPhoneNumber(e){
    var reg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;
    if(reg.test(e.detail.detail.value) === false){
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
  },

  //每次更新education的input组件后都重新获取education
  getEducation(e){
    this.setData({
      education: this.data.education_list[e.detail.value]
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
    else if (this.data.phone_error || this.data.phone_number === ""){
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
      this.setData({
        isLoading: true,
      })
      wx.login({
        success: res => {
          var req = new request();
          var tagIDs = [];
          for (var i in this.data.chosen_tags) {
            tagIDs.push(this.data.chosen_tags[i].id);
          }
          var postData = {
            "name": this.data.name,
            "gender": this.data.gender,
            "identity": this.data.identity,
            "phone": this.data.phone_number,
            "country": this.data.country,
            "city": this.data.city,
            "education": this.data.education,
            "token": res.code,
            "tags": tagIDs
          };
          req.postRequest(host + register, JSON.stringify(postData)).then(res => {
            if (res.statusCode === 400) {
              app.globalData.is_registered = false;
              $Toast({
                content: "注册失败",
                type: "error"
              });
            }
            else if (res.statusCode === 200) {
              app.globalData.is_registered = true;
              app.globalData.showSendMessage = true;
              app.globalData.access_token = res.data.data.access_token;
              app.globalData.expires_in = res.data.data.expires_in;
              app.globalData.refresh_token = res.data.data.refresh_token;
              wx.navigateBack({
                
              })
            }
            else {
              app.globalData.is_registered = false;
            }
            this.setData({
              isLoading: false
            });
          }).catch(err => {
            console.log(err)
            this.setData({
              isLoading: false
            });
          });
        }
      })
    }
  }

})