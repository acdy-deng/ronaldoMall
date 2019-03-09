package com.cheery.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @desc:
 * @className: GlobalException
 * @author: RONALDO
 * @date: 2019-03-09 17:56
 */
@Data
@AllArgsConstructor
public class GlobalException extends RuntimeException {

    private Integer code;

    private String desc;

}
