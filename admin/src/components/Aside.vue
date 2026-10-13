<template>
  <el-menu style="height: 100%" overflow-x:hidden background-color="rgb(48, 65, 86)" text-color="#fff"
    active-text-color="#ffd04b" :collapse-transition="false" :collapse="isCollapse" router>
    <!-- rgb(48, 65, 86) -->
    <!-- @select="handleSelect" -->
    <!-- logo图标 -->
    <div style="height: 60px; line-height: 60px; text-align: center">
      <!-- <img
            src="../assets/logo.png"
            style="width: 20px; position: relative; top: 4px; margin-right: 5px"
          /> -->
      <b style="color: white" v-show="logoTextShow">随心超市管理系统</b>
    </div>
    <!-- logo图标 -->

    <!-- 导航一 -->
    <el-menu-item index="/home">
      <i class="el-icon-s-home"></i>
      <span slot="title">主页</span>
    </el-menu-item>
    <!-- 导航一 -->

    <!-- 导航二 -->
    <!-- 用户管理 -->
    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-user-solid"></i>
        <span slot="title">用户</span>
      </template>

      <el-menu-item index="/user">
        <i class="el-icon-user"></i>
        <span slot="title">管理员</span>
      </el-menu-item>
      <el-menu-item index="/users">
        <i class="el-icon-s-custom"></i>
        <span slot="title">微信用户</span>
      </el-menu-item>
    </el-submenu>

    <!-- 商品管理 -->
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-shopping-bag-1"></i>
        <span slot="title">商品</span>
      </template>

      <el-menu-item index="/product">
        <i class="el-icon-sell"></i>
        <span slot="title">商品信息</span>
      </el-menu-item>
    </el-submenu>

    <!-- 订单管理 -->
    <el-submenu index="3">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">订单</span>
      </template>

      <el-menu-item index="/order">
        <i class="el-icon-s-order"></i>
        <span slot="title">订单信息</span>
      </el-menu-item>
    </el-submenu>

    <!-- 类型管理 -->
    <el-submenu index="4">
      <template slot="title">
        <i class="el-icon-price-tag"></i>
        <span slot="title">类型</span>
      </template>

      <el-menu-item index="/type">
        <i class="el-icon-collection"></i>
        <span slot="title">类型管理</span>
      </el-menu-item>
    </el-submenu>

  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean,
  },
  methods: {
    // handleSelect(){
    //   this.$route.fullPath.split("/")
    // }
  },
};
</script>

<style></style>
