package erik.study.spring.aop.after.service.impl;

import erik.study.spring.aop.after.service.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br/>Copyright (C), 2001-2016, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 *
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
@Component("hello")
public class HelloImpl implements Hello {

    private static final Logger logger = LoggerFactory.getLogger(HelloImpl.class);

    // 定义一个简单方法，模拟应用中的业务逻辑方法
    public void foo() {

        logger.info("执行Hello组件的foo()方法");
    }

    // 定义一个addUser()方法，模拟应用中的添加用户的方法
    public int addUser(String name, String pass) {
        logger.info("执行Hello组件的addUser添加用户：" + name);
        if (name.length() < 3 || name.length() > 10) {
            throw new IllegalArgumentException("name参数的长度必须大于3，小于10！");
        }
        return 20;
    }
}
