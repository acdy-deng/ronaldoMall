package com.cheery;

import com.cheery.repository.OrderItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AwmallApplicationTests {

    @Autowired
    private OrderItemRepository repository;

    @Test
    public void contextLoads() throws Exception {
//        File file = new File("/Users/fan/Downloads/0.jpg");
//        uploadFile(file);

        List list = repository.findAllByUserIdAndOrderNo(201901L, new BigInteger("1"));

        System.out.println(list);
    }

}
