/**
 * 订单页
 * 功能：按订单状态（待发货/待收货/已完成）筛选展示，van-card 渲染订单卡片
 * 数据流：flushOrders（请求后端）→ currentOrders → filterOrders（按 active 过滤）→ displayOrders（渲染）
 */
// pages/order/order.js
const request = require('../../utils/request')
// 数据区域，wxml使用{{}}读取
Page({
  data: {
    // van-tabs 标签配置
    tabs: [
      { title: '待发货', state: '0' },
      { title: '待收货', state: '1' },
      { title: '已完成', state: '2' }
    ],
    // 后端返回的全量订单（或测试数据兜底）
    currentOrders: [{
      id: 1,
      no: '202406110001',
      state: '0',
      name: '东北大米 5kg',
      goodsImg: 'https://img.yzcdn.cn/vant/cat.jpeg',
      total: 49.9
    }, {
      id: 2,
      no: '202406110002',
      state: '1',
      name: '蒙牛纯牛奶 250ml*16',
      goodsImg: 'https://img.yzcdn.cn/vant/cat.jpeg',
      total: 56
    }, {
      id: 3,
      no: '202406110003',
      state: '2',
      name: '可口可乐 330ml*24',
      goodsImg: 'https://img.yzcdn.cn/vant/cat.jpeg',
      total: 65
    }, {
      id: 4,
      no: '202406110004',
      state: '2',
      name: '奥利奥饼干 3包装',
      goodsImg: 'https://img.yzcdn.cn/vant/cat.jpeg',
      total: 99.5
    }],
    // 当前选中标签（对应 state 值）
    active: '0',
    // 根据 active 从 currentOrders 中过滤出的展示用订单
    displayOrders: []
  },

  /**
   * 从 currentOrders 中筛选出当前标签状态的订单
   * 写入 displayOrders 供 wxml 循环渲染
   */
  filterOrders() {
    const list = this.data.currentOrders.filter(o => o.state == this.data.active)
    this.setData({ displayOrders: list })
  },

  /**
   * 请求后端，获取指定状态的订单列表
   * @param {string} state - 订单状态：'0'待发货 '1'待收货 '2'已完成
   * 请求成功后将返回数据写入 currentOrders，再调 filterOrders 过滤展示
   */
  async flushOrders(state) {
    try {
      const res = await request({
        url: '/wx/order/getinfo/byid?state=' + state,
        method: 'GET'
      })
      console.log('flushOrders res:', res)
      this.setData({
        currentOrders: res.data || []
      })
      this.filterOrders()
    } catch (err) {
      console.error('flushOrders err:', err)
    }
  },

  /**
   * 页面显示生命周期
   * 每次进入订单页时，拉取当前标签对应的最新订单
   */
  onShow() {
    this.flushOrders(this.data.active)
  },

  /**
   * van-tabs 的 click 事件
   * 用户点击某个标签时触发，拉取该状态订单
   */
  tabClick(e) {
    this.flushOrders(e.detail.name)
  },

  /**
   * van-tabs 的 change 事件
   * 标签切换完成时触发（含点击和滑动），更新 active 并重新过滤
   */
  onTabChange(e) {
    this.setData({ active: e.detail.name }, () => {
      this.filterOrders()
    })
  },

  /**
   * van-card 底部按钮统一入口
   * 通过 wxml 传入的 data-action 区分操作类型：
   *   cancel  → 取消订单，state "0" → "-1"
   *   receive → 确认收货，state "1" → "2"
   *   review  → 评价（暂占位）
   * 操作成功后重新拉取当前标签的订单列表
   */
  confirm(e) {
    const dataset = e.currentTarget.dataset
    const id = dataset.id
    const action = dataset.action

    if (action === 'cancel') {
      // 取消订单：弹窗确认 → POST /wx/order/update { id, state: "-1" }
      wx.showModal({
        title: '取消订单',
        content: '确定要取消该订单吗？',
        success: (res) => {
          if (res.confirm) {
            request({
              url: '/wx/order/update',
              method: 'POST',
              data: { id: id, state: '-1' }
            }).then(() => {
              wx.showToast({ title: '已取消', icon: 'success' })
              this.flushOrders(this.data.active) // 刷新当前标签数据
            }).catch(() => {
              wx.showToast({ title: '取消失败', icon: 'error' })
            })
          }
        }
      })

    } else if (action === 'receive') {
      // 确认收货：弹窗确认 → POST /wx/order/update { id, state: "2" }
      wx.showModal({
        title: '确认收货',
        content: '确定已收到商品吗？',
        success: (res) => {
          if (res.confirm) {
            request({
              url: '/wx/order/update',
              method: 'POST',
              data: { id: id, state: '2' }
            }).then(() => {
              wx.showToast({ title: '已确认收货', icon: 'success' })
              this.flushOrders(this.data.active)
            }).catch(() => {
              wx.showToast({ title: '操作失败', icon: 'error' })
            })
          }
        }
      })

    } else if (action === 'review') {
      wx.showToast({ title: '评价功能开发中', icon: 'none' })
    }
  }
})
