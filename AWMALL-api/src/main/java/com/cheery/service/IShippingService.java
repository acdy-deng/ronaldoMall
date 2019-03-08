package com.cheery.service;

import com.cheery.common.ServerResponse;
import com.cheery.pojo.Shipping;
import com.cheery.pojo.User;

/**
 * @desc: 收货地址业务逻辑层接口
 * @className: IShippingService
 * @author: RONALDO
 * @date: 2019-03-06 10:55
 */
public interface IShippingService {

    /**
     * desc: 增加收货地址
     *
     * @param shipping 收货地址实体
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-06 10:57
     */
    ServerResponse<?> addAddress(Shipping shipping);

    /**
     * desc: 修改收货地址
     *
     * @param shipping 收货地址实体
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-06 11:00
     */
    ServerResponse<?> updateAddress(Shipping shipping);

    /**
     * desc: 删除收货地址
     *
     * @param id 地址id
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-06 11:01
     */
    ServerResponse<?> deleteAddress(Long id);

    /**
     * desc: 根据用户id查询收货地址
     *
     * @param page   页码
     * @param size   页面大小
     * @param userId 用户id
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-06 11:03
     */
    ServerResponse<?> findAllAddress(Integer page, Integer size, Long userId);

}
