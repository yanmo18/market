<template>
  <!-- 侧边栏 -->
  <el-container style="min-height: 100vh">
    <!-- 侧边导航栏 -->
    <el-aside :width="sideWidth + 'px'">
      <!-- 导航栏菜单 -->
      <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" />
      <!--  -->
      <!-- 导航栏菜单 -->
    </el-aside>
    <!-- 侧边导航栏 -->

    <!-- 顶部导航栏 -->
    <el-container>
      <el-header style="border-bottom: 1px solid #ccc;">
        <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :user="user" />
      </el-header>
      <!-- 顶部导航栏 -->

      <!-- 主体：存放数据的地方 -->
      <el-main>
        <!-- 当前页面的子路由会在 <router-view />展示 -->
        <router-view @refreshUser="getUser" />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
// @ is an alias to /src
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";

export default {
  name: "HomeView",
  data() {
    return {
      collapseBtnClass: "el-icon-s-fold",
      isCollapse: false,
      sideWidth: 250,
      logoTextShow: true,
      user: {}
      // localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    };
  },

  components: {
    Aside,
    Header
  },
  created() {
    //从后台获取最新的数据
    this.getUser();
  },
  methods: {
    collapse() {
      //点击收缩按钮触发
      this.isCollapse = !this.isCollapse;
      if (this.isCollapse) {
        //收缩
        this.sideWidth = 65;
        this.collapseBtnClass = "el-icon-s-unfold";

        this.logoTextShow = false;
      } else {
        //展开
        this.sideWidth = 200;
        this.collapseBtnClass = "el-icon-s-fold";
        this.logoTextShow = true;
      }
    },
    getUser() {
      let username = localStorage.getItem("user")
        ? JSON.parse(localStorage.getItem("user")).username
        : "";
      //从后台获取User数据
      this.request.get("/user/username/" + username).then(res => {
        //重新赋值后台最新的user数据
        this.user = res.data;
      });
    }
  }
};
</script>

<style>
</style>
