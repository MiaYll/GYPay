<template>
  <v-data-table
    :headers="headers"
  >

  </v-data-table>
</template>

<script>



export default {
    data(){
        return{
            headers: [
                {text: "订单号", value: "orderId"},
                {text: "商品名", value: "itemName"},
                {text: "支付时间", value: "shipTime"},
                {text: "金额", value: "price"},
                {text: "用户名", value: "username"},
                {text: "支付方式", value: "payType"}
            ]

        }
    }
}
</script>

<style>

</style>