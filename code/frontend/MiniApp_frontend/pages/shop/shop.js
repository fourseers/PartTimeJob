// pages/shop/shop.js
const { $Toast } = require('../../dist/base/index');
var shop_id;
const app = getApp();
import request from "../../api/request.js"
import { host, shop_inform, shop_score } from "../../api/url.js"

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
    avg_score: 0,
    avg_rate: 0,
    rate: 0,
    markers: [],
    is_disabled: true,
    is_loading: false,
  },

  onLoad(options) {
    shop_id = options.id;
  },

  onShow() {
    var req = new request();

    req.getRequest(host + shop_inform + shop_id, null, app.globalData.access_token).then(res => {
      if(res.statusCode === 200){
        var data = res.data.data;
        var rate;
        var avg_rate = parseInt(data.avg_score + 0.5);

        if (data.user_score === null) {
          rate = 0;
        }
        else {
          rate = data.user_score;
        }
        this.setData({
          address: data.address,
          avg_score: (data.avg_score === null) ? "暂无评" : data.avg_score,
          avg_rate: avg_rate,
          brand: data.brand,
          city: data.brand,
          industry: data.industry,
          introduction: data.introduction,
          latitude: data.latitude,
          longitude: data.longitude,
          markers: [{
            id: 1,
            latitude: data.latitude,
            longitude: data.longitude,
            name: data.shop_name
          }],
          province: data.province,
          name: data.shop_name,
          rate: rate
        })
      }
    }).catch(err => {
      console.log(err);
    })
  },

  handleRate(e){
    this.setData({
      rate: e.detail.value,
      is_disabled: false
    })
  },

  handleScore(){
    this.setData({
      is_loading: true
    })
    var req = new request();
    var post_data = {
      score: this.data.rate,
      shop_id: shop_id
    };
    req.postRequest(host + shop_score, post_data, app.globalData.access_token).then(res => {
      if(res.statusCode === 200){
        $Toast({
          content: "评分成功",
          type: 'success'
        });
      }
      else if(res.statusCode === 400){
        $Toast({
          content: res.data.message,
          type: 'error'
        });
      }
      this.setData({
        is_loading: false
      })
    }).catch(err => {
      console.log(res);
    })
  }

})