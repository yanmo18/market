const baseUrl = 'http://localhost:9090' // 替换为实际域名

const request = (config = {
  url: '',
  method: "GET",
  data: null
}) => {
  return new Promise((resolve, reject) => {

    let token = wx.getStorageSync("token")
    const header = {}
    if (token) {
      header.Authorization = token
    }
    if (config.url && !config.url.startsWith("http")) {
      config.url = baseUrl + config.url
    }
    wx.request({
      ...config,
      header: header,
      timeout: 10000,
      success: (res) => {
        if (res.data.code) {
          // 统一返回值封装
          if (res.data.code == 200) {
            resolve(res.data)
          } else {
            console.log("请求成功，业务异常分析:", res.data)
            if (res.data.code == 400 ) {
              console.log("明确的异常：参数错误")
              reject(res.data)
            }else if(res.data.code == 401 ) {
              console.log("明确的异常：权限不足")
              reject(res.data)
            }  
            else {
              wx.showToast({
                title: "请求成功，但不明业务异常: " + res.data.code + JSON.stringify(res.data.message),
                icon: 'none'
              })
            }
          }
        } else {
          // 未统一返回值封装
          resolve(res.data)
        }
      },
      fail: (error) => {
        wx.showToast({
          title: "请求失败: " + JSON.stringify(error),
          icon: 'none'
        })
      }
    })

  })
}

module.exports = request
module.exports.default = request