<template>
  <v-app>
      <Header />
      <Navigation />
      <Container />
  </v-app>
</template>

<script>
import Container from './components/Container.vue'
import Header from './components/Header.vue'
import Navigation from './components/Navigation.vue'

export default {
  name: 'App',

  components: {
    Container,
    Header,
    Navigation
  },

  data: () => ({
    //
  }),
};
</script>
