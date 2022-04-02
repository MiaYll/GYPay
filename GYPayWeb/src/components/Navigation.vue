<template>
  <v-navigation-drawer v-model="navdrawer" app>
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="text-h6">
            数据在线查看
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
            routes: this.$router.options.routes.filter((route) => {return route.meta.show != false || route.meta.show == undefined})
        }
    },
    computed: {
        navdrawer: {
          get() {
            return this.$store.state.navdrawer
          },
          set(val){
            this.$store.state.navdrawer = val
          }
        }
    },
    methods: {
      setPage(route){
        this.$router.push(route.path)
      }
    }
}
</script>

<style>

</style>