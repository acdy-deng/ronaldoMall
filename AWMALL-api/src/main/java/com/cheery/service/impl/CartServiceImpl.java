package com.cheery.service.impl;

import com.cheery.common.Constant;
import com.cheery.common.ServerResponse;
import com.cheery.pojo.Cart;
import com.cheery.pojo.Product;
import com.cheery.repository.CartRepository;
import com.cheery.repository.ProductRepository;
import com.cheery.service.ICartService;
import com.cheery.util.BigDecimalUtil;
import com.cheery.vo.CartProductVo;
import com.cheery.vo.CartVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static com.cheery.util.TipsUtil.parameterError;
import static com.cheery.util.TipsUtil.parameterError_;


/**
 * @desc: 购物车业务逻辑层接口实现
 * @className: CartServiceImpl
 * @author: RONALDO
 * @date: 2019-03-04 14:24
 */
@Service("cartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ServerResponse<?> cartInfo(Long userId) {
        return ServerResponse.createBySuccessMsgAndData("操作成功", getCartVoLimit(userId));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ServerResponse<?> add(Long userId, Long productId, Integer count) {
        parameterError(productId, count);
        Cart cart = repository.findAllByUserIdAndProductId(userId, productId);
        if (null == cart) {
            // 这个产品不在当前购物车,需要新增这个产品
            Cart c = new Cart();
            c.setUserId(userId);
            c.setProductId(productId);
            c.setQuantity(count);
            c.setChecked(Constant.Cart.CHECKED);
            repository.save(c);
        } else {
            // 如果产品已存在 则数量相加
            cart.setQuantity(cart.getQuantity() + count);
            repository.save(cart);
        }
        return this.cartInfo(userId);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ServerResponse<?> update(Long userId, Long productId, Integer count) {
        parameterError(productId, count);
        Cart cart = repository.findAllByUserIdAndProductId(userId, productId);
        if (null != cart) {
            cart.setQuantity(count);
        }
        repository.save(cart);
        return this.cartInfo(userId);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ServerResponse<?> delete(Long userId, Long[] productId) {
        parameterError_(productId);
        for (Long id : productId) {
            repository.deleteByUserIdAndProductId(userId, id);
        }
        return this.cartInfo(userId);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ServerResponse<?> selectOrUnselectAll(Integer checked, Long userId) {
        repository.selectOrUnselectAll(checked, userId);
        return this.cartInfo(userId);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ServerResponse<?> selectOrUnselectOne(Integer checked, Long userId, Integer productId) {
        parameterError(productId);
        repository.selectOrUnselectOne(checked, userId, productId);
        return this.cartInfo(userId);
    }

    @Override
    public ServerResponse<?> countProduct(Long userId) {
        if (null == userId) {
            ServerResponse.createBySuccessData(0);
        }
        return ServerResponse.createBySuccessData(repository.countProduct(userId));
    }

    /**
     * desc: 购物车高复用返回数据
     *
     * @param userId 用户id
     * @return CartVo
     * @auther RONALDO
     * @date: 2019-03-04 15:43
     */
    private CartVo getCartVoLimit(Long userId) {
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
