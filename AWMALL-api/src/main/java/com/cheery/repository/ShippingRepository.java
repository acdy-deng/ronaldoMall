package com.cheery.repository;

import com.cheery.pojo.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @desc: 收货地址数据访问层接口
 * @className: ShippingRepository
 * @author: RONALDO
 * @date: 2019-03-06 10:54
 */
@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long>, JpaSpecificationExecutor<Shipping> {

    /**
     * desc: 根据用户id和收货地址id查询
     *
     * @param userId 用户id
     * @param id     收货地址id
     * @return Shipping 收货地址实体
     * @auther RONALDO
     * @date: 2019-03-06 14:02
     */
    Shipping findByUserIdAndId(Long userId, Long id);

}
