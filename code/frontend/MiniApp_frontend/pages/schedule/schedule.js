// pages/schedule/schedule.js
Page({

  /**
   * 页面的初始数据
   * timeList是0-23小时
   * schedule是每个小时对应的状态，其中:
   *    0: 未安排，1：待添加，2：已安排，3：待删除
   * scheduleSave用于保存一份用于的初始安排
   * week是一周7天
   * scheduleClass用于存放table-cell的class
   * newAdd用于判断用户是否进行了修改（只有进行过修改才能按按钮）
   * tags用于告诉用户4种颜色分别表示什么意思
   */
  data: {
    timeList: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23],
    schedule: [
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0],
      [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2],
      [0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0],
      [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2],
      [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2],
      [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2],
    ],
    scheduleSave: [
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0],
      [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2],
      [0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0],
      [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2],
      [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2],
      [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2],
    ],
    week: [0, 1, 2, 3, 4, 5, 6],
    scheduleClass: ["cell", "cell choose-g", "scheduled-cell", "cell delete-g"],
    newAdd: true,
    tags: [
      {
        class: "color-item scheduled",
        name: "已安排"
      },
      {
        class: "color-item none",
        name: "未安排"
      },
      {
        class: "color-item add",
        name: "待添加"
      },
      {
        class: "color-item delete",
        name: "待删除"
      }
    ]
  },

  onLoad(){

  },

  // 按下任意一个时间块后触发
  handleTap(e) {
    var newSchedule = this.data.scheduleSave;
    //console.log(this.data.scheduleSave);
    if(e.target.id !== ""){
      var chosenId = parseInt(e.target.id);
      var chosenDate = parseInt(e.target.id / 24);
      var chosenTime = chosenId - chosenDate * 24;
      if (newSchedule[chosenDate][chosenTime] === 0) {
        newSchedule[chosenDate][chosenTime] = 1;
      }
      else if (newSchedule[chosenDate][chosenTime] === 1) {
        newSchedule[chosenDate][chosenTime] = 0;
      }
      else if (newSchedule[chosenDate][chosenTime] === 2) {
        newSchedule[chosenDate][chosenTime] = 3;
      }
      else if (newSchedule[chosenDate][chosenTime] === 3) {
        newSchedule[chosenDate][chosenTime] = 2;
      }
      this.setData({
        schedule: newSchedule,
        newAdd: false
      })
    }
  },

  // 按下“修改”button后触发
  handleAdd() {
    var newSchedule = this.data.schedule;
    for (var i in newSchedule) {
      for (var j in newSchedule[i]) {
        if (newSchedule[i][j] == 1) {
          newSchedule[i][j] = 2;
        }
        else if (newSchedule[i][j] == 3) {
          newSchedule[i][j] = 0;
        }
      }
    }
    this.setData({
      schedule: newSchedule,
      newAdd: true
    })
  }
})