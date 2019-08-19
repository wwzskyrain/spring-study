package edu.erik;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;


public class SpringbootRetryApplicationTests {
    @Autowired
    private PayService payService;

    @Test
    public void payTest() throws Exception {
        int store = payService.minGoodsnum(-1);
        System.out.println("库存为：" + store);
    }

}
