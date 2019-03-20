package com.cheery.controller.portal;

import com.cheery.common.*;
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

/**
 * @desc: 收货地址前端控制器
 * @className: ShippingController
 * @author: RONALDO
 * @date: 2019-03-06 13:23
 */
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/usr/address")
@Api("用户收货地址模块Api")
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    @Autowired
    private HttpSession session;

    private User user() {
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        if (null == user) {
            throw new GlobalException(ApiStatus.NEED_LOGIN.getCode(), ApiStatus.NEED_LOGIN.getDesc());
        }
        return user;
    }

    /**
     * desc: 添加收货地址
     *
     * @param shipping 收货地址对象
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-06 13:26
     */
    @ApiOperation(value = "添加收货地址")
    @PostMapping("/add")
    public ApiResult<?> add(Shipping shipping) {
        shipping.setUserId(user().getId());
        return shippingService.addAddress(shipping);
    }

    /**
     * desc: 修改收货地址
     *
     * @param shipping 收货地址对象
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-06 13:29
     */
    @ApiOperation(value = "修改收货地址")
    @PutMapping("/update")
    public ApiResult<?> update(Shipping shipping) {
        shipping.setUserId(user().getId());
        return shippingService.updateAddress(shipping);
    }

    /**
     * desc: 删除收货地址
     *
     * @param id 收货地址Id
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-06 13:32
     */
    @ApiOperation(value = "删除收货地址")
    @ApiImplicitParam(name = "id", value = "收货地址id", required = true, dataType = "Long")
    @DeleteMapping("/delete")
    public ApiResult<?> delete(Long id) {
        return shippingService.deleteAddress(id);
    }

    /**
     * desc: 根据用户id查询全部收货地址
     *
     * @param page   页码
     * @param size   每页条数
     * @param userId 用户id
     * @return ApiResult<?>
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
    public ApiResult<?> findAllByShippingId(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            Long userId
    ) {
        return shippingService.findAllAddress(page, size, userId);
    }

}
