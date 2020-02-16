package erik.study.spring.aop.annotation;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;

@Aspect
@Component
public class LogExecutionTimeAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogExecutionTimeAspect.class);

    @Around("@annotation(erik.study.spring.aop.annotation.LogExecutionTime1)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        logger.info("signature:{}", JSON.toJSONString(signature));

        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        logger.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;

    }
}
