<template>
<header-component></header-component>
<RouterView></RouterView>
</template>

<script>
import HeaderComponent from "./components/HeaderComponent.vue";

export default {
  name: 'App',
  components: {
    'header-component':HeaderComponent
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>./components/HeaderComponent.vue
