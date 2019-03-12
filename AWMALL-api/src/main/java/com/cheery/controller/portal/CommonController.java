package com.cheery.controller.portal;

import com.cheery.common.ApiResult;
import com.cheery.service.ICommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @desc: 评论模块前端控制器
 * @className: CommonController
 * @author: RONALDO
 * @date: 2019-03-12 11:39
 */
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Api("评论模块Api")
public class CommonController {

    @Autowired
    private ICommentService commentService;

    /**
     * desc: 根据产品id查询对应的评论
     *
     * @param page 页码
     * @param size 每页条数
     * @param id   产品id
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-03 21:16
     */
    @ApiOperation(value = "根据产品id查询对应的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", dataType = "Integer"),
            @ApiImplicitParam(name = "id", value = "产品id", dataType = "Long")
    })
    @GetMapping("/p/comment")
    public ApiResult<?> findAllByProductId(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            Long id
    ) {
        return commentService.findCommentByProductId(page, size, id);
    }

    /**
     * desc: 根据用户id查询对应的评论
     *
     * @param page 页码
     * @param size 每页条数
     * @param id   用户id
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-03-06 14:49
     */
    @ApiOperation(value = "根据用户id查询对应的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", dataType = "Integer"),
            @ApiImplicitParam(name = "id", value = "产品id", dataType = "Long")
    })
    @GetMapping("/u/comment")
    public ApiResult<?> findAllByUserId(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "2") Integer size,
            Long id
    ) {
        return commentService.findCommentByUserId(page, size, id);
    }

}
