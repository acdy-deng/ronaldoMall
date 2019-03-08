package com.cheery.controller.portal;

import com.cheery.common.Constant;
import com.cheery.common.ServerResponse;
import com.cheery.pojo.Shipping;
import com.cheery.pojo.User;
import com.cheery.service.IShippingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


import static com.cheery.util.TipsUtil.noLogin;
import static com.cheery.util.TipsUtil.serverError;

/**
 * @desc:
 * @className: ShippingController
 * @author: RONALDO
 * @date: 2019-03-06 13:23
 */
@RestController
@RequestMapping("/usr/address")
@Api("用户收货地址模块Api")
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    /**
     * desc: 添加收货地址
     *
     * @param shipping 收货地址对象
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-06 13:26
     */
    @ApiOperation(value = "添加收货地址")
    @PostMapping("/add")
    public ServerResponse<?> add(HttpSession session, Shipping shipping) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        try {
            if (null == currentUser) {
                return noLogin();
            } else {
                shipping.setUserId(currentUser.getId());
                return shippingService.addAddress(shipping);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }

    /**
     * desc: 修改收货地址
     *
     * @param shipping 收货地址对象
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-06 13:29
     */
    @ApiOperation(value = "修改收货地址")
    @PostMapping("/update")
    public ServerResponse<?> update(HttpSession session, Shipping shipping) {
        User currentUser = (User) session.getAttribute(Constant.CURRENT_USER);
        try {
            if (null == currentUser) {
                return noLogin();
            } else {
                shipping.setUserId(currentUser.getId());
                return shippingService.updateAddress(shipping);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }

    /**
     * desc: 删除收货地址
     *
     * @param id 收货地址Id
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-06 13:32
     */
    @ApiOperation(value = "删除收货地址")
    @ApiImplicitParam(name = "id", value = "收货地址id", required = true, dataType = "Long")
    @DeleteMapping("/delete")
    public ServerResponse<?> delete(HttpSession session, Long id) {
        try {
            if (null == session.getAttribute(Constant.CURRENT_USER)) {
                return noLogin();
            } else {
                return shippingService.deleteAddress(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }

    /**
     * desc: 根据用户id查询全部收货地址
     *
     * @param page   页码
     * @param size   每页条数
     * @param userId 用户id
     * @return ServerResponse<?>
     * @auther RONALDO
     * @date: 2019-03-06 13:34
     */
    @ApiOperation(value = "根据用户id查询全部收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", dataType = "Integer"),
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long")
    })
    @GetMapping("/list")
    public ServerResponse<?> findAllByShippingId(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            Long userId
    ) {
        try {
            return shippingService.findAllAddress(page, size, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
    }

}
