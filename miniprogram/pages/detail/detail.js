// pages/detail/detail.js
const request = require('../../utils/request')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: 1,
    num: 1,              // 购买数量
    totalPrice: 5.8,     // 总价 = 单价 × 数量
    product: {
      picture: 'https://images.unsplash.com/photo-1568702846914-96b305d2aaeb?w=300&h=300&fit=crop',
      name: '红富士苹果',
      madein: '山东烟台',
      company: '烟台果业',
      createTime: '2025-05-10 09:00:00',
      info: '脆甜多汁，产地直发',
      price: 5.8,
      store: 500,
      typeId: 1,
      typeName: '水果蔬菜',
      unit: '斤'
    }
  },

  // 根据 id 请求商品详情
  init() {
    console.log("----init, id =", this.data.id)
    wx.request({
      url: "http://localhost:9090/wx/user/product/info-id?id=" + this.data.id,
      method: "GET",
      success: (res) => {
        console.log("----res", res)
        const result = res.data.data
        if (result) {
          this.setData({ product: result })
        }
      }
    })
  },

  // 立即购买 → 调用后端 /wx/order/users/buy 创建订单
  buyNow() {
    const product = this.data.product
    const num = this.data.num
    const totalPrice = (product.price * num).toFixed(2)
    wx.showModal({
      title: '确认购买',
      content: '确认购买「' + product.name + '」' + num + product.unit + '，¥' + totalPrice + ' ？',
      success: (res) => {
        if (res.confirm) {
          request({
            url: '/wx/order/users/buy',      // 后端购买接口
            method: 'POST',
            data: {
              productId: this.data.id,        // ① 商品ID
              no: 'ORD' + Date.now(),         // ② 订单编号（前端生成唯一号）
              name: product.name,             // ③ 商品名称
              num: num,                       // ④ 购买数量
              state: '0',                     // ⑤ 发货状态（0=待发货）
              total: totalPrice,              // ⑥ 订单总价
              goodsImg: product.picture       // ⑦ 商品图片
            }
          }).then(() => {
            wx.showToast({ title: '下单成功', icon: 'success' })
            wx.switchTab({ url: '/pages/order/order' })
          }).catch(() => {
            wx.showToast({ title: '下单失败', icon: 'error' })
          })
        }
      }
    })
  },

    // 增加数量
  addNum() {
    const store = this.data.product.store
    let num = this.data.num + 1
    if (num > store) {
      wx.showToast({ title: '库存不足', icon: 'none' })
      return
    }
    this.setData({ num, totalPrice: (num * this.data.product.price).toFixed(2) })
  },

  // 减少数量
  subNum() {
    let num = this.data.num - 1
    if (num < 1) return
    this.setData({ num, totalPrice: (num * this.data.product.price).toFixed(2) })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    console.log("接收到的参数:", options)
    // 只更新 URL 中实际传入的字段，保留 product 默认值
    const p = this.data.product
    const updateProduct = {}
    if (options.picture)     updateProduct.picture = decodeURIComponent(options.picture)
    if (options.name)        updateProduct.name = decodeURIComponent(options.name)
    if (options.madein)      updateProduct.madein = decodeURIComponent(options.madein)
    if (options.company)     updateProduct.company = decodeURIComponent(options.company)
    if (options.createTime)  updateProduct.createTime = decodeURIComponent(options.createTime)
    if (options.info)        updateProduct.info = decodeURIComponent(options.info)
    if (options.price)       updateProduct.price = options.price
    if (options.store)       updateProduct.store = options.store
    if (options.typeId)      updateProduct.typeId = options.typeId
    if (options.typeName)    updateProduct.typeName = decodeURIComponent(options.typeName)
    if (options.unit)        updateProduct.unit = decodeURIComponent(options.unit)
    this.setData({ product: { ...p, ...updateProduct } })
    if (options.id)          this.setData({ id: options.id })
    // URL 参数更新完后，根据 id 拉取完整数据（可选）
    this.init()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})
