package com.cheery.repository;

import com.cheery.pojo.Order;
import com.cheery.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    @Query(value = "SELECT * FROM `orders` WHERE user_id = ? AND order_no = ?", nativeQuery = true)
    Order findByUserIdAndOrderNo(Long userId, long orderNo);

    /**
     * desc: 根据用户id查询全部订单
     *
     * @param
     * @return
     * @auther RONALDO
     * @date: 2019-03-13 20:32
     */
    List<Order> findAllByUserId(Long userId);

}
