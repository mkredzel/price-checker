<template>
  <div id="app">
    <Navigation />
    <Particles />
    <Landing />
    <TV />
    <Footer />
    <Preloader />
  </div>
</template>

<script>
import Navigation from './components/Navigation'
import Landing from './components/Landing'
import Particles from './components/Particles'
import Preloader from './components/Preloader'
import TV from './components/TV'
import Footer from './components/Footer'

export default {
  components: {
    Navigation,
    Particles,
    Landing,
    TV,
    Footer,
    Preloader
  },
  watch: {
    $route: {
      immediate: true,
      handler(to) {
        document.title = to.meta.title || 'TV Price Checker';
      }
    },
  },
  data() {
    return {
    };
  }
}
</script>