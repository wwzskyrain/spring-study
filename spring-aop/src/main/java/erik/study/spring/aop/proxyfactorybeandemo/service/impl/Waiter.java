package erik.study.spring.aop.proxyfactorybeandemo.service.impl;

import erik.study.spring.aop.proxyfactorybeandemo.service.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Waiter implements Wait {

    private static final Logger logger = LoggerFactory.getLogger(Waiter.class);

    @Override
    public void say() {
        logger.info("先生");
    }

    @Override
    public void shangCai() {
        say();
        logger.info("上菜咯。。。");
    }
}