<template>
  <form @submit.prevent="submit">
    <v-text-field label="账户" v-model="username"> </v-text-field>
    <v-text-field label="密钥" v-model="password" type="password"> </v-text-field>
    <v-btn type="submit" block color="blue">
    绑定
  </v-btn>
  <Message :text="text" timeout="2000" v-model="show"/>
  </form>
</template>

<script>

import { api } from "@/utils/api.js"
import Message from '@/components/Message.vue'

export default {
  data() {
    return {
      username: '',
      password: '',
      text: '',
      show: false
    };
  },
  components: {
     Message
  },
  methods: {
      async submit(){
        const res = await api.post('account/verify',{
            username: this.username,
            password: this.password
        });
        if(res.data.code == 500){
            api.defaults.headers.common['name'] = this.username;
            api.defaults.headers.common['password'] = this.password;
            this.show("绑定成功")
        }else{
            this.show("校验失败")
        }
        
      },
      methods: {
          show(text) {
            this.text = text
            this.show = true;
          }
      }
  }
};
</script>
    
<style>
</style>