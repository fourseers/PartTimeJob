const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatBeginDate = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()

  return [year, month, day].map(formatNumber).join('-')
}

const formatEndDate = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 2
  const day = date.getDate()

  return [year, month, day].map(formatNumber).join('-')
}

const formatDate = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()

  return [year, month, day].map(formatNumber).join('-')
}

const getDate = str => {
  var temp = str.split("-");
  var date = new Date(temp[0], temp[1]-1, temp[2]);
  return date;
}

const getDates = date_range => {
  var result = Array()
  console.log(date_range)
  for (var i in date_range) {
    var start = date_range[i].begin_date;
    var end = date_range[i].end_date;
    var startTime = getDate(start);
    var endTime = getDate(end);
    while (true) {
      var date = formatDate(startTime)
      result.push(date);
      if ((startTime.getDate() === endTime.getDate()) && (startTime.getMonth() === endTime.getMonth()) && (startTime.getFullYear() === endTime.getFullYear())) {
        break;
      }
      startTime.setDate(startTime.getDate() + 1);
    }
    
  }
  return result;
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

module.exports = {
  formatTime: formatTime,
  formatBeginDate: formatBeginDate,
  formatEndDate: formatEndDate,
  formatDate: formatDate,
  getDates: getDates
}
