const path = require('path')
const webpack = require('webpack')
const htmlPlugin = require('html-webpack-plugin')
const extractTextPlugin = require('extract-text-webpack-plugin')

module.exports = config = {
    entry: {
        index: [
            'babel-polyfill',
            './src/index.js',
        ],
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: extractTextPlugin.extract({
                    fallback: "style-loader",
                    use: [{
                        loader: "css-loader",
                        options: {
                            minimize: true
                        }
                    }]
                }),
            }, {
                test: /\.(png|jpg|gif)$/,
                use: [{
                    loader: "url-loader",
                    options: {
                        limit: 5000,
                        name: '[name].[hash:8].[ext]',
                        outputPath: 'images/'
                    }
                }],
            }, {
                test: /\.html$/i,
                use: [{
                    loader: "html-withimg-loader"
                }],
            }, {
                test: /\.less$/,
                use: extractTextPlugin.extract({
                    fallback: "style-loader",
                    use: [{
                        loader: "css-loader"
                    }, {
                        loader: "less-loader"
                    }]
                }),
            }, {
                test: /\.scss$/,
                use: extractTextPlugin.extract({
                    fallback: "style-loader",
                    use: [{
                        loader: "css-loader"
                    }, {
                        loader: "sass-loader"
                    }]
                }),
            }, {
                test: /\.(jsx|js)$/,
                use: [{
                    loader: "babel-loader",
                }],
                exclude: /node_modules/,
            },
        ]
    },
    resolve: {
        extensions: ['.js', '.jsx', '.json'],
        alias: {
            actions: path.resolve(__dirname, '../src/redux/actions'),
            actionTypes: path.resolve(__dirname, '../src/redux/actionTypes'),
            sagas: path.resolve(__dirname, '../src/saga'),
            components: path.resolve(__dirname, '../src/components'),
            commonjs: path.resolve(__dirname, '../src/commonjs'),
            config: path.resolve(__dirname, '../src/config'),
            containers: path.resolve(__dirname, '../src/containers'),
            images: path.resolve(__dirname, '../src/images'),
        },
    },
    plugins: [
        new htmlPlugin({
            minify: {
                removeAttributeQuotes: true,
            },
            hash: true,
            template: './src/index.html',
        }),
        new extractTextPlugin('css/all.[chunkhash:8].css'),
        new webpack.optimize.CommonsChunkPlugin({
            name: ['common'],
            filename: 'js/commonjs/[name].[chunkhash:8].js',
            miniChunks: 2,
        }),
    ],
    watchOptions: {
        poll: 1000,
        aggregateTimeout: 500,
        ignored: /node_modules/,
    }
}