// app.js
import request from './utils/request'
// 导入封装的网络请求工具，路径从当前 app.js 向上两级到项目根目录的 utils/request.js
// App() 是微信小程序注册应用的入口，传入一个对象，对象里包含数据和方法
App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
   wx.login({
      success: async  res => {
        console.log("登录成功:", res)
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        const res2=await request({
          url:"/wx/login/byCode?code="+res.code,
          method:"post"
        })
        console.log("登录成功2:", res2)
        this.globalData.userInfo=res2.data
        wx.setStorageSync("token",res2.data.token)
      }
    })
  },
  globalData: {
    userInfo: null
  }
})
