package erik.study.spring.aop.proxyfactorybeandemo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date 2019-09-15
 * @Created by erik
 */
public class SayHelloBefore implements MethodBeforeAdvice {

    private static final Logger logger = LoggerFactory.getLogger(SayHelloBefore.class);

    private static final AtomicInteger counter = new AtomicInteger();

    private int count = counter.incrementAndGet();

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        logger.info("hello " + count);
    }
}
