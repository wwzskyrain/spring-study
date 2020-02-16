package erik.spring.util.aop.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
public class LogExecutionTimeAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogExecutionTimeAspect.class);

    @Around("@annotation(erik.spring.util.aop.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        String canonicalMethodCallName = getCanonicalMethodCallName(joinPoint);
        logger.info("{} execution starts", canonicalMethodCallName);
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTimeInMillisecond = (System.currentTimeMillis() - start);
        logger.info("{} executions ended with milliseconds:{}", canonicalMethodCallName, executionTimeInMillisecond);
        return proceed;

    }

    private String getCanonicalMethodCallName(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = ((MethodSignature) joinPoint.getSignature());
        String methodName = methodSignature.getName();
        String classSimpleName = methodSignature.getDeclaringType().getSimpleName();
        String[] parametersNames = methodSignature.getParameterNames();
        Object[] parametersValues = joinPoint.getArgs();
        return String.format("method %s.%s with parameters %s = %s ",
                classSimpleName, methodName, JSON.toJSONString(parametersNames), JSON.toJSONString(parametersValues));

    }
}
