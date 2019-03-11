package com.cheery.repository;

import com.cheery.pojo.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @desc:
 * @className: OrderItemRepository
 * @author: RONALDO
 * @date: 2019-03-11 20:38
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /**
     * desc: 根据用户id和订单id查询订单详情
     *
     * @param userId  用户id
     * @param orderNo 订单id
     * @return List<Order>
     * @auther RONALDO
     * @date: 2019-03-11 20:07
     */
    @Query(value = "SELECT * FROM order_item WHERE user_id = ? AND order_no = ?", nativeQuery = true)
    List<OrderItem> findAllByUserIdAndOrderNo(Long userId, BigInteger orderNo);

}
