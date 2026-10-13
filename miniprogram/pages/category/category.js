// pages/category/category.js   当前页面：分类页（tabBar 第二个）
// 导入封装的网络请求工具，路径从当前 pages/category/ 向上两级到项目根目录的 utils/request.js
const request = require('../../utils/request')

// Page() 是微信小程序注册页面的方法，传入一个对象，对象里包含数据和方法
Page({

  /**
   * 页面的初始数据  —— data 里的变量可以在 wxml 中用 {{ }} 直接使用
   */
  data: {
    // navItems 是左侧导航栏的数据，页面最初显示的占位数据，之后会被 loadItems 请求到的真数据覆盖
    navItems: [
      { text: "粮油" }, { text: "水产" }, { text: "肉禽" }, { text: "蔬菜" }, { text: "水果" }, { text: "酒水" }, { text: "粮油" },
    ],
    currentnavIndex: 0,
    currentProducts: []
  },

  // 点击左侧导航项时触发，e.detail 包含被点击项的索引等信息
  async clickNav(e) {
    console.log("点击导航:", e.detail)
    // 设置当前导航项的索引，页面会自动重新渲染
    this.setData({
      currentnavIndex: e.detail.index
    })
    await this.flushProducts()
  },
  lookfor(e) {
    const id = e.currentTarget.dataset.id
    console.log("点击了, 商品id:", id)
    wx.navigateTo({
      url: '/pages/detail/detail?id=' + id
    })
  },
  async flushProducts() {
    // 根据索引从 navItems 数组中取出当前被点击的分类对象
    const currentNav = this.data.navItems[this.data.currentnavIndex]
    console.log("当前活跃的是", currentNav)
    const num = currentNav.attachment.id   // 取出该分类的 id

    // 根据分类 id 请求该分类下的商品列表
    const res = await request({
      url: "http://localhost:9090/wx/user/product/category?num=" + num,
      method: "GET"
    })
    console.log("该分类商品:", res)
    // 把请求到的商品列表存入 data，页面会自动重新渲染
    this.setData({
      currentProducts: res.data
    })
  },
  // async 表示这是一个异步函数，里面可以用 await 等待请求完成
  async loadItems() {
    // await 会等待 request() 返回结果，拿到结果后才继续往下执行
    const res = await request({
      url: "http://localhost:9090/wx/user/product/type",  // 请求商品分类数据
      method: "GET"
    })

    // res 是服务器返回的完整响应体，例如 { code: 200, data: [...], message: "ok" }
    // console.log("原始数据:", res)
    // res.data 才是真正的分类数组，例如 [{ type:"粮油", id:1 }, ...]
    // console.log("原始数据:", res.data)
    // 当前页面的 navItems（此时还是初始的占位数据）
    // console.log(this.data.navItems)

    // 格式化数据：将服务器返回的 type 字段转为 tree-select 需要的 text 字段
    // map() 遍历数组的每一项，处理后返回一个新数组，原数组不变
    const r = res.data.map((e) => {
      // e 是数组中的每一项（一个商品分类对象）
      return {
        text: e.type,         // 取出 type 值，重命名为 text（tree-select 组件要求）
        attachment: e          // 把原始对象也存下来，方便后续使用其他字段
      }
    })
    // console.log("格式化后:", r)

    // 将格式化后的数据设置到页面中，是用来更新页面数据的，用{}包裹，{}相当于一个对象，对象中有一个navItems属性，值为r，r是格式化后的数据
    this.setData({
      navItems: r    // 更新 data.navItems，页面会自动重新渲染
    })
  },

  /**
   * 生命周期函数--监听页面加载  —— 页面第一次打开时执行，只会执行一次
   */
  async onLoad(options) {
    // ① 先加载分类列表
    await this.loadItems()
    // ② 默认选中第一个分类，加载其商品列表
    this.setData({ currentnavIndex: 0 })
    await this.flushProducts()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示  —— 每次切入该页面时执行（切走再切回来也会执行）
   */
  onShow() {
    // 每次切入分类页时，刷新当前分类的商品列表
    if (this.data.navItems.length > 0) {
      this.flushProducts()
    }
  },

  /**
   * 生命周期函数--监听页面隐藏  —— 页面被切走（导航到其他 tab 或页面）时执行
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载  —— 页面被关闭/销毁时执行
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数  —— 列表滚动到底部时触发，常用于加载更多
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享  —— 返回的对象会作为分享卡片的内容
   */
  onShareAppMessage() {

  }
})