module.exports = {
  publicPath: '/',
  outputDir: 'dist',
  devServer: {
    host: '0.0.0.0',
    port: 8088,
    https: false,
    hotOnly: false,
    proxy: null,
    disableHostCheck: true
  },
  chainWebpack: config => {
    if (process.env.use_analyzer) {
      config
      .plugin('webpack-bundle-analyzer')
      .use(require('webpack-bundle-analyzer').BundleAnalyzerPlugin)
    }
  },
  lintOnSave: true
}
