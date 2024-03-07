const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  devServer:{
    port:3333
  },
  transpileDependencies: true
})
