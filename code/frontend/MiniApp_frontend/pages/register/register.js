// pages/register/register.js
const { $Toast } = require("../../dist/base/index");

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
    chosenTechnology: [],
    name: '',
    identity: '',
    phoneNumber: ''
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
    this.setData({
      name: e.detail.detail.value
    })
  },

  //每次更新identity的input组件后都重新获取identity
  getIdentity(e){
    this.setData({
      identity: e.detail.detail.value
    })
  },

  //每次更新phoneNumbery的input组件后都重新获取phoneNumber
  getPhoneNumber(e){
    this.setData({
      phoneNumber: e.detail.detail.value
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
  Register() {
    
  }

})