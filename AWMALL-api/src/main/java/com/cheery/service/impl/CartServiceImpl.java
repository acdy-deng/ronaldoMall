package com.cheery.service.impl;

import com.cheery.common.Constant;
import com.cheery.common.PojoToVo;
import com.cheery.common.ServerResponse;
import com.cheery.pojo.Cart;
import com.cheery.repository.CartRepository;
import com.cheery.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


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
    private PojoToVo pojoToVo;

    @Override
    public ServerResponse<?> cartInfo(Long userId) {
        return ServerResponse.createBySuccessMsgAndData("操作成功", pojoToVo.getCartVoLimit(userId));
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

}
