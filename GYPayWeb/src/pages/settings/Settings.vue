<template>
  <v-container>
    <form @submit.prevent="submit">
      <v-row>
        <v-col cols="6">
          <v-text-field label="用户名" v-model="this.$store.state.account.username" outlined disabled></v-text-field>
        </v-col>
        <v-col cols="6">
          <v-text-field label="手续费" outlined v-model="rate" disabled></v-text-field>
        </v-col>
        <v-col cols="12">
          <v-text-field label="修改密码*" outlined type="password" v-model="password"></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-btn block color="green" type="submit"> 修改 </v-btn>
      </v-row>
    </form>
    <Message :text="text" timeout="2000" v-model="show"/>
  </v-container>
</template>

<script>

import request from '@/utils/request.js'
import Message from '@/components/Message.vue'

export default {
    data() {
        return {
            password: '',
            show: false,
            text: ''
        };
    },
    computed: {
        rate() {
            return this.$store.state.account.rate + '%'
        }
    },
    components: {
        Message
    },
    methods: {
        async submit(){
            const res = await request().post("/account/settings",
                {
                    username: this.username,
                    password: this.password
                }
            )
            this.showMessage(res.data.message)
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