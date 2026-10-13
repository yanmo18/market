<template>
  <div style="line-height: 60px;display: flex;">
    <!-- 收缩图标· -->
    <div style="flex: 1;">
      <span :class="collapseBtnClass" style="cursor: pointer;font-size: 19px;" @click="collapse">
      </span>

      <el-breadcrumb separator="/" style="display:inline-block;margin-left:10px">
        <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>

    </div>
    <!-- 收缩图标· -->

    <!-- 展示登录用户 -->
    <el-dropdown style="width: 150px; cursor: pointer;text-align:right">

      <div style="display:inline-block">
        <img :src="user.avatarUrl" style="width:30px;
                        border-radius:50%;
                        position:relative;
                        top:10px;
                        right:5px
                              ">
        <span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
        <!-- <i class="el-icon-setting" style="margin-right: 15px"></i> -->
      </div>

      <el-dropdown-menu slot="dropdown" style="width:100px;text-align:center">

        <el-dropdown-item>
          <router-link to="/person">个人信息</router-link>
        </el-dropdown-item>

        <el-dropdown-item>
          <span style="text-decorration:none" @click="logout">
            退出
          </span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <!-- 展示登录用户 -->
  </div>
</template>

<script>
export default {
  props: {
    collapseBtnClass: String,
    collapse: '',
    user: Object

  },
  computed: {
    currentPathName() {
      //需要监听的数据
      return this.$store.state.currentPathName;
    }
  },
  data() {
    return {
      // user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
      //从登录保存的localStorage.setItem("user", JSON.stringify(res.data));取出数据
    }
  },
  watch: {
    currentPathName(newVal, oldVal) {
      console.log(newVal)
    }
    // $route(){
    //   //去除route里面设置的当前路由
    //   // this.currentPathName=localStorage.getItem("currentPathName")
    // }
  },
  methods: {
    logout() {
      this.$router.push('/login')
      localStorage.removeItem('user')
      this.$message.success('退出成功')
    },
    // collapse(){
    //   // this.$emit("asideCollapse")
    // }
  }
}
</script>

<style></style>