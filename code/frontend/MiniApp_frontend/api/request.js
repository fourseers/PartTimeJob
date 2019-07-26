/*
 * how to use:
 * 
  Afunction(){
    new request().postRequest(app.globalData.host + login, {
      "token": "testAPI"
    }).then(res => {
      console.log(res)
    }).catch(err => {
      console.log(err)
    })
  },
  */
class request {
  constructor() {
    this._header = {
      "Content-Type": "application/json; charset=UTF-8",
      "Authorization": "Basic d2VjaGF0Q2xpZW50OjEyMzQ1Ng==",
    }
  }

  getRequest(url, data, token = null, header = this._header) {
    if (token !== null){
      header["x-access-token"] = token;
    }
    return this.requestAll(url, data, header, 'GET');
  }

  postRequest(url, data, token = null, header = this._header) {
    if (token !== null) {
      header["x-access-token"] = token;
    }
    return this.requestAll(url, data, header, 'POST')
  }

  putRequest(url, data, token = null, header = this._header) {
    if (token !== null) {
      header["x-access-token"] = token;
    }
    return this.requestAll(url, data, header, 'PUT')
  }

  deleteRequest(url, data, token = null, header = this._header) {
    if (token !== null) {
      header["x-access-token"] = token;
    }
    return this.requestAll(url, data, header, 'DELETE')
  }

  requestAll(url, data, header, method){
    return new Promise((resolve, reject) => {
      wx.request({
        url: url,
        data: data,
        header: header,
        method: method,
        success: (res => {
          resolve(res)
        }),
        fail: (res => {
          reject(res)
        })
      })
    })
  }
}

export default request