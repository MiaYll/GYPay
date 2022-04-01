<template>
  <form @submit.prevent="submit">
    <v-text-field label="账户" v-model="account.username"> </v-text-field>
    <v-text-field label="密钥" v-model="account.password" type="password"> </v-text-field>
    <v-btn type="submit" block color="blue">
    绑定
  </v-btn>
  <Message :text="text" timeout="2000" v-model="show"/>
  </form>
</template>

<script>

import request from "@/utils/request.js"
import Message from '@/components/Message.vue'

export default {
  data() {
    return {
      account: this.$store.state.account,
      text: '',
      show: false
    };
  },
  components: {
     Message
  },
  methods: {
      async submit(){
        const res = await request().post('account/verify',this.account)
        if(res.data.obj){
            this.$store.commit('bindAccount',this.account)
            this.$router.push('/datachart');
            this.showMessage("绑定成功")
        }else{
            this.showMessage("校验失败")
        }
      },
      showMessage(text) {
        this.text = text
        this.show = true
      }
  }
};
</script>
    
<style>
</style>