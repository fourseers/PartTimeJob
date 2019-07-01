// pages/register/register.js
const { $Toast } = require('../../dist/base/index');

Page({
  /*
    可以从userInfo中获取：
      用户性别gender，0位置、1男性、2女性
      用户所在国家country
      用户所在城市city
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

  chooseTechnology: function(e){
    var newChosen = this.data.chosenTechnology;
    var hasSame = false;
    //判断已选技术中是否有重复的
    for (var index in newChosen){
      if (newChosen[index].id == e.detail.name)
        hasSame = true;
    }
    if (hasSame == false) {
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

  deleteTechnology: function(e) {
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

  getName: function(e){
    this.setData({
      name: e.detail.detail.value
    })
  },

  getIdentity: function(e){
    this.setData({
      identity: e.detail.detail.value
    })
  },

  getPhoneNumber: function(e){
    this.setData({
      phoneNumber: e.detail.detail.value
    })
  },

  handleError() {
    $Toast({
      content: '您已选择相同倾向',
      type: 'error'
    });
  },

  //向服务器发送请求
  Register: function () {
    
  }

})