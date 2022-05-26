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
      show: false,
      rate: 5
    };
  },
  components: {
     Message
  },
  methods: {
      async submit(){
        const res = await request().post('account/verify',this.account)
        this.showMessage(res.data.message)
        if(res.data.code == 200){
          this.account.isAdmin = res.data.obj.admin
          this.account.rate = res.data.obj.rate
          this.$store.commit('bindAccount',this.account)
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