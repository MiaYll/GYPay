import Vue from "vue"
import VueRouter from "vue-router"
import Login from "@/pages/login/Login.vue"
// import DataChart from "@/pages/datachart/DataChart.vue"
import OrderList from "@/pages/orderlist/OrderList.vue"
import Statistics from "@/pages/statistics/Statistics.vue"
import AccountList from "@/pages/accountlist/AccountList.vue"
import Settings from "@/pages/settings/Settings.vue"
import store from "@/store"


Vue.use(VueRouter);

const routes = [
    {
      path: '/login',
      component: Login,
      name: 'Login',
      meta: {
        icon: 'mdi-account-key-outline',
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
      path: '/statistics',
      component: Statistics,
      meta: {
        icon: 'mdi-chart-line',
        title: '数据统计'
      }
    },
    {
      path: '/accountlist',
      component: AccountList,
      meta: {
        icon: 'mdi-account-supervisor',
        title: '用户管理',
        needAdmin: true
      }
    },
    {
      path: '/settings',
      component: Settings,
      meta: {
        icon: 'mdi-cog',
        title: '修改设置',
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