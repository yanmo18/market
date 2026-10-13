<template>
  <div class="wrapper">
    <div class="title"><b>随心超市管理系统</b></div>

    <div class="header">
      <div class="login"><b>后台管理系统</b></div>

      <el-form :model="user" :rules="rules" ref="userForm">
        <!-- v-model="user.username"验证前端账号密码是否合法 -->
        <el-form-item prop="username">
          <el-input placeholder="请输入管理员账号" size="medium" prefix-icon="el-icon-user" v-model="user.username">
          </el-input>

        </el-form-item>

        <el-form-item prop="password">

          <el-input placeholder="请输入密码" size="medium" prefix-icon="el-icon-lock" show-password v-model="user.password">
          </el-input>

        </el-form-item>

        <el-form-item class="btn">

          <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">注册</el-button>

          <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>

export default {
  name: "Login",
  data() {
    return {
      user: {},
      rules: {
        username: [//与el-form-item prop="username"相对应
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 10, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 1,
            max: 20,
            message: "长度在 1 到 20 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    login() {
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          // 表单校验合法
          this.request.post("/user/login", this.user).then((res) => {
            if (res.code === "200") {
              localStorage.setItem("user", JSON.stringify(res.data)); //存储用户信息到浏览器
              this.$router.push("/");
              this.$message.success("登录成功");
            } else {
              this.$message.error(res.msg);
            }
          });
        }
      });
    },
  },
};
</script>

<style scoped>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #3f5efb, #eeeeee);
  overflow: hidden;
}

.title {
  height: 60px;
  line-height: 60px;
  padding-left: 50px;
  font-size: 20px;
  color: white;
  background-color: rgba(0, 0, 0, 0.2);
}

.header {
  margin: 150px auto;
  background-color: #fff;
  width: 350px;
  height: 270px;
  padding: 20px;
  border-radius: 10px;
}

.login {
  margin: 20px 0;
  text-align: center;
  font-size: 24px;
}

.btn {
  margin: 10px 0;
  text-align: right;
}
</style>
