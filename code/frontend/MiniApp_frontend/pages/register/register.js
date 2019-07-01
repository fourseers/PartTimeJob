// pages/register/register.js
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
        id: 1,
        name: "厨师"
      },
      {
        id: 2,
        name: "收银"
      },
      {
        id: 3,
        name: "打杂"
      }
    ],
    currentTechnology: [],
    name: '',
    identity: '',
    phoneNumber: ''
  },

  handleTechnologyChange({ detail = {} }) {
    const index = this.data.currentTechnology.indexOf(detail.value);
    index === -1 ? this.data.currentTechnology.push(detail.value) : this.data.currentTechnology.splice(index, 1);
    this.setData({
      currentTechnology: this.data.currentTechnology
    });
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

  Register: function () {
    
  }

})