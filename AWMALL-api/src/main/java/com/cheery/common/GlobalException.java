package com.cheery.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @desc:
 * @className: GlobalException
 * @author: RONALDO
 * @date: 2019-03-09 17:56
 */
@Setter
@Getter
@AllArgsConstructor
public class GlobalException extends RuntimeException {

    private Integer code;

    private String desc;

}
