import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex)

const store = new Vuex.Store({
    state () {
        return {
          account: null
        }
    },
    mutations: {
        bindAccount(state,account) {
            state.account = account
        }
    },
    plugins: [createPersistedState()]
})

export default store;