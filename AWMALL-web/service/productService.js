/**
 * @desc: 产品模块API请求
 * @author: RONALDO
 * @date: 2019-03-18 : 14:42
 */
const product = {
    // 多条件查询商品
    multiQuery: function (data, resolve, reject) {
        common.request({
            url: common.url() + '/product/list',
            data: data,
            method: 'GET',
            success: resolve,
            error: reject
        })
    },
    // 查询商品详情
    productDetails: function (data, resolve, reject) {
        common.request({
            url: common.url() + '/product/details',
            data: data,
            method: 'GET',
            success: resolve,
            error: reject
        })
    },
    // 查询产品评论
    getComment: function (data, resolve, reject) {
        common.request({
            url: common.url() + '/p/comment/list',
            data: data,
            method: 'GET',
            success: resolve,
            error: reject
        })
    }
};