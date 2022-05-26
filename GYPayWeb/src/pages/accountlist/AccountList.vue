<template>
  <v-container>
    <v-dialog v-model="addAccountDialog" persistent max-width="600px">
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          color="green"
          dark
          class="white--text"
          v-bind="attrs"
          v-on="on"
          block
        >
          添加账户
        </v-btn>
      </template>
      <v-card>
        <form @submit.prevent="submit">
          <v-card-title>
            <span class="text-h5">添加账户</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <v-text-field
                    label="用户名*"
                    v-model="account.username"
                    required
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    label="密码*"
                    type="password"
                    v-model="account.password"
                    required
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="addAccountDialog = false">
              关闭
            </v-btn>
            <v-btn color="blue darken-1" text type="submit"> 保存 </v-btn>
          </v-card-actions>
        </form>
      </v-card>
    </v-dialog>
    <v-data-table :headers="headers" :items="accountData" :loading="loading">
      <template v-slot:item.edit="{ item }">
        <v-container>
          <v-row style="height: 38px; width: 100px" no-gutters>
            <v-col>
              <v-select
                v-model="item.rate"
                :items="rates"
                flat
                dense
                solo
                @input="editRate(item.name, item.rate)"
              ></v-select>
            </v-col>
          </v-row>
        </v-container>
      </template>
      <template v-slot:item.delete="{ item }">
        <v-container>
          <v-row style="height: 38px" no-gutters>
            <v-col cols="5">
              <v-btn
                @click="deleteAccountDialog = { accountName: item.name , toogle: true}"
                fab
                small
                color="red"
                dark
                dense
              >
                <v-icon dark> mdi-delete </v-icon>
              </v-btn>
            </v-col>
          </v-row>
        </v-container>
      </template>
    </v-data-table>

    <v-dialog v-model="deleteAccountDialog.toogle" persistent max-width="290">
      <v-card>
        <v-card-title class="text-h5">
          确认删除?
        </v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="deleteAccountDialog.toogle = false">
            取消
          </v-btn>
          <v-btn color="green darken-1" text @click="deleteAccount()">
            确认删除
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <Message :text="text" timeout="2000" v-model="show" />
  </v-container>
</template>

<script>
import request from "@/utils/request.js";
import Message from "@/components/Message.vue";

export default {
  data() {
    return {
      headers: [
        { text: "用户名", value: "name" },
        { text: "昨日收入", value: "lastTotal" },
        { text: "昨日订单数", value: "lastOrder" },
        { text: "昨日实收", value: "lastMoney" },
        { text: "今日订单数", value: "todayOrder" },
        { text: "今日收入", value: "todayTotal" },
        { text: "修改费率", value: "edit" },
        { text: "删除", value: "delete" },
      ],
      rates: ["1%", "2%", "3%", "4%", "5%", "6%"],
      accountData: [],
      total: 0,
      loading: false,
      addAccountDialog: false,
      deleteAccountDialog: {
        accountName: null,
        toogle: false
      },
      account: {
        username: "",
        password: "",
      },
      show: false,
      text: "",
    };
  },
  components: {
    Message,
  },
  watch: {
    options: {
      handler() {
        this.getOrderList();
      },
      deep: true,
    },
  },
  async mounted() {
    this.getAccountList();
  },
  methods: {
    async getAccountList() {
      this.loading = true;
      const res = await request().post("/admin/accountList");
      this.accountData = [];
      res.data.obj.forEach((v) => {
        this.accountData.push({
          name: v[0].account,
          lastTotal: v[1].total,
          lastOrder: v[1].amount,
          lastMoney: `${(v[1].total * (100 - v[0].rate)) / 100} (-${
            v[0].rate
          }%)`,
          todayTotal: v[0].total,
          todayOrder: v[0].amount,
          rate: v[0].rate + "%",
        });
      });
      this.loading = false;
    },
    async submit() {
      const res = await request().post("/admin/addAccount", this.account);
      this.showMessage(res.data.message);
      if (res.data.code == 200) {
        this.addAccountDialog = false;
        this.getAccountList();
      }
    },
    showMessage(text) {
      this.text = text;
      this.show = true;
    },
    async editRate(name, rate) {
      rate = parseInt(rate.substr(0, 1));
      const res = await request().post("/admin/editRate", {
        name,
        rate,
      });
      this.showMessage(res.data.message);
    },
    async deleteAccount() {
      const res = await request().post("/admin/deleteAccount", {
        name: this.deleteAccountDialog.accountName,
      });
      this.showMessage(res.data.message);
      this.deleteAccountDialog.toogle = false;
      this.deleteAccountDialog.accountName = null;
      if (res.data.code == 200) {
        this.getAccountList();
      }
    },
  },
};
</script>

<style>
</style>