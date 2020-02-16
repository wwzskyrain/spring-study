package erik.study.spring.aop.tutorial.around;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAroundAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAroundAspect.class);

    @Around("execution(* erik.study.spring.aop.tutorial.EmployeeManager.createEmployee(..))")
    public void logAroundAllMethods(ProceedingJoinPoint joinPoint) {
        logger.info("before ProceedingJoinPoint.proceed()");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        logger.info("after ProceedingJoinPoint.proceed()");
    }

    @Around("execution(* erik.study.spring.aop.tutorial.EmployeeManager.*(..))")
    public void aspectWithAroundForThrowing(ProceedingJoinPoint joinPoint){
        logger.info("before ProceedingJoinPoint.proceed()");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.info("all throwable are catch.");
        }
        logger.info("after ProceedingJoinPoint.proceed()");
    }

}