package com.cheery.controller.admin;

import com.alibaba.fastjson.JSON;
import com.cheery.pojo.Product;
import com.cheery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @desc:
 * @className: Demo
 * @author: RONALDO
 * @date: 2019-03-19 10:13
 */
@Controller
public class Demo {

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/t")
    public String t() {
        return "demo";
    }

    @GetMapping("/demo")
    public String demo(Product product) {
        System.out.println(JSON.toJSONString(product));
        if (null != productRepository.save(product)) {
            return "tip";
        } else {
            return "tips";
        }
    }

}
