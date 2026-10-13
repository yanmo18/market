# server — Market 后端

Spring Boot 2.5 + MyBatis-Plus，默认端口 **9090**。

完整说明（环境、一键启动、部署）见仓库根目录 [README.md](../README.md)。

## 快速运行

1. 导入 `sql/init.sql`（库名 `smarket`）
2. 修改 `src/main/resources/application.yml` 数据库与上传路径
3. 执行：

```bash
mvn spring-boot:run
```

或从仓库根目录运行 `scripts\start-server.bat` / `启动.bat`。