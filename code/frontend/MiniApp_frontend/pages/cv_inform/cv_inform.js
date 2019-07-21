// pages/cv_inform/cv_inform.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    experiences: ["经历1"],
  },

  addExperience() {
    var newExp = this.data.experiences;
    newExp[newExp.length] = "经历" + (newExp.length + 1);
    this.setData({
      experiences: newExp,
    })
  }
})