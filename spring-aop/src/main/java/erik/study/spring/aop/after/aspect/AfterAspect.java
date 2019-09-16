package erik.study.spring.aop.after.aspect;

import org.aspectj.lang.annotation.*;
import org.aspectj.lang.*;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

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
// 定义一个切面
@Aspect
public class AfterAspect {

    private static final Logger logger = LoggerFactory.getLogger(AfterAspect.class);


    // 匹配erik.study.spring.aop.after.service包下所有类的、
    // 所有方法的执行作为切入点
    @After("execution(* erik.study.spring.aop.after.service.*.*(..))")
    public void release() {
        logger.info("模拟方法结束后的释放资源...");
    }
}



