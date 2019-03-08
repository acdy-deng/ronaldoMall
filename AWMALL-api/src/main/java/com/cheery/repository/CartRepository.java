package com.cheery.repository;

import com.cheery.pojo.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @desc: 购物车数据访问层接口
 * @className: CartRepository
 * @author: RONALDO
 * @date: 2019-03-04 14:22
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * desc: 查询指定用户的某个购物车
     *
     * @param userId    用户id
     * @param productId 产品id
     * @return Cart
     * @auther RONALDO
     * @date: 2019-03-04 14:30
     */
    Cart findAllByUserIdAndProductId(Long userId, Long productId);

    /**
     * desc: 查询用户的购物车
     *
     * @param userId 用户id
     * @return List<Cart>
     * @auther RONALDO
     * @date: 2019-03-04 15:04
     */
    List<Cart> findAllByUserId(Long userId);

    /**
     * desc: 查询购物车产品的选择状态
     *
     * @param userId 用户id
     * @return int
     * @auther RONALDO
     * @date: 2019-03-04 15:45
     */
    @Query(value = "SELECT COUNT(1) FROM cart WHERE checked = 0 AND user_id = ?1", nativeQuery = true)
    int findCartProductCheckStatusByUserId(Long userId);

    /**
     * desc: 从购物车中删除
     *
     * @param userId    用户id
     * @param productId 产品id
     * @return 受影响行数
     * @auther RONALDO
     * @date: 2019-03-04 18:50
     */
    int deleteByUserIdAndProductId(Long userId, Long productId);

    /**
     * desc: 全选 / 反选
     *
     * @param checked 选择状态
     * @param userId  用户id
     * @return
     * @auther RONALDO
     * @date: 2019-03-04 20:08
     */
    @Modifying
    @Query(value = "UPDATE cart SET checked = ?,update_time = NOW() WHERE user_id = ?", nativeQuery = true)
    void selectOrUnselectAll(Integer checked, Long userId);

    /**
     * desc: 单选 / 单反选
     *
     * @param checked   选择状态
     * @param userId    用户id
     * @param productId chid
     * @return
     * @auther RONALDO
     * @date: 2019-03-04 20:24
     */
    @Modifying
    @Query(value = "UPDATE cart SET checked = ?,update_time = NOW() WHERE user_id = ? and product_id = ?", nativeQuery = true)
    void selectOrUnselectOne(Integer checked, Long userId, Integer productId);

    /**
     * desc: 查询购物车商品总数
     *
     * @param userId 用户id
     * @return int
     * @auther RONALDO
     * @date: 2019-03-04 21:21
     */
    @Query("select NULLIF(sum(quantity),0) from Cart where userId = ?1")
    int countProduct(Long userId);
}
