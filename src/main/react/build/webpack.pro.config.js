const path = require('path')
const webpack = require('webpack')
const uglifyjsPlugin = require('uglifyjs-webpack-plugin')

module.exports = config = {
    config: {
        output: {
            path: path.resolve(__dirname, '../../resources/static'),
            publicPath: 'http://demopay.codesign.cn/',
            filename: '[name].js',
            chunkFilename: 'js/chunk/[name].[chunkhash:8].chunk.js',
        },
    },
    plugins: [
        new uglifyjsPlugin(),
        new webpack.DefinePlugin({
            "process.env": {
                "NODE_ENV": JSON.stringify("production")
            }
        }),
    ],
}