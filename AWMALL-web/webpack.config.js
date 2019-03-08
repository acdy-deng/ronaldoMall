/*
 * @Author: CRONALDO
 * @Date:   2019-03-07 08:45:49
 */
const path = require('path');
const webpack = require('webpack');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');

// 环境变量配置 dev / online
const WEBPACK_ENV = process.env.WEBPACK_ENV || 'dev'; // WEBPACK_ENV=dev webpack-dev-server --line --port 6666

// 集中处理html 传入pageName名称
const getHtmlConfig = function (pageName) {
    return {
        template: './src/view/' + pageName + '.html',
        filename: 'view/' + pageName + '.html',
        inject: true,
        hash: true,
        chunks: ['common', pageName]
    }
};

// webpack core config
const config = {
    // 主入口js文件
    entry: {
        'common': ['./src/page/common/script/index.js'],
        'index': ['./src/page/index/index.js'],
        'login': ['./src/page/login/index.js']
    },
    // 编译之后的文件地址及名称
    output: {
        path: path.resolve(__dirname, 'dist'),
        publicPath: '/dist',
        filename: 'js/[name].js'
    },
    // 加载模块
    externals: {
        'jquery': 'window.jQuery'
    },
    module: {
        loaders: [
            {test: /\.css$/, loader: ExtractTextPlugin.extract("style-loader", "css-loader")},
            {test: /\.(gif|png|jpg|woff|svg|eot|ttf)\??.*$/, loader: 'url-loader?limit=100&name=resource/[name].[ext]'}
        ]
    },
    // 插件
    plugins: [
        // 配置通用模块
        new webpack.optimize.CommonsChunkPlugin({
            // 对应entry的common
            name: 'common',
            filename: 'base.js'
        }),
        // css单独打包
        new ExtractTextPlugin("css/[name].css"),
        // html模板处理
        new HtmlWebpackPlugin(getHtmlConfig('index'))
    ]
};

//  WEBPACK_ENV=dev webpack-dev-server --line --port 6666
if ('dev' === WEBPACK_ENV) {
    config.entry.common.push('webpack-dev-server/client?http://127.0.0.1:6666');
}

module.exports = config;
