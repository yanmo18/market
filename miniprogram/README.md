# Market 微信小程序

一个基于微信小程序的电商市场应用。

## 技术栈

- **框架**: 微信小程序原生框架
- **UI组件库**: [Vant Weapp](https://vant-ui.github.io/vant-weapp/)
- **云开发**: 腾讯云 CloudBase

## 项目结构

```
├── assets/          # 静态资源
│   └── img/         # 图片资源
├── miniprogram_npm/ # npm 依赖包
├── pages/           # 页面目录
│   ├── welcome/     # 欢迎页
│   ├── category/    # 分类页
│   ├── order/       # 订单页
│   ├── center/      # 个人中心页
│   ├── detail/      # 商品详情页
│   └── logs/        # 日志页
├── utils/           # 工具函数
│   ├── request.js   # 网络请求封装
│   └── util.js      # 通用工具函数
├── app.js           # 小程序入口文件
├── app.json         # 小程序配置文件
├── app.wxss         # 全局样式文件
├── package.json     # npm 配置文件
├── project.config.json  # 项目配置文件
└── sitemap.json     # 站点地图配置
```

## 功能模块

| 页面 | 路径 | 说明 |
|------|------|------|
| 首页 | `pages/welcome/welcome` | 商品展示、轮播图、导航入口 |
| 分类 | `pages/category/category` | 商品分类浏览 |
| 订单 | `pages/order/order` | 订单列表管理 |
| 我的 | `pages/center/center` | 用户个人中心 |
| 详情 | `pages/detail/detail` | 商品详情展示 |

## 快速开始

### 环境要求

- 微信开发者工具
- Node.js (用于安装依赖)

### 安装步骤

1. **克隆项目**

2. **安装依赖**
   ```bash
   npm install
   ```

3. **打开项目**
   - 打开微信开发者工具
   - 选择"导入项目"
   - 选择项目根目录
   - 填入 AppID: `wx763df0d493c0d850`

4. **运行项目**
   - 点击"编译"按钮
   - 在模拟器中预览效果

## 核心配置

### TabBar 导航

小程序底部导航栏包含四个入口：首页、分类、订单、我的。图标位于 `assets/img/taber/` 目录。

### 网络请求

项目封装了统一的网络请求工具 `utils/request.js`，支持 Promise 调用方式。

### 登录流程

应用启动时自动调用 `wx.login()` 获取 code，然后发送到后端换取用户信息和 token，存储在本地缓存中。

## 开发说明

- 使用 ES6+ 语法，微信开发者工具已配置 ES6 转 ES5
- 使用 PostCSS 进行样式处理
- 支持代码压缩和混淆
- 使用 Vant Weapp 组件库构建 UI

## 许可证

ISC
