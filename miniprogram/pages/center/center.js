// pages/center/center.js
Page({
  data: {
    user: {},
    orderCount: 0,
    couponCount: 0
  },

  onShow() {
    const app = getApp()
    const userInfo = app.globalData.userInfo
    if (userInfo) {
      this.setData({
        user: userInfo
      })
    }
    // 后续可从后端拉取订单数、优惠券数
  },

  /** 跳转订单页 */
  goOrder() {
    wx.navigateTo({ url: '/pages/order/order' })
  }
})