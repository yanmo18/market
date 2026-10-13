# Market 微信小程序商城（校内实训）

将原先分散的 3 个仓库合并为 **一个 monorepo**：

| 目录 | 原仓库 | 说明 |
|------|--------|------|
| `miniprogram/` | [yanmo18/market](https://github.com/yanmo18/market) | 微信原生小程序（Vant Weapp） |
| `admin/` | [yanmo18/market-manage-main-PC](https://github.com/yanmo18/market-manage-main-PC) | Vue 管理后台（PC） |
| `server/` | [yanmo18/market-back-master](https://github.com/yanmo18/market-back-master) | Spring Boot + MyBatis-Plus 后端 |

Gitee 镜像可同步本仓库：https://gitee.com/yanqinglanyanmo/market

## 功能概览

- 小程序：首页/分类/订单/我的/商品详情，`wx.login` + 统一 request
- 管理端：商品 / 分类 / 订单 / 用户管理
- 后端：用户、商品、分类、订单、文件上传；含微信端 `controller/wx/*` 与 JWT

## 快速开始

### 1. 后端 `server/`

```bash
cd server
# 导入 sql/ 下脚本，修改 src/main/resources/application.yml 数据库配置
mvn spring-boot:run
```

### 2. 管理端 `admin/`

```bash
cd admin
npm install
npm run serve
```

### 3. 小程序 `miniprogram/`

```bash
cd miniprogram
npm install
# 微信开发者工具导入本目录，并「工具 → 构建 npm」
```

把小程序里的 API 基地址改成你的后端地址（见 `miniprogram/utils/request.js`）。

## 旧仓库说明

合并后请把下面仓库标为 **Archived**，README 指向本仓库，避免继续往旧仓提交：

- `market-back-master`
- `market-manage-main-PC`

（历史代码仍保留在原仓库，便于对照。）
