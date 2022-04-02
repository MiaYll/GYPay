import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex)

const store = new Vuex.Store({
    state () {
        return {
          account: {
              username: null,
              password: null
          },
          navdrawer: true
        }
    },
    mutations: {
        bindAccount(state,account) {
            state.account = account
        },
        toogle(state){
            state.navdrawer = !state.navdrawer
        }
    },
    plugins: [createPersistedState()]
})


export default store;