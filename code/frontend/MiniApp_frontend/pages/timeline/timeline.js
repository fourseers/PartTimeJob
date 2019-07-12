// pages/timeline/timeline.js
Page({

  /**
   * 页面的初始数据
   * arrangement 0未安排、1已安排、2待添加、3待删除
   */
  data: {
    date: "周五",
    week: ["周一", "周二", "周三", "周四", "周五", "周六", "周日",],
    division: ["上午", "中午", "下午", "晚上"],
    arrangement: [[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]],
    cell_classes: [
      {
        class: "cell unarranged unselected",
        name: "未安排",
        id: 0
      },
      {
        class: "cell arranged selected",
        name: "已安排",
        id: 1
      },
      {
        class: "cell unarranged selected",
        name: "待添加",
        id: 2
      },
      {
        class: "cell arranged to-delete",
        name: "待删除",
        id: 3
      },
    ]
  },

  handleTap(e){
    //console.log(e);
    var new_arrange = this.data.arrangement;
    var date = e.currentTarget.id % 7;
    var divide = parseInt(e.currentTarget.id / 7);
    if (new_arrange[date][divide] == 0) {
      new_arrange[date][divide] = 2;
    }
    else if (new_arrange[date][divide] == 2) {
      new_arrange[date][divide] = 0;
    }
    else if (new_arrange[date][divide] == 1) {
      new_arrange[date][divide] = 3;
    }
    else if (new_arrange[date][divide] == 3) {
      new_arrange[date][divide] = 1;
    }

    this.setData({
      arrangement: new_arrange
    })
  },

  handleModify(){
    var new_arrange = this.data.arrangement;
    for(var date in this.data.arrangement){
      for(var divide in this.data.arrangement[date]){
        if (new_arrange[date][divide] == 2) {
          new_arrange[date][divide] = 1;
        }
        if (new_arrange[date][divide] == 3) {
          new_arrange[date][divide] = 0;
        }
      }
    }
    this.setData({
      arrangement: new_arrange
    })
  },

})