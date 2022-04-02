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
  <template v-slot:item.is_Ship="{ item }">
      <v-chip label :color="getColor(item.is_Ship)" text-color="white">{{ item.is_Ship ? "已发货" : "未发货" }}</v-chip>
  </template>
  </v-data-table>
</template>

<script>

import request from "@/utils/request.js"

export default {
    data(){
        return{
            headers: [
                {text: "订单号", value: "order_id"},
                {text: "商品名", value: "item_name"},
                {text: "支付时间", value: "time"},
                {text: "金额", value: "price"},
                {text: "用户名", value: "username"},
                {text: "支付方式", value: "type"},
                {text: "已发货", value: "is_Ship"}
            ],
            orderData: [],
            total: 0,
            options: {
              itemsPerPage: 10
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
        this.loading = false
      },
      getColor(type){
        return type == "WECHAT" || type === true ? "green" : "blue"
      }
    }


}
</script>

<style>

</style>