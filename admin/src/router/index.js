import store from "@/store";
import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);
//首页
import Home from "../views/Home/Home.vue";
//登录注册
import Login from "../views/LoginRegister/Login.vue";
import Register from "../views/LoginRegister/Register.vue";

//用户管理
import User from "../views/userManager/User.vue";
import Users from "../views/userManager/Users.vue";
//前台管理
import Order from "../views/shopManager/Order.vue";
import Type from "../views/shopManager/Type.vue";
import Product from "../views/shopManager/Product.vue";

//管理员个人信息
import Person from "../views/Person.vue";

const routes = [
  {
    path: "/",
    component: () => import("../views/Manage.vue"),
    redirect: "/home",
    children: [
      //首页
      { path: "home", name: "首页", component: Home },
      //用户管理
      { path: "user", name: "管理员", component: User },
      { path: "users", name: "用户信息", component: Users },
      //前台管理
      { path: "order", name: "订单信息", component: Order },
      { path: "product", name: "商品信息", component: Product },
      { path: "type", name: "类型管理", component: Type },
      //个人信息
      { path: "person", name: "个人信息", component: Person },
    ],
  },
  //登录注册
  { path: "/login", name: "login", component: Login },
  { path: "/register", name: "Register", component: Register },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  //设置当前路由名称,为了在Header组件中去使用
  localStorage.setItem("currentPathName", to.name);
  store.commit("setPath"); //触发store的数据更新
  next();
});

export default router;
