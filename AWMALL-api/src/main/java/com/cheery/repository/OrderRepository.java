package com.cheery.repository;

import com.cheery.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @desc: 订单数据访问层接口
 * @className: OrderRepository
 * @author: RONALDO
 * @date: 2019-03-11 19:57
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * desc: 根据用户id和订单id查询订单
     *
     * @param userId  用户id
     * @param orderNo 订单id
     * @return List<Order>
     * @auther RONALDO
     * @date: 2019-03-11 20:07
     */
    @Query(value = "SELECT * FROM `order` WHERE user_id = ? AND order_no = ?", nativeQuery = true)
    Order findByUserIdAndOrderNo(Long userId, long orderNo);

}
