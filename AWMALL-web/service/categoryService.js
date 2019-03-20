/**
 * @desc: 分类模块API请求
 * @author: RONALDO
 * @date: 2019-03-18 : 14:47
 */
const product = {
    // 查询产品分类名称
    getCategory: function (data, resolve, reject) {
        common.request({
            url: common.url() + '/category/list',
            data: data,
            method: 'GET',
            success: resolve,
            error: reject
        })
    }
};