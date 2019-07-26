// pages/shop/shop.js
Page({

  /**
   * 页面的初始数据
   * name: 商家名字
   * province： 商家省份
   * city： 商家城市
   * address：商家地址
   * longitude、latitude：经纬度
   * brand：品牌
   * industry： 行业
   * introduction： 简介
   * avg_score： 后端评分的均分
   * rate： 前端用户的评分 必须为整数！
   */
  data: {
    name: "咸鱼",
    province: "上海市",
    city: "上海市",
    address: "闵行区上海交通大学",
    longitude: 0.0,
    latitude: 0.0,
    brand: "软件学院",
    industry: "搬砖前端",
    introduction: "这是一个写前端页面的搬砖工作",
    avg_score: 5,
    rate: 0,
  },

  handleRate(e){
    this.setData({
      rate: e.detail.value
    })
  }

})