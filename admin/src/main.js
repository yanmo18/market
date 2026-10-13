import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
//引入全局样式
import './assets/gloable.css'
import request from '@/utils/request';

import store from './store'

import * as moment from 'moment'

Vue.prototype.$moment = moment




Vue.config.productionTip = false

// size:"mini"小型的控件
Vue.use(ElementUI, {size:"mini"});

Vue.prototype.request=request;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
