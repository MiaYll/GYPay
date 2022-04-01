<template>
  <v-data-table
    :headers="headers"
    :items="orderData"
    :options.sync="options"
    :server-items-length="total"
    :loading="loading"
  >
  <template v-slot:item.type="{ item }">
      <v-chip label :color="getColor(item.type)" text-color="white">{{ item.type == "WECHAT" ? "微信" : "支付宝" }}</v-chip>
    </template>
  </v-data-table>
</template>

<script>

import request from "@/utils/request.js"

export default {
    data(){
        return{
            headers: [
                {text: "订单号", value: "orderId"},
                {text: "商品名", value: "itemName"},
                {text: "支付时间", value: "createTime"},
                {text: "金额", value: "price"},
                {text: "用户名", value: "username"},
                {text: "支付方式", value: "type"},
                {text: "已发货", value: "isShip"}
            ],
            orderData: [],
            total: 0,
            options: {
              itemsPerPage: 15
            },
            loading: false
        }
    },
    watch: {
      options: {
        handler () {
          this.getOrderList();
        },
        deep: true,
      },
    },
    async mounted(){
      this.getOrderList();
    },
    methods: {
      async getOrderList(){
        this.loading = true
        const { page, itemsPerPage } = this.options
        const res = await request().post("/order/getOrderList",{
          page,
          size: itemsPerPage
        })
        this.orderData = res.data.obj.list
        this.total = res.data.obj.total
        console.log(this.orderData)
        this.loading = false
      },
      getColor(type){
        return type == "WECHAT" || type == 1 ? "green" : "secondary"
      }
    }


}
</script>

<style>

</style>