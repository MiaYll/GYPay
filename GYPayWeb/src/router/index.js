import Vue from "vue"
import VueRouter from "vue-router"
import Login from "@/pages/login/Login.vue"
import DataChart from "@/pages/datachart/DataChart.vue"
import OrderList from "@/pages/orderlist/OrderList.vue"


Vue.use(VueRouter);

const routes = [
    {
      path: '/login',
      component: Login,
      meta: {
        icon: 'mdi-account-outline',
        title: '绑定密钥'
      }
    },
    {
      path: '/orderlist',
      component: OrderList,
      meta: {
        icon: 'mdi-format-list-bulleted',
        title: '订单列表'
      }
    },
    {
      path: '/datachart',
      component: DataChart,
      meta: {
        icon: 'mdi-chart-line',
        title: '数据图表'
      }
    },
    {
      path: '/',
      redirect: '/login',
      meta: {
        show: false
      }
    }
]

const router = new VueRouter({
  mode: "history",
  routes // (缩写) 相当于 routes: routes
})
  
export default router;