package com.cheery.common;

import com.cheery.pojo.Cart;
import com.cheery.pojo.Product;
import com.cheery.repository.CartRepository;
import com.cheery.repository.ProductRepository;
import com.cheery.util.BigDecimalUtil;
import com.cheery.vo.CartProductVo;
import com.cheery.vo.CartVo;
import com.cheery.vo.ProductDetailsVo;
import com.cheery.vo.ProductListVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @desc: 用户pojo对象转vo对象
 * @className: PojoToVo
 * @author: RONALDO
 * @date: 2019-03-03 17:11
 */
@Component
public class PojoToVo {

    @Autowired
    private CartRepository repository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * desc: 用于产品pojo转vo -- 基本信息
     *
     * @param p 产品实体
     * @return ProductListVo
     * @auther RONALDO
     * @date: 2019-03-01 14:16
     */
    public static ProductListVo assembleProductListVo(Product p) {
        ProductListVo vo = new ProductListVo();
        vo.setId(p.getId());
        vo.setSubtitle(p.getSubtitle());
        vo.setBrand(p.getBrand().getName());
        vo.setStyle(p.getStyle().getName());
        vo.setPrice(p.getPrice());
        vo.setDiscountPrice(p.getDiscountPrice());
        vo.setIshot(p.getIshot());
        vo.setIsboutique(p.getIsboutique());
        vo.setIsspike(p.getIsspike());
        vo.setSale(p.getSale());
        return vo;
    }

    /**
     * desc: 用于产品pojo转vo -- 详情信息
     *
     * @param p 产品实体
     * @return ProductListVo
     * @auther RONALDO
     * @date: 2019-03-03 16:57
     */
    public static ProductDetailsVo assembleProductDetailsVo(Product p) {
        ProductDetailsVo vo = new ProductDetailsVo();
        vo.setId(p.getId());
        vo.setSubtitle(p.getSubtitle());
        vo.setBrand(p.getBrand().getName());
        vo.setStyle(p.getStyle().getName());
        vo.setMainImage(p.getMainImage());
        vo.setSubImages(p.getSubImages());
        vo.setDetail(p.getDetail());
        vo.setPrice(p.getPrice());
        vo.setDiscountPrice(p.getDiscountPrice());
        vo.setIshot(p.getIshot());
        vo.setIsboutique(p.getIsboutique());
        vo.setIsspike(p.getIsspike());
        vo.setStock(p.getStock());
        vo.setSale(p.getSale());
        return vo;
    }

    /**
     * desc: 购物车高复用返回数据
     *
     * @param userId 用户id
     * @return CartVo
     * @auther RONALDO
     * @date: 2019-03-04 15:43
     */
    public CartVo getCartVoLimit(Long userId) {
        CartVo vo = new CartVo();
        // 查询当前用户的购物车列表
        List<Cart> cartList = repository.findAllByUserId(userId);
        // 初始化一个CartProductVo
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        // 初始化购物车总价
        BigDecimal cartTotalPrice = new BigDecimal("0");
        if (0 < cartList.size()) {
            for (Cart cartItem : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(userId);
                cartProductVo.setProductId(cartItem.getProductId());
                // 获取产品
                Product product = productRepository.findOne(cartItem.getProductId());
                if (null != product) {
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductPrice(product.getDiscountPrice());
                    cartProductVo.setProductStock(product.getStock());
                    // 判断库存
                    int buyCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()) {
                        // 库存充足的时候
                        buyCount = cartItem.getQuantity();
                        cartProductVo.setLimitQuantity(Constant.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        buyCount = product.getStock();
                        cartProductVo.setLimitQuantity(Constant.Cart.LIMIT_NUM_FAIL);
                        // 更新购物车的有效库存
                        Cart cart = new Cart();
                        cart.setId(cartItem.getId());
                        cart.setQuantity(buyCount);
                        repository.save(cart);
                    }
                    cartProductVo.setQuantity(buyCount);
                    // 计算总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
                    // 设置为选择状态
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }
                if (cartItem.getChecked() == Constant.Cart.CHECKED) {
                    // 如果已经勾选,增加到整个的购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        vo.setCartTotalPrice(cartTotalPrice);
        vo.setCartProductVoList(cartProductVoList);
        vo.setAllChecked(getAllCheckedStatus(userId));
        return vo;
    }

    /**
     * desc:  判断是否为全选
     *
     * @param userId 用户id
     * @return boolean
     * @auther RONALDO
     * @date: 2019-03-04 15:43
     */
    private boolean getAllCheckedStatus(Long userId) {
        if (userId == null) {
            return false;
        }
        return repository.findCartProductCheckStatusByUserId(userId) == 0;
    }

}
