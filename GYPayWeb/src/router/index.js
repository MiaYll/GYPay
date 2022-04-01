import Vue from "vue"
import VueRouter from "vue-router"
import Login from "@/pages/login/Login.vue"
import DataChart from "@/pages/datachart/DataChart.vue"
import OrderList from "@/pages/orderlist/OrderList.vue"
import BuyerInfo from "@/pages/buyerinfo/BuyerInfo.vue"
import store from "@/store"


Vue.use(VueRouter);

const routes = [
    {
      path: '/login',
      component: Login,
      name: 'Login',
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
      path: '/buyerinfo',
      component: BuyerInfo,
      meta: {
        icon: 'mdi-format-list-bulleted',
        title: '充值信息'
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
  routes
})

router.beforeEach((to,form,next) =>{
  console.log(to)
  if(to.path != '/login' && store.state.account.username == null){
    next({name: "Login"})
  }else{
    next()
  }
})
  
export default router;