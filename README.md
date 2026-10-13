# Market 微信小程序商城

校内实训项目：将原先分散的 3 个仓库合并为 **一个 monorepo**，包含微信小程序端、Vue 管理后台与 Spring Boot 后端。

| 目录 | 原仓库 | 说明 |
|------|--------|------|
| `miniprogram/` | [yanmo18/market](https://github.com/yanmo18/market) | 微信原生小程序（Vant Weapp） |
| `admin/` | [yanmo18/market-manage-main-PC](https://github.com/yanmo18/market-manage-main-PC) | Vue 2 + Element UI 管理后台（PC） |
| `server/` | [yanmo18/market-back-master](https://github.com/yanmo18/market-back-master) | Spring Boot + MyBatis-Plus 后端 |

Gitee 镜像：https://gitee.com/yanqinglanyanmo/market

---

## 功能概览

| 端 | 能力 |
|----|------|
| **小程序** | 首页 / 分类 / 订单 / 我的 / 商品详情；`wx.login` + 统一 request；JWT 鉴权 |
| **管理端** | 商品 / 分类 / 订单 / 用户（管理员与顾客）管理；首页图表；文件上传 |
| **后端** | 用户、商品、分类、订单、文件上传；管理端 REST + 微信端 `controller/wx/*` |

---

## 技术栈

| 层 | 技术 |
|----|------|
| 小程序 | 微信原生、Vant Weapp、`@vant/weapp` |
| 管理端 | Vue 2、Vue Router、Vuex、Element UI、Axios、ECharts、Vue CLI 5 |
| 后端 | Java 8、Spring Boot 2.5、MyBatis / MyBatis-Plus、MySQL、JWT、Hutool、Springfox Swagger 3、POI |
| 数据库 | MySQL（库名 `smarket`），脚本见 `server/sql/init.sql` |

---

## 目录结构

```
market/
├── 启动.bat / start.bat      # 一键启动（后端 + 管理端）
├── 停止.bat / stop.bat        # 停止本地服务
├── scripts/
│   ├── init-db.bat            # 导入初始化 SQL
│   ├── start-server.bat       # 仅启动后端
│   ├── start-admin.bat        # 仅启动管理端
│   └── prepare-miniprogram.bat# 安装小程序 npm 依赖
├── admin/                     # Vue 管理后台
├── miniprogram/               # 微信小程序
└── server/                    # Spring Boot 后端
    ├── sql/init.sql
    └── src/main/resources/application.yml
```

---

## 环境要求

| 依赖 | 建议版本 | 用途 |
|------|----------|------|
| JDK | 1.8+ | 后端 |
| Maven | 3.6+ | 构建 / 运行后端 |
| Node.js | 16+（含 npm） | 管理端与小程序依赖 |
| MySQL | 5.7 / 8.0 | 数据库 |
| 微信开发者工具 | 最新稳定版 | 运行小程序 |

首次使用前请确认 `mvn`、`node`、`npm` 已加入系统 PATH。

---

## 一键启动（推荐）

> 原先**没有**一键启动文件，现已在仓库根目录提供。

### 第一次使用

1. **启动 MySQL**，并导入数据库（二选一）：
   - 双击 `scripts\init-db.bat`（需 `mysql` 客户端在 PATH，按提示输入 root 密码）
   - 或用 Navicat / MySQL Workbench 执行 `server/sql/init.sql`
2. 检查 `server/src/main/resources/application.yml` 中的数据库账号、密码、上传目录是否与本机一致。
3. 双击根目录 **`启动.bat`**（或 `start.bat`）。

脚本会自动：

- 尝试检测 / 启动 MySQL80
- 创建上传目录 `D:\pic\`（若不存在）
- 启动后端 `http://localhost:9090`
- 首次自动 `npm install` 并启动管理端 `http://localhost:8080`
- 打开管理后台页面

停止服务：双击 **`停止.bat`**（或 `stop.bat`），或关掉弹出的 `market-server` / `market-admin` 窗口。

### 默认账号

| 角色 | 用户名 | 密码 | 说明 |
|------|--------|------|------|
| 管理员 | `admin` | `123456` | 管理端登录（`sys_user`，密码为 MD5） |
| 管理员 | `manager` / `operator` | `123456` | 测试账号 |
| 顾客示例 | `zhangsan` 等 | `123456` | 表 `sys_users`，供业务演示 |

### 小程序（需单独打开）

```bat
scripts\prepare-miniprogram.bat
```

然后用微信开发者工具导入 `miniprogram/` 目录 → **工具 → 构建 npm** → 编译。

- AppID：见 `miniprogram/project.config.json`（当前 `wx763df0d493c0d850`）
- API 基地址：`miniprogram/utils/request.js`（默认 `http://localhost:9090`）
- 真机调试时，请把基地址改成电脑局域网 IP，并在开发者工具中勾选「不校验合法域名」

---

## 手动启动

### 1. 数据库

```sql
-- 执行 server/sql/init.sql
-- 将创建库 smarket，并写入分类 / 商品 / 订单 / 用户等测试数据
```

### 2. 后端 `server/`

编辑 `src/main/resources/application.yml`：

```yaml
server:
  port: 9090
spring:
  datasource:
    username: root
    password: 你的密码
    url: jdbc:mysql://localhost:3306/smarket?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC
files:
  upload:
    path: D:\pic\   # Windows 本地上传目录，请按需修改
```

```bash
cd server
mvn spring-boot:run
```

入口类：`org.grow.mall.MarketbApplication`  
Swagger（若可访问）：`http://localhost:9090/swagger-ui/` 或 `/swagger-ui/index.html`

### 3. 管理端 `admin/`

API 默认指向 `http://localhost:9090`（`admin/src/utils/request.js`）。

```bash
cd admin
npm install
npm run serve
```

浏览器访问：`http://localhost:8080`（以终端打印为准）。

生产构建：

```bash
cd admin
npm run build
# 产物在 admin/dist/，可部署到 Nginx / 静态服务器
```

### 4. 小程序 `miniprogram/`

```bash
cd miniprogram
npm install
# 微信开发者工具导入本目录 → 工具 → 构建 npm
```

---

## 主要接口一览（后端）

| 前缀 | 说明 |
|------|------|
| `/user` | 管理员用户：登录 `/user/login`、注册、分页等 |
| `/users` | 顾客用户：登录 `/users/users-login` 等 |
| `/product` | 商品 CRUD / 分页 |
| `/type` | 分类 |
| `/order` | 订单 |
| `/file` | 上传 `/file/upload`、下载 `/{fileUUID}` |
| `/echarts` | 管理端统计图表数据 |
| `/wx/*` | 小程序端：登录、商品、订单等 |

管理端与小程序上传地址中硬编码了 `http://localhost:9090/file/upload`，换环境时请同步修改相关页面 / 配置。

---

## 部署说明

### 开发环境（本机）

按上文「一键启动」或「手动启动」即可。管理端与小程序均默认请求本机 `9090`。

### 生产 / 演示环境建议架构

```
浏览器 / 微信客户端
        │
        ├─► Nginx（443/80）
        │      ├─ /          → admin/dist 静态资源
        │      └─ /api/ 或 /  → 反向代理到 Spring Boot :9090
        │
        └─► 小程序 → 正式 HTTPS 域名（须配置合法 request / upload 域名）
```

### 后端部署

```bash
cd server
mvn -DskipTests package
# 产物：server/target/marketb-0.0.1-SNAPSHOT.jar（以实际 jar 名为准）

java -jar target/marketb-0.0.1-SNAPSHOT.jar
```

建议：

1. 将 `application.yml` 中的数据库密码、上传路径改为服务器路径（如 `/data/market/upload/`）。
2. 生产库单独建库、导入结构；测试数据可按需裁剪。
3. 用 systemd / Windows 服务 / 面板进程守护保持进程常驻。
4. 前面加 Nginx，配置 HTTPS；上传目录做好磁盘空间与备份。

示例 Nginx（仅供参考）：

```nginx
server {
    listen 80;
    server_name your.domain.com;

    location / {
        root /var/www/market-admin;
        try_files $uri $uri/ /index.html;
    }

    location /api/ {
        proxy_pass http://127.0.0.1:9090/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

若走 `/api` 前缀，需同时改管理端 `baseURL` 与小程序 `baseUrl`，或在 Nginx 中按现有无前缀路径转发。

### 管理端部署

```bash
cd admin
# 将 src/utils/request.js 与各页 el-upload 的 action 改为线上 API
npm run build
# 将 dist/ 上传到 Nginx root 或对象存储 + CDN
```

### 小程序发布

1. 将 `miniprogram/utils/request.js` 的 `baseUrl` 改为 **HTTPS** 正式域名。
2. 微信公众平台 → 开发管理 → 开发设置：配置 request / uploadFile 合法域名。
3. 开发者工具上传代码 → 提交审核 → 发布。

### 环境切换 checklist

- [ ] MySQL 地址 / 账号 / 库名
- [ ] `files.upload.path` 可写且持久化
- [ ] `admin`：`utils/request.js` + 上传 `action` URL
- [ ] `miniprogram`：`utils/request.js` 的 `baseUrl`
- [ ] 小程序合法域名与 AppID
- [ ] 防火墙放行 9090（或仅内网 + Nginx 反代）

---

## 常见问题

| 问题 | 处理 |
|------|------|
| 后端启动报数据库连接失败 | 确认 MySQL 已启动，已执行 `init.sql`，`application.yml` 密码正确 |
| 上传失败 / 找不到文件 | 确认 `D:\pic\`（或你配置的路径）存在且进程有写权限 |
| 管理端空白或接口跨域 | 确认后端已启动；开发环境一般直连 `9090`；检查浏览器控制台 |
| 小程序 request:fail | 开发阶段勾选「不校验合法域名」；真机用局域网 IP 而非 `localhost` |
| Vant 组件找不到 | 在小程序目录执行 `npm install` 后，在开发者工具中 **构建 npm** |
| `mvn` / `npm` 不是内部命令 | 安装 JDK/Maven/Node 并配置 PATH 后重开终端 |
| 端口被占用 | 运行 `停止.bat`，或修改 `application.yml` / Vue 端口 |

---

## 旧仓库说明

合并后请把下列仓库标为 **Archived**，README 指向本仓库，避免继续往旧仓提交：

- `market-back-master`
- `market-manage-main-PC`

（历史代码仍保留在原仓库，便于对照。）

---

## 许可证

以各子项目原仓库约定为准；本 monorepo 用于校内实训演示。