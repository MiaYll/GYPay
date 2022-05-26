<template>
  <v-navigation-drawer v-model="navdrawer" app>
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="text-h6">
            支付系统数据面板
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list
        dense
        nav
      >
        <v-list-item
          v-for="route in routes"
          :key="route.meta.title"
          @click="setPage(route)"
          link
        >
          <v-list-item-icon>
            <v-icon>{{ route.meta.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ route.meta.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
  </v-navigation-drawer>
</template>

<script>

export default {
    data(){
        return {
            routes: []
        }
    },
    mounted(){
      this.updateRoute();
    },
    watch: {
      isAdmin: {
        handler() {
          this.updateRoute();
        },
        deep: true,
      },
    },
    computed: {
        navdrawer: {
          get() {
            return this.$store.state.navdrawer
          },
          set(val){
            this.$store.state.navdrawer = val
          }
        },
        isAdmin() {
          return this.$store.state.account.isAdmin;
        }
    },
    methods: {
      setPage(route){
        this.$router.push(route.path)
      },
      updateRoute(){
        this.routes = this.$router.options.routes.filter((route) => {
              if(route.meta.show == false){
                return false
              }
              if(route.meta.needAdmin){
                if(this.$store.state.account.isAdmin){
                  return true
                }else{
                  return false
                }
                
              }
              return true
            })
      }
    }
}
</script>

<style>

</style>