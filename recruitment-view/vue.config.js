module.exports = {
  publicPath: '/',
  outputDir: 'dist',
  devServer: {
    host: '0.0.0.0',
    port: 8089,
    https: false,
    hotOnly: false,
    proxy: {
      '/apis/oss': {
        target: 'https://www.baidu.com',
        changeOrigin: true,
        pathRewrite: {'^/apis/oss': ''},
      }
    },
    disableHostCheck: true
  },
  chainWebpack: config => {
    if (process.env.use_analyzer) {
      config
      .plugin('webpack-bundle-analyzer')
      .use(require('webpack-bundle-analyzer').BundleAnalyzerPlugin)
    }
  }
}
