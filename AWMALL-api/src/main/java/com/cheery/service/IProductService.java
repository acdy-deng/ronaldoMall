package com.cheery.service;

import com.cheery.common.ServerResponse;
import com.cheery.pojo.Product;

/**
 * @desc: 产品业务逻辑层接口
 * @className: IProductService
 * @author: RONALDO
 * @date: 2019-02-28 21:45
 */
public interface IProductService {

    /**
     * desc: 根据不同的条件组合查询产品
     *
     * @param page     页码
     * @param size     每页条数
     * @param keyWords 搜索关键字
     * @param brand    产品品牌
     * @param style    产品风格
     * @param product  产品实体( 用于传多条件参数 )
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-02-28 21:59
     */
    ServerResponse<?> findProductByMultipleconditions(Integer page, Integer size, String keyWords, String brand, String style, Product product);

    /**
     * desc: 根据产品id查询产品详情
     *
     * @param id 产品id
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-03 16:42
     */
    ServerResponse<?> findProductDetailsById(Long id);

}
