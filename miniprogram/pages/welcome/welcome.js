// pages/welcome/welcome.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    swiperImgs: [
      "../../assets/img/banner/1.png",
      "../../assets/img/banner/2.png",
      "../../assets/img/banner/3.png",
      "../../assets/img/banner/4.png",
      "../../assets/img/banner/5.png"
    ],
    results: [
      { picture: "../../assets/img/banner/1.png", name: "测试商品", madein: "中国", price: "99" }
    ],
    navList: [
      { icon: "../../assets/img/nav/1.png", text: "热销榜单" },
      { icon: "../../assets/img/nav/2.png", text: "新品上市" },
      { icon: "../../assets/img/nav/3.png", text: "限时秒杀" },
      { icon: "../../assets/img/nav/4.png", text: "特色推荐" },
      { icon: "../../assets/img/nav/5.png", text: "积分商城" },
      { icon: "../../assets/img/nav/6.png", text: "更多分类" },
      { icon: "../../assets/img/banner/1.png", text: "001" },
      { icon: "../../assets/img/banner/2.png", text: "002" },
      { icon: "../../assets/img/banner/3.png", text: "003" },
      { icon: "../../assets/img/banner/4.png", text: "004" }
    ]
  },
  test() {
    console.log("test")
    wx.request({
      url: "http://localhost:9090/wx/user/product/info",
      method: "GET",
      success: (res) => {
        console.log(res)
        this.setData({
          results: res.data.data
        })
      },
      fail: (res) => {
        console.log(res)
      }
      // 显示成功与失败的回调函数
    })
  },
  // 点击事件 bind:tap="lookfor" —— 跳转到商品详情页
  lookfor(event) {
    const d = event.currentTarget.dataset
    console.log("当前去往商品 id：", d.id)
    wx.navigateTo({
      url: `/pages/detail/detail?id=${d.id || ''}&picture=${encodeURIComponent(d.picture || '')}&name=${encodeURIComponent(d.name || '')}&madein=${encodeURIComponent(d.madein || '')}&price=${d.price || ''}`
    })
  },
  // enter(){
  //   console.log("enter")
  //   wx.request({
  //     url:"http://localhost:9090/wx/user/product/info",
  //     method:"GET",
  //     success:(res)=>{
  //       console.log(res)
  //     }
  //   })
  // },
  // go() {
  //   console.log("xxxxx","yyy")
  //   console.log(this.data.swiperImgs)
  //   wx.request({
  //     url: 'http://localhost:9090/wx/user/product/info',
  //     method: 'GET',
  //     success: (res) => {
  //       console.log(res)
  //       console.log("有用",res.data.data)
  //     }
  //   })
  // },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    console.log("页面加载了")
    console.log("页面接收到的参数 options：", options)
    this.test()
    if (options.id) {
      // 例如从分享链接或推送消息进入，携带了商品 id，直接跳至详情页
      wx.navigateTo({
        url: `/pages/detail/detail?id=${options.id}`
      })
    }
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
