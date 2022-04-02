<template>
  <v-app-bar app>
  <v-app-bar-nav-icon @click.stop="toogle()"></v-app-bar-nav-icon>
  <v-app-bar-title>
      {{ this.$route.meta.title }}
  </v-app-bar-title>

  </v-app-bar>
</template>

<script>
import { mapMutations } from 'vuex'
export default {
  methods: {
    ...mapMutations(['toogle'])
  }

}
</script>

<style>

</style>